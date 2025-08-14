package com.teenthofabud.codingchallenge.ecommerce.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ListMismatchException extends ListException {

    public ListMismatchException(String context, String key, Object value) {
        super("List " + context + " with " + key + ": " + value + " does not belong to user", HttpStatus.PRECONDITION_FAILED);
    }
}
