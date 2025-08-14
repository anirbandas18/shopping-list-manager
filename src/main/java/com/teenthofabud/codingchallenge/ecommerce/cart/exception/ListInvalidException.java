package com.teenthofabud.codingchallenge.ecommerce.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ListInvalidException extends ListException {

    public ListInvalidException(String context, String key, Object value) {
        super("List " + context + " with " + key + " cannot be " + value, HttpStatus.BAD_REQUEST);
    }
}
