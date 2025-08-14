package com.teenthofabud.codingchallenge.ecommerce.cart.model;

import com.teenthofabud.codingchallenge.ecommerce.audit.Audit;
import com.teenthofabud.codingchallenge.ecommerce.audit.AuditListener;
import com.teenthofabud.codingchallenge.ecommerce.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.List;

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
@Entity(name = "ShoppingListEntity")
@Table(name = "shopping_list")
public class ShoppingListEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    protected Long id;

    @Embedded
    private Audit audit;

    @Column(nullable = false)
    private String username;

    @Builder.Default
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "list",
            cascade = CascadeType.ALL, orphanRemoval = true
    )
    private List<ListItemEntity> listItems = new ArrayList<>();

    public ShoppingListEntity addItemToList(ListItemEntity listItem) {
        listItems.add(listItem);
        listItem.setList(this);
        return this;
    }

    public ShoppingListEntity removeItemFromList(ListItemEntity listItem) {
        listItems.remove(listItem);
        listItem.setList(null);
        return this;
    }

}
