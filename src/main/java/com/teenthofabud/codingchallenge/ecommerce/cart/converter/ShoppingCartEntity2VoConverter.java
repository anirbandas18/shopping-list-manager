package com.teenthofabud.codingchallenge.ecommerce.cart.converter;

import com.teenthofabud.codingchallenge.ecommerce.cart.model.CartItemVo;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingCartEntity;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingCartVo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Converts ShoppingCartEntity to ShoppingCartVo.
 * This converter retrieves cart items and calculates the total price.
 */
@Component
public class ShoppingCartEntity2VoConverter implements Converter<ShoppingCartEntity, ShoppingCartVo> {

    private final CartItemEntity2VoConverter cartItemEntity2VoConverter;

    public ShoppingCartEntity2VoConverter(CartItemEntity2VoConverter cartItemEntity2VoConverter) {
        this.cartItemEntity2VoConverter = cartItemEntity2VoConverter;
    }

    /**
     * Converts a ShoppingCartEntity to a ShoppingCartVo.
     * Retrieves cart items using CartItemEntity2VoConverter and calculates the total price.
     *
     * @param source the ShoppingCartEntity to convert
     * @return the converted ShoppingCartVo
     */
    @Override
    public ShoppingCartVo convert(ShoppingCartEntity source) {
        List<CartItemVo> cartItemVoList = source.getCartItems().stream().map(cartItemEntity2VoConverter::convert).toList();
        Double totalPrice = cartItemVoList.stream().map(CartItemVo::price).mapToDouble(Double::doubleValue).sum();
        return ShoppingCartVo.builder()
                .id(source.getId())
                .userName(source.getUsername())
                .totalPrice(totalPrice)
                .items(cartItemVoList)
                .build();
    }
}
