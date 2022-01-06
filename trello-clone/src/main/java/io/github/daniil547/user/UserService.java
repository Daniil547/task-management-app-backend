package io.github.daniil547.user;

import io.github.daniil547.common.services.PageService;

public class UserService extends PageService<User> {

    public User create(String username, String firstName, String lastname) {
        User user = new User();

        super.create(user, username, "Your Profile", "Your bio or whatever");

        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastname);

        return user;
    }
}
