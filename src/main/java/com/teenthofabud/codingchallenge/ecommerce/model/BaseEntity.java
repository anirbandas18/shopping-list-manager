package com.teenthofabud.codingchallenge.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Version;
import lombok.Data;

@Data
public abstract class BaseEntity {

    @Version
    @Column(nullable = false)
    protected Short version;

}
