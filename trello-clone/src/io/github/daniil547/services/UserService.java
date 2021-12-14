package io.github.daniil547.services;

import io.github.daniil547.domain.Member;
import io.github.daniil547.domain.User;

public class UserService extends ResourceService<User> {

    public User create(String username, String fullName) {
        User user = new User();

        super.create(user, new Member(null) /* :( */);
        /* FIXME: Was like this, got infinite recursion. We REALLY should refactor our hierarchy
            super.create(user, new Member(user));

         */
        user.setUsername(username);
        user.setFullName(fullName);

        return user;
    }
}
