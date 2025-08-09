package com.teenthofabud.codingchallenge.ecommerce.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CartInconsistentException extends CartException {

    public CartInconsistentException(String context, String key, Object value) {
        super("Cart " + context + " with " + key + ": " + value + " is already achieved", HttpStatus.PRECONDITION_FAILED);
    }
}
