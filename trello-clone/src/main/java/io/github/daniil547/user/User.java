package io.github.daniil547.user;

import io.github.daniil547.common.domain.Page;
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
@Table(name = "users")
@AttributeOverrides({@AttributeOverride(name = "pageName",
                                        column = @Column(name = "username",
                                                         unique = true,
                                                         nullable = false)),
                     @AttributeOverride(name = "pageDescription",
                                        column = @Column(name = "about",
                                                         length = 350)),
                     @AttributeOverride(name = "pageTitle",
                                        column = @Column(name = "profile_title",
                                                         length = 50))})

public class User extends Page {
    @Column(nullable = false,
            length = 20)
    private String firstName;
    @Column(nullable = false,
            length = 20)
    private String lastName;
    @Column(unique = true,
            nullable = false,
            length = 254)
    private String email;

    public String getUsername() {
        return getPageName();
    }

    public void setUsername(String username) {
        setPageName(username);
    }

    public String getAbout() {
        return getPageDescription();
    }

    public void setAbout(String about) {
        setPageDescription(about);
    }
}
