package com.teenthofabud.codingchallenge.ecommerce.model;

import com.teenthofabud.codingchallenge.ecommerce.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Version;
import lombok.Data;

@Data
public abstract class BaseEntity implements Auditable {

    @Version
    @Column(nullable = false)
    protected Short version;

}
