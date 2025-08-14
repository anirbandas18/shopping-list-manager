package com.teenthofabud.codingchallenge.ecommerce.cart.service.impl;

import com.teenthofabud.codingchallenge.ecommerce.cart.converter.ShoppingListEntity2VoConverter;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.*;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingListDto;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingListEntity;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingListVo;
import com.teenthofabud.codingchallenge.ecommerce.cart.repository.ShoppingListRepository;
import com.teenthofabud.codingchallenge.ecommerce.cart.service.ShoppingListService;
import com.teenthofabud.codingchallenge.ecommerce.user.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of ShoppingListService interface.
 * This class provides methods to manage shopping lists for users, including creating, retrieving, and deleting them.
 */
@Component
@Slf4j
public class ShoppingListServiceImpl implements ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final UserService userService;
    private final ShoppingListEntity2VoConverter shoppingListEntity2VoConverter;

    private final Integer cartRateLimitPerUser; // Example limit, can be configured

    public ShoppingListServiceImpl(@Value("${shoppingListManager.cart.creation.rateLimitPerUser}") Integer cartRateLimitPerUser,
                                   ShoppingListRepository shoppingListRepository, UserService userService, ShoppingListEntity2VoConverter shoppingListEntity2VoConverter) {
        this.cartRateLimitPerUser = cartRateLimitPerUser;
        this.shoppingListRepository = shoppingListRepository;
        this.userService = userService;
        this.shoppingListEntity2VoConverter = shoppingListEntity2VoConverter;
    }

    private void validateUserExists(String userName) throws ListInvalidException {
        try {
            userService.loadUserByUsername(userName);
            log.debug("User with username {} exists", userName);
        } catch (UsernameNotFoundException e) {
            log.error("User with username {} does not exist", userName);
            throw new ListInvalidException("shopping", "username", userName);
        }
    }

    private ShoppingListEntity validateList(ShoppingListDto dto) throws ListNotFoundException, ListMismatchException {
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findById(dto.id())
                .orElseThrow(() -> new ListNotFoundException("shopping", "id", dto.id().toString()));
        if(!shoppingListEntity.getUsername().equals(dto.username())) {
            log.error("Username mismatch: expected {}, found {}", dto.username(), shoppingListEntity.getUsername());
            throw new ListMismatchException("shopping", "username", dto.username());
        }
        log.debug("Shopping list with ID {} validated for user {}", dto.id(), dto.username());
        return shoppingListEntity;
    }

    @Override
    public List<ShoppingListVo> getShoppingLists(String userName) throws ListInvalidException {
        validateUserExists(userName);
        List<ShoppingListEntity> shoppingCartEntities = shoppingListRepository.findByUsername(userName);
        List<ShoppingListVo> shoppingListVos = new ArrayList<>();
        if (shoppingCartEntities != null && !shoppingCartEntities.isEmpty()) {
            log.info("Found {} shopping lists for user: {}", shoppingCartEntities.size(), userName);
            shoppingListVos = shoppingCartEntities.stream().map(shoppingListEntity2VoConverter::convert).toList();
        }
        log.info("No shopping lists found for user: {}", userName);
        return shoppingListVos;
    }

    @Override
    public ShoppingListVo getShoppingList(@Valid ShoppingListDto dto) throws ListNotFoundException, ListMismatchException, ListInvalidException {
        validateUserExists(dto.username());
        log.debug("Retrieving shopping list with ID: {} for user: {}", dto.id(), dto.username());
        ShoppingListEntity shoppingListEntity = validateList(dto);
        log.info("Shopping list with ID: {} retrieved successfully for user: {}", dto.id(), dto.username());
        ShoppingListVo shoppingListVo = shoppingListEntity2VoConverter.convert(shoppingListEntity);
        log.debug("Shopping list details: {}", shoppingListVo);
        return shoppingListVo;
    }

    @Override
    public Long createShoppingList(String userName) throws ListInvalidException, ListAbusedException {
        validateUserExists(userName);
        List<ShoppingListEntity> shoppingCartEntities = shoppingListRepository.findByUsername(userName);
        if(shoppingCartEntities.size() == cartRateLimitPerUser) {
            log.error("User {} has reached the maximum limit of {} shopping lists", userName, cartRateLimitPerUser);
            throw new ListAbusedException("shopping", "limits", cartRateLimitPerUser);
        }
        log.debug("Creating new shopping list for user: {}", userName);
        ShoppingListEntity shoppingListEntity = ShoppingListEntity.builder()
                .username(userName)
                .build();
        ShoppingListEntity savedEntity = shoppingListRepository.save(shoppingListEntity);
        log.info("Shopping list created for user: {} with ID: {}", userName, savedEntity.getId());
        return savedEntity.getId();
    }

}
