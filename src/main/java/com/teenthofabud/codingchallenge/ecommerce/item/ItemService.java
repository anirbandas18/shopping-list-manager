package com.teenthofabud.codingchallenge.ecommerce.item;

import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemAlreadyExistsException;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemNotFoundException;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemVo;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemForm;

import java.util.List;

public interface ItemService {
    /**
     * Adds a new item to the system.
     *
     * @param itemForm the form containing item details
     * @return the ID of the newly added item
     * @throws ItemAlreadyExistsException if an item with the same details already exists
     */
    public Long addItem(ItemForm itemForm) throws ItemAlreadyExistsException;

    /**
     * Retrieves an item by its ID.
     *
     * @param itemId the ID of the item to retrieve
     * @return the retrieved item entity
     * @throws ItemInvalidException if the item is invalid
     * @throws ItemNotFoundException if the item with the specified ID does not exist
     */
    ItemVo getItemById(Long itemId) throws ItemInvalidException, ItemNotFoundException;

    /**
     * Retrieves all items in the system.
     *
     * @return a list of all item entities
     */
    public List<ItemVo> getItems();

}
