package com.teenthofabud.codingchallenge.ecommerce.item.model;

import com.teenthofabud.codingchallenge.ecommerce.category.Category;
import lombok.Builder;

/**
 * Represents a projection of an item in the shopping list manager.
 * This record encapsulates the details of an item, including its category, name, and price.
 */
@Builder
public record ItemProjection(Long id, String name, Double price) {

}
