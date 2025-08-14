package com.teenthofabud.codingchallenge.ecommerce.tracking.model;

import com.teenthofabud.codingchallenge.ecommerce.audit.Audit;
import com.teenthofabud.codingchallenge.ecommerce.audit.AuditListener;
import com.teenthofabud.codingchallenge.ecommerce.model.BaseEntity;
import com.teenthofabud.codingchallenge.ecommerce.tracking.InteractionType;
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
@Entity(name = "ItemInteractionEntity")
@Table(name = "item_interaction",
        indexes = {
                @Index(columnList = "action", name = "idx_item_interaction_action"),
                @Index(columnList = "item_id", name = "idx_item_interaction_item_id"),
        }
)
public class ItemInteractionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    protected Long id;

    @Embedded
    private Audit audit;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private InteractionType action;

    @Column(nullable = false)
    private Long counter;

    @Column(name = "item_id", nullable = false)
    private Long itemId;


}
