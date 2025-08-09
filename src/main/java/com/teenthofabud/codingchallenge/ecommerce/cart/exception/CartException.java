package com.teenthofabud.codingchallenge.ecommerce.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CartException extends Exception {

    private final HttpStatus httpStatus;

    public CartException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
