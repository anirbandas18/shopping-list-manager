package com.teenthofabud.codingchallenge.ecommerce.cart.service.impl;

import com.teenthofabud.codingchallenge.ecommerce.cart.converter.ListItemDto2EntityConverter;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListMismatchException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListNotFoundException;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.*;
import com.teenthofabud.codingchallenge.ecommerce.cart.repository.ListItemRepository;
import com.teenthofabud.codingchallenge.ecommerce.cart.repository.ShoppingListRepository;
import com.teenthofabud.codingchallenge.ecommerce.cart.service.ListItemService;
import com.teenthofabud.codingchallenge.ecommerce.cart.service.ShoppingListService;
import com.teenthofabud.codingchallenge.ecommerce.item.ItemService;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemNotFoundException;
import com.teenthofabud.codingchallenge.ecommerce.model.event.AddedToListEvent;
import com.teenthofabud.codingchallenge.ecommerce.model.event.DeletedFromListEvent;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Default implementation of ListItemService interface.
 * This class provides methods to manage items in a shopping cart, including adding and removing items.
 */
@Component
@Slf4j
public class ListItemServiceImpl implements ListItemService {

    private final ListItemRepository listItemRepository;
    private final ShoppingListService shoppingListService;
    private final ItemService itemService;
    private final ListItemDto2EntityConverter listItemDto2EntityConverter;
    private final ShoppingListRepository shoppingListRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ListItemServiceImpl(ListItemRepository listItemRepository, ShoppingListService shoppingListService, ItemService itemService, ListItemDto2EntityConverter listItemDto2EntityConverter, ShoppingListRepository shoppingListRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.listItemRepository = listItemRepository;
        this.shoppingListService = shoppingListService;
        this.itemService = itemService;
        this.listItemDto2EntityConverter = listItemDto2EntityConverter;
        this.shoppingListRepository = shoppingListRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    private void validateItemExists(Long itemId) throws ListInvalidException {
        try {
            itemService.getItemById(itemId);
            log.debug("Item with ID {} exists", itemId);
        } catch (ItemInvalidException | ItemNotFoundException e) {
            log.error("Unable to validate Item with ID {}", itemId, e);
            throw new ListInvalidException("item", "itemId", itemId);
        }
    }

    private void validateShoppingListExists(String username, Long listId) throws ListMismatchException, ListNotFoundException, ListInvalidException {
        shoppingListService.getShoppingList(ShoppingListDto.builder().username(username).id(listId).build());
        log.debug("Shopping list with ID {} exists for user: {}", listId, username);
    }

    @Override
    public Long addItemToList(@Valid ListItemDto dto) throws ListInvalidException, ListMismatchException, ListNotFoundException {
        Long itemId = dto.getForm().getItemId();
        Long listId = dto.getListId();
        String username = dto.getUsername();
        validateItemExists(itemId);
        validateShoppingListExists(username, listId);
        Optional<ListItemEntity> optionalCartItemEntity = listItemRepository.findByItemIdAndListId(itemId, listId);
        if (optionalCartItemEntity.isPresent()) {
            log.debug("Item with ID {} already exists in list with ID {}. Updating quantity.", itemId, listId);
            ListItemEntity existingCartItem = optionalCartItemEntity.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + dto.getForm().getQuantity());
            listItemRepository.save(existingCartItem);
            log.info("Updated quantity of item with ID {} in list with ID {}", itemId, listId);
            return existingCartItem.getId();
        } else {
            ListItemEntity listItemEntity = listItemDto2EntityConverter.convert(dto);
            log.debug("Adding item with ID {} to cart with ID {}", itemId, listId);
            Optional<ShoppingListEntity> optionalShoppingCartEntity = shoppingListRepository.findById(listId);
            ShoppingListEntity shoppingListEntity = optionalShoppingCartEntity.get();
            listItemEntity.setList(shoppingListEntity);
            log.debug("Cart item entity before saving: {}", listItemEntity);
            ListItemEntity savedEntity = listItemRepository.save(listItemEntity);
            log.debug("Saving cart item entity: {}", savedEntity);
            log.info("Added item with ID {} to list with ID {} for user: {}", itemId, listId, username);
            AddedToListEvent event = AddedToListEvent.builder().itemId(itemId).username(username).cartId(listId).build();
            applicationEventPublisher.publishEvent(event);
            log.debug("Published AddedToListEvent for item ID {} by user {}", itemId, username);
            log.debug("Returning ID of saved list item entity: {}", savedEntity.getId());
            return savedEntity.getId();
        }
    }

    @Override
    public void removeItemFromList(@Valid ListItemDto dto, Long id) throws ListNotFoundException, ListMismatchException, ListInvalidException {
        Long listId = dto.getListId();
        validateShoppingListExists(dto.getUsername(), listId);
        log.debug("Attempting to remove item with ID {} from list with ID {}", id, listId);
        if (!listItemRepository.existsById(id)) {
            log.error("List item with ID {} does not exist", id);
            throw new ListNotFoundException("item", "id", id);
        }
        Optional<ListItemEntity> optionalCartItemEntity = listItemRepository.findById(id);
        ListItemEntity listItemEntity = optionalCartItemEntity.get();
        listItemEntity.setList(null); // Clear the association with the cart
        log.debug("Removing list item with ID {} from list with ID {}", id, listId);
        listItemRepository.deleteById(id);
        log.info("Removed list item with ID {} from list with ID {} for user: {}", id, listId, dto.getUsername());
        DeletedFromListEvent event = DeletedFromListEvent.builder().itemId(id).cartId(listId).username(dto.getUsername()).build();
        applicationEventPublisher.publishEvent(event);
        log.debug("Published DeletedFromListEvent for item ID {} by user {}", id, dto.getUsername());
    }

}
