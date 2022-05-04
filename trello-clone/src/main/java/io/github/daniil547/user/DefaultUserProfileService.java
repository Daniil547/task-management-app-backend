package io.github.daniil547.user;

import io.github.daniil547.common.services.DefaultPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserProfileService extends DefaultPageService<UserProfileDto, UserProfile> implements UserProfileService {

    private final UserProfileRepository repo;
    private final UserSearchQueryParser searchQueryParser;

    @Autowired
    public DefaultUserProfileService(UserProfileRepository userProfileRepository, UserSearchQueryParser searchQueryParser) {
        this.repo = userProfileRepository;
        this.searchQueryParser = searchQueryParser;
    }

    @Override
    public UserProfileRepository repository() {
        return this.repo;
    }

    @Override
    public UserSearchQueryParser searchQueryParser() {
        return this.searchQueryParser;
    }
}
