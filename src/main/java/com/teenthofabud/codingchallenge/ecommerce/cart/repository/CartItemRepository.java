package com.teenthofabud.codingchallenge.ecommerce.cart.repository;

import com.teenthofabud.codingchallenge.ecommerce.cart.model.CartItemEntity;
import jakarta.validation.constraints.Min;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CartItemRepository is responsible for managing ItemEntity objects in the database.
 * It extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    /**
     * Checks if an item with the specified itemId exists in the cart with the specified cartId.
     *
     * @param itemId the ID of the item to check
     * @param cartId the ID of the cart to check
     * @return true if the item exists in the cart, false otherwise
     */
    boolean existsByItemIdAndCartId(Long itemId, Long cartId);

    /**
     * Finds all items in the cart with the specified cartId.
     *
     * @param cartId the ID of the cart to retrieve items from
     * @return a list of CartItemEntity objects representing the items in the cart
     */
    public List<CartItemEntity> findByCartId(Long cartId);
}
