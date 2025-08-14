package com.teenthofabud.codingchallenge.ecommerce.cart.model;

import com.teenthofabud.codingchallenge.ecommerce.audit.Audit;
import com.teenthofabud.codingchallenge.ecommerce.audit.AuditListener;
import com.teenthofabud.codingchallenge.ecommerce.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditListener.class)
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@Entity(name = "ListItemEntity")
@Table(name = "list_item")
public class ListItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    protected Long id;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Embedded
    private Audit audit;

    @Column(name = "item_id", nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShoppingListEntity list;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer quantity;

}
