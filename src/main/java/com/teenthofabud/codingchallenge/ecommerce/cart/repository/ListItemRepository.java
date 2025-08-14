package com.teenthofabud.codingchallenge.ecommerce.cart.repository;

import com.teenthofabud.codingchallenge.ecommerce.cart.model.ListItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ListItemRepository is responsible for managing ItemEntity objects in the database.
 * It extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface ListItemRepository extends JpaRepository<ListItemEntity, Long> {

    /**
     * Finds the exact item in the cart with the specified listId.
     *
     * @param itemId the ID of the item to retrieve
     * @param listId the ID of the list to retrieve the item from
     * @return a probable ListItemEntity object representing the item in the list
     */
    public Optional<ListItemEntity> findByItemIdAndListId(Long itemId, Long listId);
}
