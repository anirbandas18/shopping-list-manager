package com.teenthofabud.codingchallenge.ecommerce.cart.resource.impl;

import com.teenthofabud.codingchallenge.ecommerce.cart.exception.CartException;
import com.teenthofabud.codingchallenge.ecommerce.cart.exception.CartInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingCartDto;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingCartVo;
import com.teenthofabud.codingchallenge.ecommerce.cart.resource.ShoppingCartAPI;
import com.teenthofabud.codingchallenge.ecommerce.cart.service.ShoppingCartService;
import com.teenthofabud.codingchallenge.ecommerce.user.model.UserRole;
import io.micrometer.observation.annotation.Observed;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/shopping-cart")
public class ShoppingCartController implements ShoppingCartAPI {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @RolesAllowed({
            UserRole.Fields.ROLE_ADMIN + UserRole.Fields.ROLE_USER
    })
    @Observed(name = "shopping-cart.post-for-user", contextualName = "shopping-carts.create")
    @PostMapping
    @Override
    public ResponseEntity<Void> postCart(Authentication authentication) throws CartException {
        log.debug("Creating new shopping cart for user: {}", authentication.getName());
        Long id = shoppingCartService.createShoppingCart(authentication.getName());
        log.info("Created new shopping cart with ID: {}", id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Void> deleteCart(Authentication authentication, Long id) throws CartException {
        return null;
    }

    @Override
    public ResponseEntity<Void> putCart(Authentication authentication, Long id) throws CartException {
        return null;
    }

    @RolesAllowed({
            UserRole.Fields.ROLE_ADMIN + UserRole.Fields.ROLE_USER
    })
    @Observed(name = "shopping-cart.load-by-id-for-user", contextualName = "shopping-carts.find-specifically")
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ShoppingCartVo> getCartById(Authentication authentication, @PathVariable Long id) throws CartException {
        log.debug("Received request to get shopping cart by id: {} for user: {}", id, authentication.getName());
        ShoppingCartDto dto = ShoppingCartDto.builder().id(id).username(authentication.getName()).build();
        ShoppingCartVo shoppingCartVo = shoppingCartService.getShoppingCart(dto);
        log.debug("Shopping cart retrieved successfully: {}", shoppingCartVo);
        return ResponseEntity.ok(shoppingCartVo);
    }

    @RolesAllowed({
            UserRole.Fields.ROLE_ADMIN + UserRole.Fields.ROLE_USER
    })
    @Observed(name = "shopping-cart.load-all-for-user", contextualName = "shopping-carts.find-all")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<ShoppingCartVo>> getCarts(Authentication authentication) throws CartInvalidException {
        log.debug("Received request to get all shopping carts for user: {}", authentication.getName());
        List<ShoppingCartVo> shoppingCartVoList = shoppingCartService.getShoppingCarts(authentication.getName());
        if(shoppingCartVoList.isEmpty()) {
            log.debug("No shopping carts found");
            return ResponseEntity.noContent().build();
        }
        log.debug("Shopping carts retrieved successfully: {}", shoppingCartVoList);
        return ResponseEntity.ok(shoppingCartVoList);
    }
}
