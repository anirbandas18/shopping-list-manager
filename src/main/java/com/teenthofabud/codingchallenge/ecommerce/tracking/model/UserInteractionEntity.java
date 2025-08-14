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
@Entity(name = "UserInteractionEntity")
@Table(name = "user_interaction",
        indexes = {
                @Index(columnList = "action", name = "idx_user_interaction_username"),
                @Index(columnList = "item_id", name = "idx_user_interaction_item_id"),
        }
)
public class UserInteractionEntity extends BaseEntity {

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
    private String username;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "list_id", nullable = false)
    private Long listId;

}
