package com.teenthofabud.codingchallenge.ecommerce.item.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public class ItemException extends Exception {

    private final HttpStatus httpStatus;

    public ItemException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
