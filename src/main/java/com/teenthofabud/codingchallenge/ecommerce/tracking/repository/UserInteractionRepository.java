package com.teenthofabud.codingchallenge.ecommerce.tracking.repository;

import com.teenthofabud.codingchallenge.ecommerce.tracking.model.UserInteractionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInteractionRepository extends JpaRepository<UserInteractionEntity, Long> {

}
