package com.teenthofabud.codingchallenge.ecommerce.item.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ItemInvalidException extends ItemException {

    public ItemInvalidException(String key, Object value) {
        super("Item " + key + " cannot be " + value, HttpStatus.BAD_REQUEST);
    }
}
