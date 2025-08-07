package com.teenthofabud.codingchallenge.ecommerce.item.impl;

import com.teenthofabud.codingchallenge.ecommerce.item.ItemAPI;
import com.teenthofabud.codingchallenge.ecommerce.item.ItemService;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemException;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemForm;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemVo;
import com.teenthofabud.codingchallenge.ecommerce.user.model.UserRole;
import io.micrometer.observation.annotation.Observed;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controller for handling item-related requests.
 * Provides endpoints to add, retrieve, and list items.
 */
@Slf4j
@RestController
@RequestMapping(path = "api/item")
public class ItemController implements ItemAPI {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RolesAllowed({
            UserRole.Fields.ROLE_ADMIN
    })
    @Observed(name = "item.post-with-details", contextualName = "items.create")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Void> postItem(@RequestBody @Valid ItemForm itemForm) throws ItemException {
        log.debug("Received item form: {}", itemForm);
        Long id = itemService.addItem(itemForm);
        log.debug("Item posted successfully");
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @RolesAllowed({
            UserRole.Fields.ROLE_ADMIN,
            UserRole.Fields.ROLE_USER
    })
    @Observed(name = "item.load-by-id", contextualName = "items.find-specifically")
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ItemVo> getItemById(@PathVariable Long id) throws ItemException {
        log.debug("Received request to get item by id: {}", id);
        ItemVo itemVo = itemService.getItemById(id);
        log.debug("Item retrieved successfully: {}", itemVo);
        return ResponseEntity.ok(itemVo);
    }

    @RolesAllowed({
            UserRole.Fields.ROLE_ADMIN,
            UserRole.Fields.ROLE_USER
    })
    @Observed(name = "item.load-all-items", contextualName = "items.find-all")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<ItemVo>> getItems() {
        log.debug("Received request to get all items");
        List<ItemVo> itemVoList = itemService.getItems();
        if(itemVoList.isEmpty()) {
            log.debug("No items found");
            return ResponseEntity.noContent().build();
        }
        log.debug("Items retrieved successfully: {}", itemVoList);
        return ResponseEntity.ok(itemVoList);
    }
}
