package com.teenthofabud.codingchallenge.ecommerce.tracking.repository;

import com.teenthofabud.codingchallenge.ecommerce.tracking.model.ItemInteractionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInteractionRepository extends JpaRepository<ItemInteractionEntity, Long> {

}
