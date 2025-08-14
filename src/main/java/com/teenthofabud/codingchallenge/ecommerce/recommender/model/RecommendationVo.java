package com.teenthofabud.codingchallenge.ecommerce.recommender.model;

import lombok.Builder;

import java.util.List;

@Builder
public record RecommendationVo(List<String> items,
                               List<String> complementaryItems,
                               List<String> alternativeItems,
                               List<String> recipeIdeas) {

}
