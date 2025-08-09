package com.teenthofabud.codingchallenge.ecommerce.cart.service.impl;

import com.teenthofabud.codingchallenge.ecommerce.cart.converter.CartItemEntity2VoConverter;
import com.teenthofabud.codingchallenge.ecommerce.cart.converter.CartItemForm2EntityConverter;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.CartAlreadyExistsException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.CartInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.CartMismatchException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.CartNotFoundException;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.*;
import com.teenthofabud.codingchallenge.ecommerce.cart.repository.CartItemRepository;
import com.teenthofabud.codingchallenge.ecommerce.cart.repository.ShoppingCartRepository;
import com.teenthofabud.codingchallenge.ecommerce.cart.service.CartItemService;
import com.teenthofabud.codingchallenge.ecommerce.cart.service.ShoppingCartService;
import com.teenthofabud.codingchallenge.ecommerce.item.ItemService;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Default implementation of CartItemService interface.
 * This class provides methods to manage items in the shopping cart, including adding, removing, and updating item quantities.
 */
@Component
@Slf4j
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ShoppingCartService shoppingCartService;
    private final ItemService itemService;
    private final CartItemForm2EntityConverter cartItemForm2EntityConverter;
    private final CartItemEntity2VoConverter cartItemEntity2VoConverter;
    private final ShoppingCartRepository shoppingCartRepository;

    public CartItemServiceImpl(CartItemEntity2VoConverter cartItemEntity2VoConverter, CartItemRepository cartItemRepository, ShoppingCartService shoppingCartService, ItemService itemService, CartItemForm2EntityConverter cartItemForm2EntityConverter, ShoppingCartRepository shoppingCartRepository) {
        this.cartItemEntity2VoConverter = cartItemEntity2VoConverter;
        this.cartItemRepository = cartItemRepository;
        this.shoppingCartService = shoppingCartService;
        this.itemService = itemService;
        this.cartItemForm2EntityConverter = cartItemForm2EntityConverter;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    private void validateItemExists(Long itemId) throws CartInvalidException {
        try {
            itemService.getItemById(itemId);
            log.debug("Item with ID {} exists", itemId);
        } catch (ItemInvalidException | ItemNotFoundException e) {
            log.error("Unable to validate Item with ID {}", itemId, e);
            throw new CartInvalidException("item", "itemId", itemId);
        }
    }

    private void validateShoppingCartExists(String username, Long cartId) throws CartInvalidException {
        try {
            shoppingCartService.getShoppingCart(ShoppingCartDto.builder().username(username).id(cartId).build());
            log.debug("Shopping cart with ID {} exists", cartId);
        } catch (CartNotFoundException | CartMismatchException e) {
            log.error("Unable to validate Cart with ID {} for user: {}", cartId, username, e);
            throw new CartInvalidException("item", "cartId", cartId);
        }
    }

    @Override
    public Long addItemToCart(CartItemForm cartItemForm) throws CartInvalidException, CartAlreadyExistsException {
        Long itemId = cartItemForm.getItemId();
        Long cartId = cartItemForm.getCartId();
        validateItemExists(itemId);
        validateShoppingCartExists(cartItemForm.getUsername(), cartItemForm.getCartId());
        if (cartItemRepository.existsByItemIdAndCartId(itemId, cartItemForm.getCartId())) {
            log.error("Item with ID {} already exists in cart with ID {}", itemId, cartId);
            throw new CartAlreadyExistsException("item", "itemId", itemId);
        }
        CartItemEntity cartItemEntity = cartItemForm2EntityConverter.convert(cartItemForm);
        log.debug("Adding item with ID {} to cart with ID {}", itemId, cartId);
        Optional<ShoppingCartEntity> optionalShoppingCartEntity = shoppingCartRepository.findById(cartId);
        ShoppingCartEntity shoppingCartEntity = optionalShoppingCartEntity.get();
        cartItemEntity.setCart(shoppingCartEntity);
        log.debug("Cart item entity before saving: {}", cartItemEntity);
        CartItemEntity savedEntity = cartItemRepository.save(cartItemEntity);
        log.debug("Saving cart item entity: {}", savedEntity);
        log.info("Added item with ID {} to cart with ID {}", itemId, cartId);
        return savedEntity.getId();
    }

    @Override
    public void removeItemFromCart(Long id) throws CartNotFoundException {
        if (!cartItemRepository.existsById(id)) {
            log.error("Cart item with ID {} does not exist", id);
            throw new CartNotFoundException("item", "id", id);
        }
        Optional<CartItemEntity> optionalCartItemEntity = cartItemRepository.findById(id);
        CartItemEntity cartItemEntity = optionalCartItemEntity.get();
        // TODO: Remove the association with the cart
        cartItemEntity.setCart(null); // Clear the association with the cart
        log.debug("Removing cart item with ID {}", id);
        cartItemRepository.deleteById(id);
        log.info("Removed cart item with ID {}", id);
    }

    @Override
    public void updateItemQuantityInCart(Long id, Integer quantity) throws CartNotFoundException, CartInvalidException {
        if (!cartItemRepository.existsById(id)) {
            log.error("Cart item with ID {} does not exist", id);
            throw new CartNotFoundException("item", "id", id);
        }
        if (quantity <= 0) {
            log.error("Invalid quantity {} for cart item with ID {}", quantity, id);
            throw new CartInvalidException("item", "quantity", quantity);
        }
        log.debug("Updating cart item with ID {} to quantity {}", id, quantity);
        CartItemEntity cartItemEntity = cartItemRepository.findById(id).orElseThrow(() -> new CartNotFoundException("item", "id", id));
        cartItemEntity.setQuantity(quantity);
        cartItemRepository.save(cartItemEntity);
        log.info("Updated cart item with ID {} to quantity {}", id, quantity);
    }

    @Override
    public List<CartItemVo> getItemsInCart(Long cartId) {
        log.debug("Retrieving items from cart with ID {}", cartId);
        List<CartItemEntity> cartItemEntities = cartItemRepository.findByCartId(cartId);
        List<CartItemVo> cartItemVos = new ArrayList<>();
        if (cartItemEntities.isEmpty()) {
            log.warn("No items found in cart with ID {}", cartId);
            return cartItemVos;
        }
        cartItemVos = cartItemEntities.stream().map(cartItemEntity2VoConverter::convert).toList();
        log.info("Retrieved {} items from cart with ID {}", cartItemVos.size(), cartId);
        return cartItemVos;
    }
}
