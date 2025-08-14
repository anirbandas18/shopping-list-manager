package com.teenthofabud.codingchallenge.ecommerce.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ListAlreadyExistsException extends ListException {

    public ListAlreadyExistsException(String context, String key, Object value) {
        super("List " + context + " with " + key + ": " + value + " already exists", HttpStatus.CONFLICT);
    }
}
