package com.teenthofabud.codingchallenge.ecommerce.cart.service;

import com.teenthofabud.codingchallenge.ecommerce.cart.exception.CartAlreadyExistsException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.CartInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.CartNotFoundException;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.CartItemForm;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.CartItemVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for managing shopping cart items.
 * Provides methods to add, remove, update, and clear items in the shopping cart.
 */
@Service
public interface CartItemService {

    /**
     * Adds an item to the shopping cart.
     *
     * @param cartItemForm the form containing details of the item to be added
     */
    public Long addItemToCart(CartItemForm cartItemForm) throws CartInvalidException, CartAlreadyExistsException;

    /**
     * Removes an item from the shopping cart.
     *
     * @param id the ID of the cart item to be removed
     */
    public void removeItemFromCart(Long id) throws CartNotFoundException;

    /**
     * Updates the quantity of an item in the shopping cart.
     *
     * @param id the ID of the cart item to be updated
     * @param quantity the new quantity for the item
     */
    public void updateItemQuantityInCart(Long id, Integer quantity) throws CartNotFoundException, CartInvalidException;

    /**
     * Get all items in the shopping cart.
     *
     * @param cartId the ID of the shopping cart to be retrieved from
     * @return a CartItemVo representing the item in the cart
     */
    public List<CartItemVo> getItemsInCart(Long cartId);
}
