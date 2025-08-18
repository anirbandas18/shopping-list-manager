package com.teenthofabud.codingchallenge.ecommerce.recommender.impl;

import com.teenthofabud.codingchallenge.ecommerce.exception.ShoppingListManagerSystemException;
import com.teenthofabud.codingchallenge.ecommerce.recommender.RecommenderDAO;
import com.teenthofabud.codingchallenge.ecommerce.recommender.exception.RecommenderInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class AIRecommenderDAOImpl implements RecommenderDAO {

    private static final String PROVIDE_RECIPES_PROMPT = "Can you propose me some recipes ideas based on the combination of these items?";
    private static final String SUGGEST_COMPLEMENTARY_ITEMS_PROMPT = "Can you suggest me some complementary items based on these items?";
    private static final String SUGGEST_ALTERNATIVE_ITEMS_PROMPT = "Can you suggest me some alternative items based on these items?";

    private final ChatClient chatClient;

    public AIRecommenderDAOImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public List<String> provideRecipeIdeasBasedOnItems(List<String> items) throws RecommenderInvalidException {
        log.debug("Received items for recipe ideas: {}", items);
        if(items.isEmpty()) {
            log.warn("No items provided for recipe ideas");
            throw new RecommenderInvalidException("items", "No items provided for recipe ideas");
        }
        String itemsAsStr = String.join(",", items);
        log.info("Calling chat client to provide recipe ideas for items: {}", itemsAsStr);
        try {
            return this.chatClient.prompt()
                .advisors(new SimpleLoggerAdvisor())
                .system(sp -> sp.params(Map.of( "objective", "recipes", "items", itemsAsStr)))
                .user(PROVIDE_RECIPES_PROMPT)
                .call()
                .entity(new ListOutputConverter(new DefaultConversionService()));
        } catch (NonTransientAiException e) {
            log.error("Unable to call LLM via AI provider for providing recipes ", e);
            throw new ShoppingListManagerSystemException("Failed to call LLM via AI provider");
        }
    }

    @Override
    public List<String> suggestComplementaryItemsBasedOnItems(List<String> items) throws RecommenderInvalidException {
        log.debug("Received items for complementary items suggestion: {}", items);
        if(items.isEmpty()) {
            log.warn("No items provided for complementary items suggestion");
            throw new RecommenderInvalidException("items", "No items provided for complementary items suggestion");
        }
        String itemsAsStr = String.join(",", items);
        log.info("Calling chat client to suggest complementary items for items: {}", itemsAsStr);
        try {
            return this.chatClient.prompt()
                    .advisors(new SimpleLoggerAdvisor())
                    .system(sp -> sp.params(Map.of( "objective", "complementary items", "items", itemsAsStr)))
                    .user(SUGGEST_COMPLEMENTARY_ITEMS_PROMPT)
                    .call()
                    .entity(new ListOutputConverter(new DefaultConversionService()));
        } catch (NonTransientAiException e) {
            log.error("Unable to call LLM via AI provider for suggesting complementary items ", e);
            throw new ShoppingListManagerSystemException("Failed to call LLM via AI provider");
        }
    }

    @Override
    public List<String> suggestAlternativeItemsBasedOnItems(List<String> items) throws RecommenderInvalidException {
        log.debug("Received items for alternative items suggestion: {}", items);
        if(items.isEmpty()) {
            log.warn("No items provided for alternative items suggestion");
            throw new RecommenderInvalidException("items", "No items provided for alternative items suggestion");
        }
        String itemsAsStr = String.join(",", items);
        log.info("Calling chat client to suggest alternative items for items: {}", itemsAsStr);
        try {
            return this.chatClient.prompt()
                    .advisors(new SimpleLoggerAdvisor())
                    .system(sp -> sp.params(Map.of( "objective", "alternative items", "items", itemsAsStr)))
                    .user(SUGGEST_ALTERNATIVE_ITEMS_PROMPT)
                    .call()
                    .entity(new ListOutputConverter(new DefaultConversionService()));
        } catch (NonTransientAiException e) {
            log.error("Unable to call LLM via AI provider for suggesting alternative items ", e);
            throw new ShoppingListManagerSystemException("Failed to call LLM via AI provider");
        }
    }
}
