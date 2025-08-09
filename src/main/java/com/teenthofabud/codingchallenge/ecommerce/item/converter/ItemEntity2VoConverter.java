package com.teenthofabud.codingchallenge.ecommerce.item.converter;

import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemVo;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts an ItemEntity to an ItemVo.
 * This converter is used to transform the entity representation of an item
 * into a value object representation, which is typically used for data transfer.
 */
@Component
public class ItemEntity2VoConverter implements Converter<ItemEntity, ItemVo> {

    /**
     * Converts a given ItemEntity to an ItemVo.
     *
     * @param source the ItemEntity to convert
     * @return an ItemVo representation of the source entity
     */
    @Override
    public ItemVo convert(ItemEntity source) {
        return ItemVo.builder()
                .id(source.getId())
                .category(source.getCategory().name())
                .price(source.getPrice())
                .description(source.getDescription())
                .name(source.getName())
                .build();
    }
}
