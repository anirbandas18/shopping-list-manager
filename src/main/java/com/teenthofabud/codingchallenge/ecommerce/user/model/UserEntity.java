package com.teenthofabud.codingchallenge.ecommerce.user.model;

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
@Entity(name = "UserEntity")
@Table(name = "users",
        indexes = {
                @Index(columnList = "username", name = "idx_users_username")
        }
)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    protected Long id;

    @Embedded
    private Audit audit;

    @Column(nullable = false)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String roles;

}
