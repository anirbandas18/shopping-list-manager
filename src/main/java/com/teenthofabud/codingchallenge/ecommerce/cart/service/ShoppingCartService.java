package com.teenthofabud.codingchallenge.ecommerce.cart.service;

import com.teenthofabud.codingchallenge.ecommerce.cart.exception.*;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingCartDto;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingCartVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for managing shopping carts in an e-commerce application.
 * Provides methods to retrieve, create, delete, and update shopping carts for users.
 */
@Service
public interface ShoppingCartService {

    /**
     * Retrieves all the shopping carts for a given user.
     *
     * @param userName the username of the user whose shopping cart is to be retrieved
     * @return a list of ShoppingCartVo representing the user's shopping cart
     * @throws CartInvalidException if the shopping cart is invalid
     */
    public List<ShoppingCartVo> getShoppingCarts(String userName) throws CartInvalidException;

    /**
     * Retrieves the shopping cart for a user based on the provided ShoppingCartDto.
     *
     * @param dto the ShoppingCartDto containing the details of the shopping cart to be retrieved
     * @return the ShoppingCartVo representing the user's shopping cart
     * @throws CartInvalidException if the shopping cart is invalid
     * @throws CartNotFoundException if the shopping cart is not found
     * @throws CartMismatchException if the shopping cart does not belong to the user
     */
    public ShoppingCartVo getShoppingCart(ShoppingCartDto dto) throws CartInvalidException, CartNotFoundException, CartMismatchException;

    /**
     * Creates a new shopping cart for a user.
     *
     * @param userName the username of the user for whom the shopping cart is to be created
     * @return the ID of the newly created shopping cart
     * @throws CartInvalidException if the shopping cart is invalid
     */
    public Long createShoppingCart(String userName) throws CartInvalidException, CartAbusedException;

    /**
     * Deletes the shopping cart for a user.
     *
     * @param dto the ShoppingCartDto containing the details of the shopping cart to be deleted
     * @throws CartInvalidException if the shopping cart is invalid
     * @throws CartNotFoundException if the shopping cart is not found
     * @throws CartMismatchException if the shopping cart does not belong to the user
     */
    public void deleteShoppingCart(ShoppingCartDto dto) throws CartInvalidException, CartNotFoundException, CartMismatchException;

    /**
     * Clears the shopping cart for a user.
     *
     * @param dto the ShoppingCartDto containing the details of the shopping cart to be cleared
     * @throws CartInvalidException if the shopping cart is invalid
     * @throws CartNotFoundException if the shopping cart is not found
     * @throws CartMismatchException if the shopping cart does not belong to the user
     */
    public void clearShoppingCart(ShoppingCartDto dto) throws CartInvalidException, CartNotFoundException, CartMismatchException, CartInconsistentException;

/*
    */
/**
     * Updates the shopping cart for a user.
     *
     * @param dto the ShoppingCartDto containing the updated details of the shopping cart
     * @throws CartInvalidException if the shopping cart is invalid
     * @throws CartNotFoundException if the shopping cart is not found
     *//*

    public void addItemToShoppingCart(ShoppingCartDto dto, CartItemForm form) throws CartInvalidException, CartNotFoundException;

    */
/**
     * Removes an item from the shopping cart for a user.
     *
     * @param dto the ShoppingCartDto containing the details of the shopping cart
     * @param form the CartItemForm containing the details of the item to be removed
     * @throws CartInvalidException if the shopping cart or item is invalid
     * @throws CartNotFoundException if the shopping cart is not found
     *//*

    public void removeItemFromShoppingCart(ShoppingCartDto dto, CartItemForm form) throws CartInvalidException, CartNotFoundException;
*/

}
