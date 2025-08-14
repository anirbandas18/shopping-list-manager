package com.teenthofabud.codingchallenge.ecommerce.cart.resource;

import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListAlreadyExistsException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListException;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ListItemForm;
import com.teenthofabud.codingchallenge.ecommerce.model.ErrorVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
@Tag(name = "ListItemAPI", description = "Manage items in a user's shopping list")
public interface ListItemAPI {

    @Operation(method = "POST", summary = "Add new item or updates quantity of added item to shopping list of the user", description = "postListItem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added new item to shopping list"),
            @ApiResponse(
                    responseCode = "400", description = "Item not added to list due to invalid data",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
            @ApiResponse(
                    responseCode = "404", description = "Shopping list does not exist for the user",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
            @ApiResponse(
                    responseCode = "412", description = "Cannot add item to shopping list of another user",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))

    })
    @Parameters(value = {
            @Parameter(name = "list-id", description = "List Id", schema = @Schema(implementation = Integer.class), in = ParameterIn.PATH)
    })
    public ResponseEntity<Void> postListItem(Authentication authentication, Long listId,
                                             @RequestBody(description = "List item details", required = true, content = @Content(schema = @Schema(implementation = ListItemForm.class))) ListItemForm form) throws ListException, ListAlreadyExistsException;

    @Operation(method = "DELETE", summary = "Delete item from shopping list of the user", description = "deleteListItem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted existing item from shopping list"),
            @ApiResponse(
                    responseCode = "404", description = "Shopping list or Cart item not found with given id",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
            @ApiResponse(
                    responseCode = "412", description = "Shopping list does not exists or belongs to another user",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))

    })
    @Parameters(value = {
            @Parameter(name = "list-id", description = "List Id", schema = @Schema(implementation = Integer.class), in = ParameterIn.PATH),
            @Parameter(name = "id", description = "List Item Id", schema = @Schema(implementation = Integer.class), in = ParameterIn.QUERY)
    })
    public ResponseEntity<Void> deleteListItem(Authentication authentication, Long listId, Long id) throws ListException;

}
