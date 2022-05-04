package io.github.daniil547.user;

import io.github.daniil547.common.services.PageConverter;
import org.springframework.stereotype.Component;

@Component
public class UserProfileConverter extends PageConverter<UserProfileDto, UserProfile> {

    @Override
    protected UserProfile transferEntitySpecificFieldsFromDto(UserProfileDto dto) {
        UserProfile userProfile = new UserProfile();

        userProfile.setFirstName(dto.getFirstName());
        userProfile.setLastName(dto.getLastName());

        return userProfile;
    }

    @Override
    protected UserProfileDto transferDtoSpecificFieldsFromEntity(UserProfile entity) {
        UserProfileDto dto = new UserProfileDto();

        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());

        return dto;
    }
}