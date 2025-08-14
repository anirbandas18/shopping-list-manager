package com.teenthofabud.codingchallenge.ecommerce.item.model;

import com.teenthofabud.codingchallenge.ecommerce.audit.Audit;
import com.teenthofabud.codingchallenge.ecommerce.audit.AuditListener;
import com.teenthofabud.codingchallenge.ecommerce.category.Category;
import com.teenthofabud.codingchallenge.ecommerce.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditListener.class)
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@Entity(name = "ItemEntity")
@Table(name = "item",
        indexes = {
                @Index(columnList = "name", name = "idx_item_name"),
                @Index(columnList = "category", name = "idx_item_category")
        }
)
public class ItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    protected Long id;

    @Embedded
    private Audit audit;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

}
