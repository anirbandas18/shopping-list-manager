package com.teenthofabud.codingchallenge.ecommerce.item.model;

import lombok.*;

/**
 * Represents a value object for an item in the shopping list manager.
 * This record encapsulates the details of an item, including its category, name, description, and price.
 */
@Builder
public record ItemVo(Long id, String category, String name, String description, Double price) {

}
