package com.teenthofabud.codingchallenge.ecommerce.cart.repository;

import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ShoppingListRepository is responsible for managing ShoppingListEntity objects in the database.
 * It extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingListEntity, Long> {

    /**
     * Finds a list of ShoppingListEntity by username.
     *
     * @param userName the username of the user
     * @return a list of ShoppingListEntity associated with the given username
     */
    List<ShoppingListEntity> findByUsername(String userName);
}
