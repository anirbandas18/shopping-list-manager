package com.teenthofabud.codingchallenge.ecommerce.recommender;

import com.teenthofabud.codingchallenge.ecommerce.recommender.exception.RecommenderInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.recommender.exception.RecommenderMismatchException;
import com.teenthofabud.codingchallenge.ecommerce.recommender.exception.RecommenderNotFoundException;
import com.teenthofabud.codingchallenge.ecommerce.recommender.model.RecommendationVo;
import com.teenthofabud.codingchallenge.ecommerce.recommender.model.RecommenderDto;
import org.springframework.stereotype.Service;

/**
 * Service interface for handling recommendations based on shopping list data.
 * Provides methods to retrieve recommendations for a shopping list.
 */
@Service
public interface RecommenderService {

    /**
     * Retrieves recommendations for a shopping list based on the provided RecommenderDto.
     *
     * @param dto the RecommenderDto containing details for generating recommendations
     * @return RecommendationVo containing the recommended items
     * @throws RecommenderInvalidException if the input data is invalid
     * @throws RecommenderNotFoundException if no recommendations can be found
     */
    public RecommendationVo getRecommendationsForShoppingList(RecommenderDto dto) throws RecommenderInvalidException, RecommenderNotFoundException, RecommenderMismatchException;

}
