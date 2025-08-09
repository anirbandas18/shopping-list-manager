package com.teenthofabud.codingchallenge.ecommerce.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CartInvalidException extends CartException {

    public CartInvalidException(String context, String key, Object value) {
        super("Cart " + context + " with " + key + " cannot be " + value, HttpStatus.BAD_REQUEST);
    }
}
