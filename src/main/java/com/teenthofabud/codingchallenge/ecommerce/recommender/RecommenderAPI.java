package com.teenthofabud.codingchallenge.ecommerce.recommender;

import com.teenthofabud.codingchallenge.ecommerce.model.ErrorVo;
import com.teenthofabud.codingchallenge.ecommerce.recommender.exception.RecommenderException;
import com.teenthofabud.codingchallenge.ecommerce.recommender.model.RecommendationVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400", description = "Invalid request data",
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
        @ApiResponse(
                responseCode = "401", description = "Unauthorized access",
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
        @ApiResponse(
                responseCode = "500", description = "Server error",
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))
})
@Tag(name = "RecommenderAPI", description = "Recommend items based on the user's shopping list")
public interface RecommenderAPI {

    @Operation(method = "GET", summary = "Recommended items based on user's shopping list", description = "getRecommendationsByListId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully recommended items based on user's shopping list",
                    content = @Content(schema = @Schema(implementation = RecommendationVo.class))),
            @ApiResponse(
                    responseCode = "400", description = "List id is invalid",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
            @ApiResponse(
                    responseCode = "404", description = "List not found with given id",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
            @ApiResponse(
                    responseCode = "412", description = "List not associated with user",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))
    })
    @Parameters(value = {
            @Parameter(name = "list-id", description = "List Id", schema = @Schema(implementation = Integer.class), in = ParameterIn.PATH)
    })
    public ResponseEntity<RecommendationVo> getRecommendationsByListId(Authentication authentication, Long listId) throws RecommenderException;

}
