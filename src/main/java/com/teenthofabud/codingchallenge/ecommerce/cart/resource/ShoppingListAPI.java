package com.teenthofabud.codingchallenge.ecommerce.cart.resource;

import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingListVo;
import com.teenthofabud.codingchallenge.ecommerce.model.ErrorVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

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
@Tag(name = "ShoppingListAPI", description = "Manage shopping lists for a user")
public interface ShoppingListAPI {

    @Operation(method = "POST", summary = "Create new list for the user", description = "postList")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created new list"),
            @ApiResponse(
                    responseCode = "400", description = "List not accepted due to invalid data",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
            @ApiResponse(
                    responseCode = "429", description = "Too many lists for the user",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))

    })
    public ResponseEntity<Void> postList(Authentication authentication) throws ListException;

    @Operation(method = "GET", summary = "Get list details by Id for the user", description = "getListById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list details by id",
                    content = @Content(schema = @Schema(implementation = ShoppingListVo.class))),
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
            @Parameter(name = "id", description = "List Id", schema = @Schema(implementation = Integer.class), in = ParameterIn.PATH)
    })
    public ResponseEntity<ShoppingListVo> getListById(Authentication authentication, Long id) throws ListException;

    @Operation(method = "GET", summary = "Get all lists of the user", description = "getLists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all lists",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingListVo.class)))),
            @ApiResponse(responseCode = "204", description = "No lists found")
    })
    public ResponseEntity<List<ShoppingListVo>> getLists(Authentication authentication) throws ListInvalidException;

}
