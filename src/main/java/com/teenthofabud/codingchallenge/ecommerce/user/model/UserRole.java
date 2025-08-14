package com.teenthofabud.codingchallenge.ecommerce.user.model;

import lombok.experimental.FieldNameConstants;

import java.util.Arrays;

@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum UserRole {

    @FieldNameConstants.Include ROLE_USER,
    @FieldNameConstants.Include ROLE_ADMIN;

    public static String getRolesAsConcatenatedString() {
        return String.join(",", Arrays.stream(UserRole.values()).map(UserRole::name).toList());
    }

}
