package com.teenthofabud.codingchallenge.ecommerce.item.converter;

import com.teenthofabud.codingchallenge.ecommerce.category.Category;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemEntity;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemForm;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts an ItemForm object to an ItemEntity object.
 * This converter is used to transform the data from the form representation
 * to the entity representation before saving it to the database.
 */
@Component
public class ItemForm2EntityConverter implements Converter<ItemForm, ItemEntity> {

    /**
     * Converts a given ItemForm to an ItemEntity.
     *
     * @param source the ItemForm to convert
     * @return an ItemEntity representation of the source form
     */
    @Override
    public ItemEntity convert(ItemForm source) {
        return ItemEntity.builder()
                .price(source.getPrice())
                .name(source.getName())
                .description(source.getDescription())
                .category(Category.valueOf(source.getCategory()))
                .build();
    }
}
