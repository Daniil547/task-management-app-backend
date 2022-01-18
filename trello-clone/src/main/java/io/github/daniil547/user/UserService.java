package io.github.daniil547.user;

import io.github.daniil547.common.services.PageService;

public interface UserService extends PageService<User> {
    User create(String username, String firstName, String lastname, String email);
}
