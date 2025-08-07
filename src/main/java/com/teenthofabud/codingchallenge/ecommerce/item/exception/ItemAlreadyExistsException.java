package com.teenthofabud.codingchallenge.ecommerce.item.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ItemAlreadyExistsException extends ItemException {

    public ItemAlreadyExistsException(String key, Object value) {
        super("Item with " + key + ": " + value + " already exists", HttpStatus.CONFLICT);
    }
}
