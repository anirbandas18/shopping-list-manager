package com.teenthofabud.codingchallenge.ecommerce.recommender.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RecommenderInvalidException extends RecommenderException {

    public RecommenderInvalidException(String key, Object value) {
        super("Recommender for " + key + " is wrong " + value, HttpStatus.BAD_REQUEST);
    }
}
