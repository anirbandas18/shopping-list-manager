package com.teenthofabud.codingchallenge.ecommerce.recommender.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RecommenderMismatchException extends RecommenderException {

    public RecommenderMismatchException(String key, Object value) {
        super("Recommender for " + key + ": " + value + " does not belong to user", HttpStatus.PRECONDITION_FAILED);
    }
}
