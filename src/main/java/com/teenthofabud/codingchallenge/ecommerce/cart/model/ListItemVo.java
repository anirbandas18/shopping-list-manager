package com.teenthofabud.codingchallenge.ecommerce.cart.model;

import lombok.Builder;

/**
 * Represents a value object for an item in the shopping cart.
 * This record encapsulates the details of a cart item, including its name, description, price, and quantity.
 */
@Builder
public record ListItemVo(Long id,
                         String name,
                         String description,
                         Double price,
                         Integer quantity) {

}