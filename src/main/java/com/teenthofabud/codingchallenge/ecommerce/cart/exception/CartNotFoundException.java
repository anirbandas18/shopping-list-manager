package com.teenthofabud.codingchallenge.ecommerce.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CartNotFoundException extends CartException {

    public CartNotFoundException(String context, String key, Object value) {
        super("Cart " + context + " not found with " + key + ": " + value, HttpStatus.NOT_FOUND);
    }
}
