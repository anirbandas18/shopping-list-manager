package com.teenthofabud.codingchallenge.ecommerce.user.impl;

import com.teenthofabud.codingchallenge.ecommerce.user.UserEntity;
import com.teenthofabud.codingchallenge.ecommerce.user.UserRepository;
import com.teenthofabud.codingchallenge.ecommerce.user.UserDto;
import com.teenthofabud.codingchallenge.ecommerce.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultUserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public DefaultUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        if (username == null || username.isBlank()) {
            log.warn("Username is null or blank");
            throw new UsernameNotFoundException("Username cannot be null or blank");
        }
        return userRepository.findByUsername(username)
                .map(UserDto::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        log.info("Saving user entity: {}", userEntity);
        if (userEntity == null) {
            log.warn("User entity is null");
            throw new IllegalArgumentException("User entity cannot be null");
        }
        return userRepository.save(userEntity);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting user by ID: {}", id);
        if (id == null) {
            log.warn("ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!userRepository.existsById(id)) {
            log.warn("User with ID {} does not exist", id);
            throw new UsernameNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
        log.info("User with ID {} deleted successfully", id);
    }
}
