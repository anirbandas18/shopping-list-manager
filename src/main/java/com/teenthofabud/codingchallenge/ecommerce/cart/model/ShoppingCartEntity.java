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
@Entity(name = "ShoppingCartEntity")
@Table(name = "shopping_cart")
public class ShoppingCartEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    protected Long id;

    @Embedded
    private Audit audit;

    @Column(nullable = false)
    private String username;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "cart",
            cascade = CascadeType.ALL, orphanRemoval = true
    )
    private List<CartItemEntity> cartItems = new ArrayList<>();

    public ShoppingCartEntity addItemToCart(CartItemEntity cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
        return this;
    }

    public ShoppingCartEntity removeItemFromCart(CartItemEntity cartItem) {
        cartItems.remove(cartItem);
        cartItem.setCart(null);
        return this;
    }

}
