package com.teenthofabud.codingchallenge.ecommerce.recommender.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RecommenderInconsistentException extends RecommenderException {

    public RecommenderInconsistentException(String key, Object value) {
        super("Recommender with " + key + ": " + value + " is already achieved", HttpStatus.PRECONDITION_FAILED);
    }
}
