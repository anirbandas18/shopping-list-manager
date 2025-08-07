package com.teenthofabud.codingchallenge.ecommerce.item;

import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing ItemEntity objects.
 * This interface extends JpaRepository to provide CRUD operations for ItemEntity.
 */
@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    Optional<ItemEntity> findByName(String name);

}
