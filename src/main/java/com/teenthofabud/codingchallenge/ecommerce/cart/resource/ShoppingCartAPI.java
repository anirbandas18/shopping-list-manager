package com.teenthofabud.codingchallenge.ecommerce.cart.resource;

import com.teenthofabud.codingchallenge.ecommerce.cart.exception.CartException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.CartInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingCartVo;
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
@Tag(name = "ShoppingCartAPI", description = "Manage shopping carts for a user")
public interface ShoppingCartAPI {

    @Operation(method = "POST", summary = "Create new cart for the user", description = "postCart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created new cart"),
            @ApiResponse(
                    responseCode = "400", description = "Cart not accepted due to invalid data",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
            @ApiResponse(
                    responseCode = "429", description = "Too many carts for the user",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))

    })
    public ResponseEntity<Void> postCart(Authentication authentication) throws CartException;

    @Operation(method = "DELETE", summary = "Delete cart by Id for the user", description = "deleteCart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted existing cart"),
            @ApiResponse(
                    responseCode = "400", description = "Cart id is invalid",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
            @ApiResponse(
                    responseCode = "404", description = "Cart not found with given id",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
            @ApiResponse(
                    responseCode = "412", description = "Cart not associated with user",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))

    })
    @Parameters(value = {
            @Parameter(name = "id", description = "Cart Id", schema = @Schema(implementation = Integer.class), in = ParameterIn.PATH)
    })
    public ResponseEntity<Void> deleteCart(Authentication authentication, Long id) throws CartException;

    @Operation(method = "PUT", summary = "Clear cart by Id for the user", description = "putCart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cleared existing cart"),
            @ApiResponse(
                    responseCode = "400", description = "Cart id is invalid",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
            @ApiResponse(
                    responseCode = "404", description = "Cart not found with given id",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
            @ApiResponse(
                    responseCode = "412", description = "Cart not associated with user",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))

    })
    @Parameters(value = {
            @Parameter(name = "id", description = "Cart Id", schema = @Schema(implementation = Integer.class), in = ParameterIn.PATH)
    })
    public ResponseEntity<Void> putCart(Authentication authentication, Long id) throws CartException;

    @Operation(method = "GET", summary = "Get cart details by Id for the user", description = "getCartById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cart details by id",
                    content = @Content(schema = @Schema(implementation = ShoppingCartVo.class))),
            @ApiResponse(
                    responseCode = "400", description = "Cart id is invalid",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
            @ApiResponse(
                    responseCode = "404", description = "Cart not found with given id",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
            @ApiResponse(
                    responseCode = "412", description = "Cart not associated with user",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))

    })
    @Parameters(value = {
            @Parameter(name = "id", description = "Cart Id", schema = @Schema(implementation = Integer.class), in = ParameterIn.PATH)
    })
    public ResponseEntity<ShoppingCartVo> getCartById(Authentication authentication, Long id) throws CartException;

    @Operation(method = "GET", summary = "Get all carts of the user", description = "getCarts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all carts",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShoppingCartVo.class)))),
            @ApiResponse(responseCode = "204", description = "No carts found")
    })
    public ResponseEntity<List<ShoppingCartVo>> getCarts(Authentication authentication) throws CartInvalidException;

}
