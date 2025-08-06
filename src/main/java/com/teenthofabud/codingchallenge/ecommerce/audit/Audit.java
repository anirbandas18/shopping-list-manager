package com.teenthofabud.codingchallenge.ecommerce.audit;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Embeddable
@Access(AccessType.FIELD)
@DynamicUpdate
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Audit {

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @Column(name = "modification_time")
    private LocalDateTime modificationTime;

    @Column(name = "modified_by")
    private String modifiedBy;

}
