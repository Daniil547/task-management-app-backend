package io.github.daniil547.user;

import io.github.daniil547.common.services.DomainConverter;

public class UserConverter extends DomainConverter<UserDto, User> {
    @Override
    protected User transferEntitySpecificFieldsFromDto(UserDto dto) {
        return new User();
    }

    @Override
    protected UserDto transferDtoSpecificFieldsFromEntity(User entity) {
        return new UserDto();
    }
}
