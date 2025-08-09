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
     * Finds a list of ShoppingCartEntity by username.
     *
     * @param userName the username of the user
     * @return a list of ShoppingCartEntity associated with the given username
     */
    List<ShoppingCartEntity> findByUsername(String userName);
}
