package com.teenthofabud.codingchallenge.ecommerce.user;

import com.teenthofabud.codingchallenge.ecommerce.user.model.UserDto;
import com.teenthofabud.codingchallenge.ecommerce.user.model.UserEntity;
import com.teenthofabud.codingchallenge.ecommerce.user.model.UserRole;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserEntity2DtoConverter implements Converter<UserEntity, UserDto> {
    @Override
    public UserDto convert(UserEntity source) {
        return UserDto.builder()
                .roles(Arrays.stream(source.getRoles().split(",")).map(UserRole::valueOf).toList())
                .passwordHash(source.getPasswordHash())
                .username(source.getUsername())
                .build();
    }
}
