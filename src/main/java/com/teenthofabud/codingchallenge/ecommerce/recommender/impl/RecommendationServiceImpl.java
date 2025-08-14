package com.teenthofabud.codingchallenge.ecommerce.recommender.impl;

import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListMismatchException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListNotFoundException;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ListItemVo;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingListDto;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingListVo;
import com.teenthofabud.codingchallenge.ecommerce.cart.service.ShoppingListService;
import com.teenthofabud.codingchallenge.ecommerce.recommender.RecommenderDAO;
import com.teenthofabud.codingchallenge.ecommerce.recommender.Recommender2ShoppingDtoConverter;
import com.teenthofabud.codingchallenge.ecommerce.recommender.exception.RecommenderInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.recommender.exception.RecommenderMismatchException;
import com.teenthofabud.codingchallenge.ecommerce.recommender.exception.RecommenderNotFoundException;
import com.teenthofabud.codingchallenge.ecommerce.recommender.model.RecommendationVo;
import com.teenthofabud.codingchallenge.ecommerce.recommender.model.RecommenderDto;
import com.teenthofabud.codingchallenge.ecommerce.recommender.RecommenderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@Slf4j
public class RecommendationServiceImpl implements RecommenderService {

    private final ShoppingListService shoppingListService;
    private final Recommender2ShoppingDtoConverter recommender2ShoppingDtoConverter;
    private final RecommenderDAO recommenderDAO;

    private final Integer maxCharactersForAutoComplete;

    public RecommendationServiceImpl(@Value("${shoppingListManager.recommender.autocomplete.maxCharacters}") Integer maxCharactersForAutoComplete, Recommender2ShoppingDtoConverter recommender2ShoppingDtoConverter, ShoppingListService shoppingListService, RecommenderDAO recommenderDAO) {
        this.maxCharactersForAutoComplete = maxCharactersForAutoComplete;
        this.recommender2ShoppingDtoConverter = recommender2ShoppingDtoConverter;
        this.shoppingListService = shoppingListService;
        this.recommenderDAO = recommenderDAO;
    }

    private ShoppingListVo getShoppingList(ShoppingListDto dto) throws RecommenderNotFoundException, RecommenderInvalidException, RecommenderMismatchException {
        try {
            log.debug("Fetching shopping list for dto: {}", dto);
            ShoppingListVo vo = shoppingListService.getShoppingList(dto);
            log.debug("Fetched shopping list: {}", vo);
            return vo;
        } catch (ListInvalidException e) {
            throw new RecommenderInvalidException("shopping list", e.getMessage());
        } catch (ListMismatchException e) {
            throw new RecommenderMismatchException("shopping list", dto.id());
        } catch (ListNotFoundException e) {
            throw new RecommenderNotFoundException("shopping list", e.getMessage());
        }
    }

    @Override
    public RecommendationVo getRecommendationsForShoppingList(@Valid RecommenderDto dto) throws RecommenderInvalidException, RecommenderNotFoundException, RecommenderMismatchException {
        log.debug("Converting RecommenderDto to ShoppingListDto: {}", dto);
        ShoppingListDto shoppingListDto = recommender2ShoppingDtoConverter.convert(dto);
        ShoppingListVo shoppingListVo = getShoppingList(shoppingListDto);
        List<String> items = shoppingListVo.items().stream().map(ListItemVo::name).toList();
        List<String> autoCompletedItems = items.stream().map(e -> e.substring(0, maxCharactersForAutoComplete)).toList();
        log.debug("Extracted items from shopping list: {}", items);
        if (items.isEmpty()) {
            log.warn("No items found in the shopping list for dto: {}", dto);
            throw new RecommenderInvalidException("shopping list", "No items");
        }
        log.debug("Suggesting complementary items based on items: {}", items);
        List<String> complementaryItems = recommenderDAO.suggestComplementaryItemsBasedOnItems(autoCompletedItems);
        log.debug("Received complementary items: {}", complementaryItems);
        log.debug("Suggesting alternative items based on items: {}", items);
        List<String> alternativeItems = recommenderDAO.suggestAlternativeItemsBasedOnItems(autoCompletedItems);
        log.debug("Received alternative items: {}", complementaryItems);
        List<String> itemsForRecommendationWithComplementaryItems = Stream.concat(items.stream(), complementaryItems.stream()).toList();
        List<String> itemsForRecommendationWithComplementaryAndAlternativeItems = Stream.concat(itemsForRecommendationWithComplementaryItems.stream(), alternativeItems.stream()).toList();
        log.info("To provide recipe ideas we are enhancing original items with complementary and alternative items: {}", itemsForRecommendationWithComplementaryAndAlternativeItems);
        List<String> recipeIdeas = recommenderDAO.provideRecipeIdeasBasedOnItems(itemsForRecommendationWithComplementaryAndAlternativeItems);
        log.debug("Received recipe ideas: {}", recipeIdeas);
        return RecommendationVo.builder()
                .recipeIdeas(recipeIdeas)
                .complementaryItems(complementaryItems)
                .alternativeItems(alternativeItems)
                .build();
    }

}
