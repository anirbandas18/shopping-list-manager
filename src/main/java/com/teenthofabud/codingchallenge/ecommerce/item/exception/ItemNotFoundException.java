package com.teenthofabud.codingchallenge.ecommerce.item.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ItemNotFoundException extends ItemException {

    public ItemNotFoundException(String key, Object value) {
        super("Item not found with " + key + ": " + value, HttpStatus.NOT_FOUND);
    }
}
