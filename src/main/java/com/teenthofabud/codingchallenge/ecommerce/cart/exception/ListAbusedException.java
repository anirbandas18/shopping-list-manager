package com.teenthofabud.codingchallenge.ecommerce.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ListAbusedException extends ListException {

    public ListAbusedException(String context, String key, Object value) {
        super("List " + context + " with " + key + ": " + value + " exceeded", HttpStatus.TOO_MANY_REQUESTS);
    }
}
