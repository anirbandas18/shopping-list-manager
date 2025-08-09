package com.teenthofabud.codingchallenge.ecommerce.cart.model;

import com.teenthofabud.codingchallenge.ecommerce.audit.Audit;
import com.teenthofabud.codingchallenge.ecommerce.audit.AuditListener;
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
@Entity(name = "CartItemEntity")
@Table(name = "cart_item")
public class CartItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    protected Long id;

    @Embedded
    private Audit audit;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShoppingCartEntity cart;

    @Column(nullable = false)
    private Integer quantity;

}
