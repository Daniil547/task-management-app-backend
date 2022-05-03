package io.github.daniil547.user;

import io.github.daniil547.common.services.DomainConverter;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialsConverter extends DomainConverter<UserCredentialsDto, UserCredentials> {
    @Override
    protected UserCredentials transferEntitySpecificFieldsFromDto(UserCredentialsDto dto) {
        UserCredentials userCredentials = new UserCredentials();

        userCredentials.setUsername(dto.getUsername());
        userCredentials.setEmail(dto.getEmail());
        userCredentials.setPassword(dto.getPassword());

        return userCredentials;
    }

    @Override
    protected UserCredentialsDto transferDtoSpecificFieldsFromEntity(UserCredentials entity) {
        UserCredentialsDto dto = new UserCredentialsDto();

        dto.setUsername(dto.getUsername());
        dto.setEmail(dto.getEmail());
        dto.setPassword(dto.getPassword());

        return dto;
    }
}
