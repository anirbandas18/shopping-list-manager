package com.teenthofabud.codingchallenge.ecommerce.item;

import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemEntity;
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

    //List<ItemEntity> findByNameStartsWithOrNameContainsOrNameEndsWith(String name);
    //List<ItemEntity> findByNameStartsWith(String name);
    //List<ItemEntity> findByNameEndsWith(String name);
    //List<ItemEntity> findByNameContains(String name);

    /**
     * Finds an ItemProjection by its ID.
     *
     * @param type projection type of the item
     * @return an Optional containing the ItemProjection if found, or empty if not found
     */
    <T> List<T> findBy(Class<T> type);

}
