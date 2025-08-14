package com.teenthofabud.codingchallenge.ecommerce.cart.service;

import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListMismatchException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListNotFoundException;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ListItemDto;
import org.springframework.stereotype.Service;

/**
 * Service interface for managing items in a shopping list.
 * Provides methods to add, remove, and update items in the list.
 */
@Service
public interface ListItemService {

    /**
     * Adds an item to the shopping list or updates quantity if already present.
     *
     * @param dto the ListItemDto containing metadata and details of the item to be added
     * @return the ID of the newly added list item
     * @throws ListInvalidException if the provided list item data is invalid
     * @throws ListMismatchException if the list item is being added to a shopping list of a different user
     * @throws ListNotFoundException if the item or shopping list is not found
     */
    public Long addItemToList(ListItemDto dto) throws ListInvalidException, ListMismatchException, ListNotFoundException;

    /**
     * Adds an item to the shopping list using a form.
     *
     * @param dto the ListItemDto containing metadata and details of the item to be added
     * @param id the ID of the item already present in the shopping list
     * @throws ListNotFoundException if the list item or the shopping list is not found
     * @throws ListInvalidException if the user is not available
     * @throws ListMismatchException if the list item belongs to a different shopping list of the same or different user
     */
    public void removeItemFromList(ListItemDto dto, Long id) throws ListInvalidException, ListNotFoundException, ListMismatchException;

}
