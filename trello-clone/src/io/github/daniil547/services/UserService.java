package io.github.daniil547.services;

import io.github.daniil547.domain.Member;
import io.github.daniil547.domain.User;

public class UserService extends ResourceService<User> {

    public User create(String username, String fullName) {
        User user = new User();
        super.create(user, new Member(user) /* a stab, we should refactor our hierarchy */);
        user.setUsername(username);
        user.setFullName(fullName);

        return user;
    }
}
