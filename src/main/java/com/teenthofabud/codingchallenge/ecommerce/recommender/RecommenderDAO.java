package com.teenthofabud.codingchallenge.ecommerce.recommender;

import com.teenthofabud.codingchallenge.ecommerce.recommender.exception.RecommenderInvalidException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DAO interface for providing recipe ideas based on a list of items.
 * This interface defines methods to interact with the data layer for recommendation purposes.
 */
@Service
public interface RecommenderDAO {

    /**
     * Provides recipe ideas based on a list of items.
     *
     * @param items List of item names to base recipe ideas on
     * @return List of recipe ideas
     */
    public List<String> provideRecipeIdeasBasedOnItems(List<String> items) throws RecommenderInvalidException;

    /**
     * Suggests complementary items based on a list of items.
     *
     * @param items List of item names to base complementary item suggestions on
     * @return List of suggested complementary items
     */
    public List<String> suggestComplementaryItemsBasedOnItems(List<String> items) throws RecommenderInvalidException;

    /**
     * Suggests alternative items based on a list of items.
     *
     * @param items List of item names to base alternative item suggestions on
     * @return List of suggested alternative items
     */
    public List<String> suggestAlternativeItemsBasedOnItems(List<String> items) throws RecommenderInvalidException;

}