package com.teenthofabud.codingchallenge.ecommerce.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CartMismatchException extends CartException {

    public CartMismatchException(String context, String key, Object value) {
        super("Cart " + context + " with " + key + ": " + value + " does not belong to user", HttpStatus.PRECONDITION_FAILED);
    }
}
