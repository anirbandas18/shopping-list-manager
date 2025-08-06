package com.teenthofabud.codingchallenge.ecommerce.user;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum UserRole {

    @FieldNameConstants.Include ROLE_USER,
    @FieldNameConstants.Include ROLE_ADMIN;

}
