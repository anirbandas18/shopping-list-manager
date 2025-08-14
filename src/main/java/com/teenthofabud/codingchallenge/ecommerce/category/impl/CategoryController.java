package com.teenthofabud.codingchallenge.ecommerce.category.impl;

import com.teenthofabud.codingchallenge.ecommerce.category.Category;
import com.teenthofabud.codingchallenge.ecommerce.category.CategoryAPI;
import com.teenthofabud.codingchallenge.ecommerce.category.CategoryVo;
import com.teenthofabud.codingchallenge.ecommerce.user.model.UserRole;
import io.micrometer.observation.annotation.Observed;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * CategoryController is a REST controller that handles requests related to categories.
 * It provides an endpoint to retrieve all available categories.
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/category")
public class CategoryController implements CategoryAPI {

    /**
     * Retrieves all available categories.
     * This endpoint is accessible to users with ADMIN or USER roles.
     *
     * @return ResponseEntity containing a list of CategoryVo objects representing the available categories
     */
    @PreAuthorize("hasAnyRole('" + UserRole.Fields.ROLE_ADMIN + "', '" + UserRole.Fields.ROLE_USER + "')")
    @Observed(name = "category.load-all-categories", contextualName = "categories.find-all", lowCardinalityKeyValues = {"shopping-list.category", "get-all"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<CategoryVo>> getCategories() {
        log.debug("Received request to get all categories");
        List<CategoryVo> categoryVoList = Arrays.stream(Category.values())
                .map(e -> CategoryVo.builder().name(e.name()).ordinal(e.ordinal()).build())
                .toList();
        log.debug("{} categories available", categoryVoList.size());
        log.debug("Categories retrieved successfully: {}", categoryVoList);
        return ResponseEntity.ok(categoryVoList);
    }
}
