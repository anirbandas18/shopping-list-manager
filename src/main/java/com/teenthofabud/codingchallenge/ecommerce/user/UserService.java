package com.teenthofabud.codingchallenge.ecommerce.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * Saves a new user entity.
     *
     * @param userEntity the user entity to save
     * @return the saved UserEntity
     */
    UserEntity save(UserEntity userEntity);

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    void deleteById(Long id);
}
