package com.teenthofabud.codingchallenge.ecommerce.cart.model;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a Data Transfer Object (DTO) for a shopping cart in the shopping list manager.
 * This record encapsulates the details of a shopping cart, including its ID and username.
 */
@Builder
public record ShoppingCartDto(Long id, String username) {
}
