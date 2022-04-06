package io.github.daniil547.user;

import io.github.daniil547.common.services.PageService;

public interface UserService extends PageService<UserDto, User> {
    @Override
    UserRepository repository();
}
