package com.teenthofabud.codingchallenge.ecommerce.recommender.impl;

import com.teenthofabud.codingchallenge.ecommerce.recommender.RecommenderService;
import com.teenthofabud.codingchallenge.ecommerce.recommender.model.RecommendationVo;
import com.teenthofabud.codingchallenge.ecommerce.recommender.RecommenderAPI;
import com.teenthofabud.codingchallenge.ecommerce.recommender.exception.RecommenderException;
import com.teenthofabud.codingchallenge.ecommerce.recommender.model.RecommenderDto;
import com.teenthofabud.codingchallenge.ecommerce.user.model.UserRole;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/recommender")
public class RecommenderController implements RecommenderAPI {

    private final RecommenderService recommenderService;

    public RecommenderController(RecommenderService recommenderService) {
        this.recommenderService = recommenderService;
    }

    /**
     * Endpoint to get recommendations based on the user's shopping cart.
     * Requires either ADMIN or USER role.
     *
     * @param authentication the authentication object containing user details
     * @param listId the ID of the shopping list
     * @return ResponseEntity containing RecommendationVo with recommendations
     * @throws RecommenderException if an error occurs while fetching recommendations
     */
    @PreAuthorize("hasAnyRole('" + UserRole.Fields.ROLE_ADMIN + "', '" + UserRole.Fields.ROLE_USER + "')")
    @Observed(name = "recommender.load-by-list-of-user", contextualName = "recommenders.find-specifically")
    @GetMapping(value = "/shopping-list/{list-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<RecommendationVo> getRecommendationsByListId(Authentication authentication, @PathVariable(name = "list-id") Long listId) throws RecommenderException {
        log.debug("Received request to get recommendations for list ID: {} for user: {}", listId, authentication.getName());
        RecommenderDto dto = RecommenderDto.builder().cartId(listId).username(authentication.getName()).build();
        log.debug("RecommenderDto created: {}", dto);
        RecommendationVo vo = recommenderService.getRecommendationsForShoppingList(dto);
        log.debug("Recommendations fetched: {}", vo);
        log.info("Returning recommendations for list ID: {}", listId);
        return ResponseEntity.ok(vo);
    }
}
