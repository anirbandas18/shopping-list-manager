package com.teenthofabud.codingchallenge.ecommerce.item;

import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemException;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemVo;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemForm;
import com.teenthofabud.codingchallenge.ecommerce.model.ErrorVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

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
@Tag(name = "ItemAPI", description = "Manage items")
public interface ItemAPI {

    @Operation(method = "POST", summary = "Accept new item", description = "postItem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Accepted new item"),
            @ApiResponse(
                    responseCode = "400", description = "Item not accepted due to invalid data",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
            @ApiResponse(
                    responseCode = "409", description = "Item already exists with same name",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))

    })
    public ResponseEntity<Void> postItem(
            @RequestBody(description = "Item details", required = true, content = @Content(schema = @Schema(implementation = ItemForm.class))) ItemForm itemForm) throws ItemException;


    @Operation(method = "GET", summary = "Get item details by Id", description = "getItemById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved item details by id",
                    content = @Content(schema = @Schema(implementation = ItemVo.class))),
            @ApiResponse(
                    responseCode = "400", description = "Item id is invalid",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
            @ApiResponse(
                    responseCode = "404", description = "Item not found with given id",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))

    })
    @Parameters(value = {
            @Parameter(name = "id", description = "Item Id", schema = @Schema(implementation = Integer.class), in = ParameterIn.PATH)
    })
    public ResponseEntity<ItemVo> getItemById(Long id) throws ItemException;

    @Operation(method = "GET", summary = "Get all items", description = "getItems")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all items",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemVo.class)))),
            @ApiResponse(responseCode = "204", description = "No items found")
    })
    public ResponseEntity<List<ItemVo>> getItems();


}
