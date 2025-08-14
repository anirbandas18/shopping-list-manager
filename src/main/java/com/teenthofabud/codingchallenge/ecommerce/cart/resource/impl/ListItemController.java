package com.teenthofabud.codingchallenge.ecommerce.cart.resource.impl;

import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.ListInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ListItemDto;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ListItemForm;
import com.teenthofabud.codingchallenge.ecommerce.cart.resource.ListItemAPI;
import com.teenthofabud.codingchallenge.ecommerce.cart.service.ListItemService;
import com.teenthofabud.codingchallenge.ecommerce.user.model.UserRole;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping(path = "/api/list-item")
public class ListItemController implements ListItemAPI {

    private final ListItemService listItemService;

    public ListItemController(ListItemService listItemService) {
        this.listItemService = listItemService;
    }

    @PreAuthorize("hasAnyRole('" + UserRole.Fields.ROLE_ADMIN + "', '" + UserRole.Fields.ROLE_USER + "')")
    @Observed(name = "list-item.post-for-user-list", contextualName = "list-items.create", lowCardinalityKeyValues = {"shopping-list.list-item", "create"})
    @PostMapping(value = "/shopping-list/{list-id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Void> postListItem(Authentication authentication, @PathVariable(name = "list-id") Long listId, @Valid @RequestBody ListItemForm form) throws ListException {
        log.debug("Received request to add item {} to list with id: {} for user: {}", form, listId, authentication.getName());
        ListItemDto dto = ListItemDto.builder().listId(listId).username(authentication.getName()).form(form).build();
        log.debug("Converted ListItemForm to ListItemDto: {}", dto);
        log.info("Adding item to list with ID: {} for user: {}", dto.getListId(), dto.getUsername());
        Long id = listItemService.addItemToList(dto);
        log.info("Item added to list: {} with ID: {} for user: {}", dto.getListId(), id, dto.getUsername());
        log.debug("Returning response with status 201 for adding item to list");
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasAnyRole('" + UserRole.Fields.ROLE_ADMIN + "', '" + UserRole.Fields.ROLE_USER + "')")
    @Observed(name = "list-item.delete-from-user-list", contextualName = "list-items.remove", lowCardinalityKeyValues = {"shopping-list.list-item", "delete"})
    @DeleteMapping(value = "/shopping-list/{list-id}")
    @Override
    public ResponseEntity<Void> deleteListItem(Authentication authentication, @PathVariable(name = "list-id") Long listId, @RequestParam Long id) throws ListException {
        if(id == null || id <= 0) {
            log.error("Invalid item ID: {}", id);
            throw new ListInvalidException("item", "id", id);
        }
        log.debug("Received request to delete item with id: {} from list with id: {} for user: {}", id, listId, authentication.getName());
        ListItemDto dto = ListItemDto.builder().listId(listId).username(authentication.getName()).build();
        listItemService.removeItemFromList(dto, id);
        log.info("Item with ID: {} removed from list with ID: {} for user: {}", id, listId, authentication.getName());
        log.debug("Returning response with status 204 for deleting item from list");
        return ResponseEntity.noContent().build();
    }
}
