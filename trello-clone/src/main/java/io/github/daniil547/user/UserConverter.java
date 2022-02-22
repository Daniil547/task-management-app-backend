package io.github.daniil547.user;

import io.github.daniil547.common.services.PageConverter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends PageConverter<UserDto, User> {
    public UserConverter() {
    }

    @Override
    protected User transferEntitySpecificFieldsFromDto(UserDto dto) {
        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        return user;
    }

    @Override
    protected UserDto transferDtoSpecificFieldsFromEntity(User entity) {
        UserDto dto = new UserDto();

        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());

        return dto;
    }
}