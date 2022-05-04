package io.github.daniil547.user;

import io.github.daniil547.common.domain.Domain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@Entity
@Table(name = "users_credentials")
@AttributeOverrides({@AttributeOverride(name = "id",
                                        column = @Column(name = "user_id",
                                                         unique = true,
                                                         columnDefinition = "user_id uuid primary key references users(id)"))})
public class UserCredentials extends Domain {
    @Column(name = "username",
            unique = true,
            nullable = false)
    private String username;

    @Column(unique = true,
            nullable = false,
            length = 254)
    private String email;

    @Column(nullable = false,
            length = 256)
    private String password;
}
