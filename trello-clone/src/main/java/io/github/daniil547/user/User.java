package io.github.daniil547.user;

import io.github.daniil547.common.domain.Page;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends Page {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String about;

    @Override
    public String getPageName() {
        return getUsername();
    }

    @Override
    public void setPageName(String username) {
        setUsername(username);
    }

    @Override
    public String getDescription() {
        return getAbout();
    }

    @Override
    public void setDescription(String about) {
        setAbout(about);
    }
}
