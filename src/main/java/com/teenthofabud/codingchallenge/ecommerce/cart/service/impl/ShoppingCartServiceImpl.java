package com.teenthofabud.codingchallenge.ecommerce.cart.service.impl;

import com.teenthofabud.codingchallenge.ecommerce.cart.converter.ShoppingCartEntity2VoConverter;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.*;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.CartItemEntity;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingCartDto;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingCartEntity;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingCartVo;
import com.teenthofabud.codingchallenge.ecommerce.cart.repository.ShoppingCartRepository;
import com.teenthofabud.codingchallenge.ecommerce.cart.service.ShoppingCartService;
import com.teenthofabud.codingchallenge.ecommerce.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of ShoppingCartService interface.
 * This class provides methods to manage shopping carts for users, including creating, retrieving, and deleting shopping carts.
 */
@Component
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final ShoppingCartEntity2VoConverter shoppingCartEntity2VoConverter;

    private final Integer cartRateLimitPerUser; // Example limit, can be configured

    public ShoppingCartServiceImpl(@Value("${shoppingListManager.cart.creation.rateLimitPerUser}") Integer cartRateLimitPerUser,
            ShoppingCartRepository shoppingCartRepository, UserService userService, ShoppingCartEntity2VoConverter shoppingCartEntity2VoConverter) {
        this.cartRateLimitPerUser = cartRateLimitPerUser;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userService = userService;
        this.shoppingCartEntity2VoConverter = shoppingCartEntity2VoConverter;
    }

    private void validateUserExists(String userName) throws CartInvalidException {
        try {
            userService.loadUserByUsername(userName);
            log.debug("User with username {} exists", userName);
        } catch (UsernameNotFoundException e) {
            log.error("User with username {} does not exist", userName);
            throw new CartInvalidException("shopping", "username", userName);
        }
    }

    private ShoppingCartEntity validateCart(ShoppingCartDto dto) throws CartInvalidException, CartNotFoundException, CartMismatchException {
        if(dto.id() == null || dto.id() <= 0) {
            log.error("Invalid shopping cart ID: {}", dto.id());
            throw new CartInvalidException("shopping", "id", dto.id());
        }
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findById(dto.id())
                .orElseThrow(() -> new CartNotFoundException("shopping", "id", dto.id().toString()));
        if(!shoppingCartEntity.getUsername().equals(dto.username())) {
            log.error("Username mismatch: expected {}, found {}", dto.username(), shoppingCartEntity.getUsername());
            throw new CartMismatchException("shopping", "username", dto.username());
        }
        log.debug("Shopping cart with ID {} validated for user {}", dto.id(), dto.username());
        return shoppingCartEntity;
    }

    @Override
    public List<ShoppingCartVo> getShoppingCarts(String userName) throws CartInvalidException {
        validateUserExists(userName);
        List<ShoppingCartEntity> shoppingCartEntities = shoppingCartRepository.findByUsername(userName);
        List<ShoppingCartVo> shoppingCartVos = new ArrayList<>();
        if (shoppingCartEntities != null && !shoppingCartEntities.isEmpty()) {
            log.info("Found {} shopping carts for user: {}", shoppingCartEntities.size(), userName);
            shoppingCartVos = shoppingCartEntities.stream().map(shoppingCartEntity2VoConverter::convert).toList();
        }
        log.info("No shopping carts found for user: {}", userName);
        return shoppingCartVos;
    }

    @Override
    public ShoppingCartVo getShoppingCart(ShoppingCartDto dto) throws CartInvalidException, CartNotFoundException, CartMismatchException {
        validateUserExists(dto.username());
        log.debug("Retrieving shopping cart with ID: {} for user: {}", dto.id(), dto.username());
        ShoppingCartEntity shoppingCartEntity = validateCart(dto);
        log.info("Shopping cart with ID: {} retrieved successfully for user: {}", dto.id(), dto.username());
        ShoppingCartVo shoppingCartVo = shoppingCartEntity2VoConverter.convert(shoppingCartEntity);
        log.debug("Shopping cart details: {}", shoppingCartVo);
        return shoppingCartVo;
    }

    @Override
    public Long createShoppingCart(String userName) throws CartInvalidException, CartAbusedException {
        validateUserExists(userName);
        List<ShoppingCartEntity> shoppingCartEntities = shoppingCartRepository.findByUsername(userName);
        if(shoppingCartEntities.size() == cartRateLimitPerUser) {
            log.error("User {} has reached the maximum limit of {} shopping carts", userName, cartRateLimitPerUser);
            throw new CartAbusedException("shopping", "limits", cartRateLimitPerUser);
        }
        log.debug("Creating new shopping cart for user: {}", userName);
        ShoppingCartEntity shoppingCartEntity = ShoppingCartEntity.builder()
                .username(userName)
                .build();
        ShoppingCartEntity savedEntity = shoppingCartRepository.save(shoppingCartEntity);
        log.info("Shopping cart created for user: {} with ID: {}", userName, savedEntity.getId());
        return savedEntity.getId();
    }

    @Override
    public void deleteShoppingCart(ShoppingCartDto dto) throws CartNotFoundException, CartInvalidException, CartMismatchException {
        validateUserExists(dto.username());
        ShoppingCartEntity shoppingCartEntity = validateCart(dto);
        log.debug("Deleting shopping cart with ID: {} for user: {}", dto.id(), dto.username());
        shoppingCartRepository.delete(shoppingCartEntity);
        log.info("Shopping cart with ID: {} deleted for user: {}", dto.id(), dto.username());
    }

    @Override
    public void clearShoppingCart(ShoppingCartDto dto) throws CartInvalidException, CartNotFoundException, CartMismatchException, CartInconsistentException {
        validateUserExists(dto.username());
        ShoppingCartEntity shoppingCartEntity = validateCart(dto);
        List<CartItemEntity> cartItemEntities = shoppingCartEntity.getCartItems();
        if(cartItemEntities.isEmpty()) {
            log.error("User {} has no items in shopping cart with ID: {}", dto.username(), dto.id());
            throw new CartInconsistentException("shopping", "content", "empty");
        }
        log.info("Clearing shopping cart with ID: {} for user: {}", dto.id(), dto.username());
        //cartItemEntities.forEach(e -> e.setCart(null));
        shoppingCartEntity.setCartItems(null);
        shoppingCartRepository.save(shoppingCartEntity);
        log.info("Shopping cart with ID: {} cleared for user: {}", dto.id(), dto.username());
    }

}
