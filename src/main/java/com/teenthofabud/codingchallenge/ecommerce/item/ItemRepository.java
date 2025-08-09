package com.teenthofabud.codingchallenge.ecommerce.item;

import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemEntity;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing ItemEntity objects.
 * This interface extends JpaRepository to provide CRUD operations for ItemEntity.
 */
@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    /**
     * Finds an ItemEntity by its name.
     *
     * @param name the name of the item
     * @return an Optional containing the ItemEntity if found, or empty if not found
     */
    Optional<ItemEntity> findByName(String name);

    /**
     * Finds all ItemProjection objects.
     * This method retrieves all items as projections, which are lightweight representations of the item data.
     *
     * @return a list of ItemProjection objects
     */
    <T> List<T> findAllProjectedBy(Class<T> type);

}
