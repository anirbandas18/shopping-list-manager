package com.teenthofabud.codingchallenge.ecommerce.cart.service;

import com.teenthofabud.codingchallenge.ecommerce.cart.exception.*;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingListDto;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingListVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for managing shopping lists.
 * Provides methods to retrieve, create, delete, and update shopping lists for users.
 */
@Service
public interface ShoppingListService {

    /**
     * Retrieves all the shopping lists for a given user.
     *
     * @param userName the username of the user whose shopping list is to be retrieved
     * @return a list of ShoppingListVo representing the user's shopping list
     * @throws ListInvalidException if the shopping list is invalid
     */
    public List<ShoppingListVo> getShoppingLists(String userName) throws ListInvalidException;

    /**
     * Retrieves the shopping list for a user based on the provided ShoppingListDto.
     *
     * @param dto the ShoppingListDto containing the details of the shopping list to be retrieved
     * @return the ShoppingListVo representing the user's shopping list
     * @throws ListInvalidException if the shopping list is invalid
     * @throws ListNotFoundException if the shopping list is not found
     * @throws ListMismatchException if the shopping list does not belong to the user
     */
    public ShoppingListVo getShoppingList(ShoppingListDto dto) throws ListInvalidException, ListNotFoundException, ListMismatchException;

    /**
     * Creates a new shopping list for a user.
     *
     * @param userName the username of the user for whom the shopping list is to be created
     * @return the ID of the newly created shopping list
     * @throws ListInvalidException if the shopping list is invalid
     */
    public Long createShoppingList(String userName) throws ListInvalidException, ListAbusedException;

}
