package com.teenthofabud.codingchallenge.ecommerce.cart.converter;

import com.teenthofabud.codingchallenge.ecommerce.cart.model.ListItemVo;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingListEntity;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingListVo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Converts ShoppingListEntity to ShoppingListVo.
 * This converter retrieves list items and calculates the total price.
 */
@Component
public class ShoppingListEntity2VoConverter implements Converter<ShoppingListEntity, ShoppingListVo> {

    private final ListItemEntity2VoConverter listItemEntity2VoConverter;

    public ShoppingListEntity2VoConverter(ListItemEntity2VoConverter listItemEntity2VoConverter) {
        this.listItemEntity2VoConverter = listItemEntity2VoConverter;
    }

    /**
     * Converts a ShoppingListEntity to a ShoppingListVo.
     * Retrieves list items using ListItemEntity2VoConverter and calculates the total price.
     *
     * @param source the ShoppingListEntity to convert
     * @return the converted ShoppingListVo
     */
    @Override
    public ShoppingListVo convert(ShoppingListEntity source) {
        List<ListItemVo> listItemVoList = source.getListItems().stream().map(listItemEntity2VoConverter::convert).toList();
        double totalPrice = 0.0d;
        for(ListItemVo e : listItemVoList) {
            totalPrice = totalPrice + (e.price() * e.quantity());
        }
        return ShoppingListVo.builder()
                .id(source.getId())
                .userName(source.getUsername())
                .totalPrice(totalPrice)
                .items(listItemVoList)
                .build();
    }
}
