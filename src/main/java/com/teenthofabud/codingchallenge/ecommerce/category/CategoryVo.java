package com.teenthofabud.codingchallenge.ecommerce.category;

import lombok.Builder;

/**
 * Represents a category in the shopping list manager.
 * This record is used to encapsulate the name of the category.
 */
@Builder
public record CategoryVo(Integer ordinal, String name) {

}
