package io.github.daniil547;

import com.github.javafaker.Faker;
import io.github.daniil547.user.User;
import io.github.daniil547.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class UserProvider extends AbstractProvider<User> {
    private final UserRepository repo;
    private final Faker faker = new Faker();

    @Autowired
    public UserProvider(UserRepository repo) {
        this.repo = repo;
    }

    public User ensureExists() {
        User user = new User();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setUsername(faker.name().username().replace(".", "_"));
        user.setEmail(faker.internet().safeEmailAddress());
        user.setPageDescription(faker.lorem().sentence(10));

        return repo.save(user);
    }
}
