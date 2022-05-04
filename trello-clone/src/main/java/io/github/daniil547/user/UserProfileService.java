package io.github.daniil547.user;

import io.github.daniil547.common.services.PageService;

public interface UserProfileService extends PageService<UserProfileDto, UserProfile> {
    @Override
    UserProfileRepository repository();
}
