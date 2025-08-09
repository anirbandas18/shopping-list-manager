package com.teenthofabud.codingchallenge.ecommerce.item.converter;

import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemProjection;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemVo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts an ItemProjection to an ItemVo.
 * This converter is used to transform the projection representation of an item
 * into a value object representation, which is typically used for data transfer.
 */
@Component
public class ItemProjection2VoConverter implements Converter<ItemProjection, ItemVo> {

    /**
     * Converts a given ItemProjection to an ItemVo.
     *
     * @param source the ItemProjection to convert
     * @return an ItemVo representation of the source projection
     */
    @Override
    public ItemVo convert(ItemProjection source) {
        return ItemVo.builder()
                .id(source.id())
                .price(source.price())
                .name(source.name())
                .build();
    }
}
