package com.teenthofabud.codingchallenge.ecommerce.recommender.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RecommenderNotFoundException extends RecommenderException {

    public RecommenderNotFoundException(String key, Object value) {
        super("Recommender not found for " + key + ": " + value, HttpStatus.NOT_FOUND);
    }
}
