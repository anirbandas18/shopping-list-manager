package com.teenthofabud.codingchallenge.ecommerce.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ListNotFoundException extends ListException {

    public ListNotFoundException(String context, String key, Object value) {
        super("List " + context + " not found with " + key + ": " + value, HttpStatus.NOT_FOUND);
    }
}
