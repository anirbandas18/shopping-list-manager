package com.teenthofabud.codingchallenge.ecommerce.category;

import com.teenthofabud.codingchallenge.ecommerce.model.ErrorVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ApiResponses(value = {
        @ApiResponse(
                responseCode = "401", description = "Unauthorized access",
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
        @ApiResponse(
                responseCode = "500", description = "Server error",
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))
})
@Tag(name = "CategoryAPI", description = "View categories")
public interface CategoryAPI {

    @Operation(method = "GET", summary = "Get all categories", description = "getCategories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all categories",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryVo.class)))),
            @ApiResponse(responseCode = "204", description = "No categories found")
    })
    public ResponseEntity<List<CategoryVo>> getCategories();


}
