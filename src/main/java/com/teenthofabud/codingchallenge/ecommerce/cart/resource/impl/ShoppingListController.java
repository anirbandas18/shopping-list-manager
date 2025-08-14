package com.teenthofabud.codingchallenge.ecommerce.cart.resource.impl;

import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingListDto;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingListVo;
import com.teenthofabud.codingchallenge.ecommerce.cart.resource.ShoppingListAPI;
import com.teenthofabud.codingchallenge.ecommerce.cart.service.ShoppingListService;
import com.teenthofabud.codingchallenge.ecommerce.user.model.UserRole;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/shopping-list")
public class ShoppingListController implements ShoppingListAPI {

    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @PreAuthorize("hasAnyRole('" + UserRole.Fields.ROLE_ADMIN + "', '" + UserRole.Fields.ROLE_USER + "')")
    @Observed(name = "shopping-list.post-for-user", contextualName = "shopping-lists.create", lowCardinalityKeyValues = {"shopping-list", "create"})
    @PostMapping
    @Override
    public ResponseEntity<Void> postList(Authentication authentication) throws ListException {
        log.debug("Creating new shopping list for user: {}", authentication.getName());
        Long id = shoppingListService.createShoppingList(authentication.getName());
        log.info("Created new shopping list with ID: {}", id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasAnyRole('" + UserRole.Fields.ROLE_ADMIN + "', '" + UserRole.Fields.ROLE_USER + "')")
    @Observed(name = "shopping-list.load-by-id-for-user", contextualName = "shopping-lists.find-specifically", lowCardinalityKeyValues = {"shopping-list", "get-by-id"})
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ShoppingListVo> getListById(Authentication authentication, @PathVariable Long id) throws ListException {
        log.debug("Received request to get shopping list by id: {} for user: {}", id, authentication.getName());
        ShoppingListDto dto = ShoppingListDto.builder().id(id).username(authentication.getName()).build();
        ShoppingListVo shoppingListVo = shoppingListService.getShoppingList(dto);
        log.debug("Shopping list retrieved successfully: {}", shoppingListVo);
        return ResponseEntity.ok(shoppingListVo);
    }

    @PreAuthorize("hasAnyRole('" + UserRole.Fields.ROLE_ADMIN + "', '" + UserRole.Fields.ROLE_USER + "')")
    @Observed(name = "shopping-list.load-all-for-user", contextualName = "shopping-lists.find-all", lowCardinalityKeyValues = {"shopping-list", "get-all"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<ShoppingListVo>> getLists(Authentication authentication) throws ListInvalidException {
        log.debug("Received request to get all shopping lists for user: {}", authentication.getName());
        List<ShoppingListVo> shoppingListVoList = shoppingListService.getShoppingLists(authentication.getName());
        if(shoppingListVoList.isEmpty()) {
            log.debug("No shopping lists found");
            return ResponseEntity.noContent().build();
        }
        log.debug("Shopping lists retrieved successfully: {}", shoppingListVoList);
        return ResponseEntity.ok(shoppingListVoList);
    }
}
