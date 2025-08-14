package com.teenthofabud.codingchallenge.ecommerce.user.impl;

import com.teenthofabud.codingchallenge.ecommerce.user.UserEntity2DtoConverter;
import com.teenthofabud.codingchallenge.ecommerce.user.UserRepository;
import com.teenthofabud.codingchallenge.ecommerce.user.UserService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Default implementation of UserService interface.
 * This class provides methods to load user details by username.
 */
@Service
@Slf4j
public class DefaultUserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserEntity2DtoConverter userEntity2DtoConverter;

    public DefaultUserServiceImpl(UserEntity2DtoConverter userEntity2DtoConverter, UserRepository userRepository) {
        this.userEntity2DtoConverter = userEntity2DtoConverter;
        this.userRepository = userRepository;
    }

    @Observed(name = "user.load-by-username", contextualName = "users.security", lowCardinalityKeyValues = {"shopping-list.user", "default"})
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        if (username == null || username.isBlank()) {
            log.warn("Username is null or blank");
            throw new UsernameNotFoundException("Username cannot be null or blank");
        }
        return userRepository.findByUsername(username)
                .map(userEntity2DtoConverter::convert)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

}
