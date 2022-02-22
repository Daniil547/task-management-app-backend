package io.github.daniil547.user;

import io.github.daniil547.common.services.DefaultPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService extends DefaultPageService<UserDto, User> implements UserService {

    private final UserRepository repo;
    private final UserSearchQueryParser searchQueryParser;

    @Autowired
    public DefaultUserService(UserRepository userRepository, UserSearchQueryParser searchQueryParser) {
        this.repo = userRepository;
        this.searchQueryParser = searchQueryParser;
    }

    @Override
    public UserRepository repository() {
        return this.repo;
    }

    @Override
    public UserSearchQueryParser searchQueryParser() {
        return this.searchQueryParser;
    }
}
