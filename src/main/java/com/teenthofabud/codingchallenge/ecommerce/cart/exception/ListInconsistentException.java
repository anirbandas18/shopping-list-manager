package com.teenthofabud.codingchallenge.ecommerce.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ListInconsistentException extends ListException {

    public ListInconsistentException(String context, String key, Object value) {
        super("List " + context + " with " + key + ": " + value + " is already achieved", HttpStatus.PRECONDITION_FAILED);
    }
}
