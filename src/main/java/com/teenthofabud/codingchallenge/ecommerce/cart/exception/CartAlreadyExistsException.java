package com.teenthofabud.codingchallenge.ecommerce.cart.exception;

import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CartAlreadyExistsException extends ItemException {

    public CartAlreadyExistsException(String context, String key, Object value) {
        super("Cart " + context + " with " + key + ": " + value + " already exists", HttpStatus.CONFLICT);
    }
}
