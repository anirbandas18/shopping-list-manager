package com.teenthofabud.codingchallenge.ecommerce.cart.model;

import lombok.Builder;

/**
 * Represents a Data Transfer Object (DTO) for a shopping cart in the shopping list manager.
 * This record encapsulates the details of a shopping cart, including its ID and username.
 */
@Builder
public record ShoppingListDto(Long id, String username) {
}
