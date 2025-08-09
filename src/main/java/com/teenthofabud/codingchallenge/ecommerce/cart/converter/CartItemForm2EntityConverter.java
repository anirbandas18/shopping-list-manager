package com.teenthofabud.codingchallenge.ecommerce.cart.converter;

import com.teenthofabud.codingchallenge.ecommerce.cart.model.CartItemEntity;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.CartItemForm;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingCartEntity;
import com.teenthofabud.codingchallenge.ecommerce.cart.repository.ShoppingCartRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Converts CartItemForm to CartItemEntity.
 * This converter is used to create a new cart item without the ID.
 */
@Component
public class CartItemForm2EntityConverter implements Converter<CartItemForm, CartItemEntity> {

    private final ShoppingCartRepository shoppingCartRepository;

    public CartItemForm2EntityConverter(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    /**
     * Converts a CartItemForm to a CartItemEntity.
     * This method constructs a CartItemEntity with the item ID and quantity from the form.
     *
     * @param source the CartItemForm to convert
     * @return the converted CartItemEntity
     */
    @Override
    public CartItemEntity convert(CartItemForm source) {
        CartItemEntity cartItemEntity = CartItemEntity.builder().itemId(source.getItemId()).quantity(source.getQuantity()).build();
        Optional<ShoppingCartEntity> optionalShoppingCartEntity = shoppingCartRepository.findById(source.getCartId());
        ShoppingCartEntity shoppingCartEntity = optionalShoppingCartEntity.get();
        shoppingCartEntity.addItemToCart(cartItemEntity);
        return cartItemEntity;
    }
}
