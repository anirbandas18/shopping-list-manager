package com.teenthofabud.codingchallenge.ecommerce.cart.converter;

import com.teenthofabud.codingchallenge.ecommerce.cart.model.CartItemEntity;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.CartItemVo;
import com.teenthofabud.codingchallenge.ecommerce.item.ItemService;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemNotFoundException;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemVo;
import com.teenthofabud.codingchallenge.ecommerce.model.ShoppingListManagerSystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts CartItemEntity to CartItemVo.
 * This converter retrieves item details from the ItemService and constructs a CartItemVo.
 */
@Component
@Slf4j
public class CartItemEntity2VoConverter implements Converter<CartItemEntity,CartItemVo> {

    private final ItemService itemService;

    public CartItemEntity2VoConverter(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Converts a CartItemEntity to a CartItemVo.
     * Retrieves item details using the ItemService and constructs the CartItemVo.
     *
     * @param cartItemEntity the CartItemEntity to convert
     * @return the converted CartItemVo
     * @throws ShoppingListManagerSystemException if item retrieval fails
     */
    @Override
    public CartItemVo convert(CartItemEntity cartItemEntity) {
        try {
            ItemVo itemVo = itemService.getItemById(cartItemEntity.getItemId());
            return CartItemVo.builder()
                    .quantity(cartItemEntity.getQuantity())
                    .description(itemVo.description())
                    .id(cartItemEntity.getId())
                    .name(itemVo.name())
                    .price(itemVo.price())
                    .build();
        } catch (ItemInvalidException | ItemNotFoundException e) {
            log.error("Unable to retrieve cart item: {}", e.getMessage());
            throw new ShoppingListManagerSystemException(e.getMessage());
        }
    }

}
