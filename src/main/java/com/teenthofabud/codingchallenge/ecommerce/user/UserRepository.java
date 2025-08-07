package com.teenthofabud.codingchallenge.ecommerce.user;

import com.teenthofabud.codingchallenge.ecommerce.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing UserEntity objects.
 * This interface extends JpaRepository to provide CRUD operations for UserEntity.
 * It includes a method to find a user by their username.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return an Optional containing the UserEntity if found, or empty if not found
     */
    Optional<UserEntity> findByUsername(String username);
}
