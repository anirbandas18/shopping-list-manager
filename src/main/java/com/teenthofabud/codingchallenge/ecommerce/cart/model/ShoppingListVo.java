package com.teenthofabud.codingchallenge.ecommerce.cart.model;

import lombok.Builder;

import java.util.List;

/**
 * Represents a value object for a shopping cart in the shopping list manager.
 * This record encapsulates the details of a shopping cart, including its ID, user ID,
 * a list of cart items, and the total price of the items in the cart.
 */
@Builder
public record ShoppingListVo(Long id,
                             String userName,
                             List<ListItemVo> items,
                             Double totalPrice) {
}
