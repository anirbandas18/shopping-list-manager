package com.teenthofabud.codingchallenge.ecommerce.recommender.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RecommenderException extends Exception {

    private final HttpStatus httpStatus;

    public RecommenderException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
