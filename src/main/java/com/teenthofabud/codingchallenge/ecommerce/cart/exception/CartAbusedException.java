package com.teenthofabud.codingchallenge.ecommerce.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CartAbusedException extends CartException {

    public CartAbusedException(String context, String key, Object value) {
        super("Cart " + context + " with " + key + ": " + value + " exceeded", HttpStatus.TOO_MANY_REQUESTS);
    }
}
