package io.github.daniil547.user;

import io.github.daniil547.common.services.ResourceService;
import io.github.daniil547.user.member.Member;

public class UserService extends ResourceService<User> {

    public User create(String username, String firstName, String lastname) {
        User user = new User();

        super.create(user, new Member(null) /* :( */, username, "Your Profile", "Your bio or whatever");
        /* FIXME: Was like this, got infinite recursion. We REALLY should refactor our hierarchy
            super.create(user, new Member(user));

         */
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastname);

        return user;
    }
}
