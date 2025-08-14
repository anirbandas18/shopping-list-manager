package com.teenthofabud.codingchallenge.ecommerce.recommender;

import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingListDto;
import com.teenthofabud.codingchallenge.ecommerce.recommender.model.RecommenderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Recommender2ShoppingDtoConverter implements Converter<RecommenderDto, ShoppingListDto> {

    @Override
    public ShoppingListDto convert(RecommenderDto source) {
        return ShoppingListDto.builder().id(source.getCartId()).username(source.getUsername()).build();
    }
}
