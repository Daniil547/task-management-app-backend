package io.github.daniil547.user;

import io.github.daniil547.common.services.DefaultPageService;

public class DefaultUserService extends DefaultPageService<User> implements UserService {

    UserRepository repo;

    public DefaultUserService(UserRepository userRepository) {
        this.repo = userRepository;
    }

    public User create(String username, String firstName, String lastname, String email) {
        User user = new User();

        super.init(user, username, firstName + " " + lastname + "'s Profile", "");

        user.setFirstName(firstName);
        user.setLastName(lastname);
        user.setEmail(email);

        return save(user);
    }

    @Override
    protected UserRepository getRepository() {
        return this.repo;
    }
}
