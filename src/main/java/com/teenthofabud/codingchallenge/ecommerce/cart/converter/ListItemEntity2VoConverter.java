package com.teenthofabud.codingchallenge.ecommerce.cart.converter;

import com.teenthofabud.codingchallenge.ecommerce.cart.model.ListItemEntity;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ListItemVo;
import com.teenthofabud.codingchallenge.ecommerce.exception.ShoppingListManagerSystemException;
import com.teenthofabud.codingchallenge.ecommerce.item.ItemService;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemNotFoundException;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts ListItemEntity to ListItemVo.
 * This converter retrieves item details from the ItemService and constructs a ListItemVo.
 */
@Component
@Slf4j
public class ListItemEntity2VoConverter implements Converter<ListItemEntity, ListItemVo> {

    private final ItemService itemService;

    public ListItemEntity2VoConverter(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Converts a ListItemEntity to a ListItemVo.
     * Retrieves item details using the ItemService and constructs the ListItemVo.
     *
     * @param listItemEntity the ListItemEntity to convert
     * @return the converted ListItemVo
     * @throws ShoppingListManagerSystemException if item retrieval fails
     */
    @Override
    public ListItemVo convert(ListItemEntity listItemEntity) {
        try {
            ItemVo itemVo = itemService.getItemById(listItemEntity.getItemId());
            return ListItemVo.builder()
                    .quantity(listItemEntity.getQuantity())
                    .description(itemVo.description())
                    .id(listItemEntity.getId())
                    .name(itemVo.name())
                    .price(itemVo.price())
                    .build();
        } catch (ItemInvalidException | ItemNotFoundException e) {
            log.error("Unable to retrieve cart item: {}", e.getMessage());
            throw new ShoppingListManagerSystemException(e.getMessage());
        }
    }

}
