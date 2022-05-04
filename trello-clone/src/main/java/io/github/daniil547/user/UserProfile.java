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
@Table(name = "users_profiles")
@AttributeOverrides({@AttributeOverride(name = "id",
                                        column = @Column(name = "user_id",
                                                         unique = true,
                                                         columnDefinition = "user_id uuid primary key references users(id)")),
                     @AttributeOverride(name = "pageDescription",
                                        column = @Column(name = "about",
                                                         length = 350)),
                     @AttributeOverride(name = "pageTitle",
                                        column = @Column(name = "profile_title",
                                                         length = 50)),
                     @AttributeOverride(name = "pageName",
                                        column = @Column(name = "profile_page_name",
                                                         length = 30))})
public class UserProfile extends Page {
    @Column(nullable = false,
            length = 20)
    private String firstName;
    @Column(nullable = false,
            length = 20)
    private String lastName;

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
