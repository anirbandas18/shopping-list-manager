package com.teenthofabud.codingchallenge.ecommerce.cart.repository;

import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ShoppingCartRepository is responsible for managing ShoppingCartEntity objects in the database.
 * It extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {

    /**
     * Finds a list of ShoppingCartEntity objects by the username.
     *
     * @param userName the username associated with the shopping carts
     * @return a list of ShoppingCartEntity objects
     */
    List<ShoppingCartEntity> findByUsername(String userName);
}
