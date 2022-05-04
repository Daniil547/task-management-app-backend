package io.github.daniil547;

import com.github.javafaker.Faker;
import io.github.daniil547.user.UserProfile;
import io.github.daniil547.user.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class UserProvider extends AbstractProvider<UserProfile> {
    private final UserProfileRepository repo;
    private final Faker faker = new Faker();

    @Autowired
    public UserProvider(UserProfileRepository repo) {
        this.repo = repo;
    }

    public UserProfile ensureExists() {
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(faker.name().firstName());
        userProfile.setLastName(faker.name().lastName());
        userProfile.setUsername(faker.name().username().replace(".", "_"));
        userProfile.setPageDescription(faker.lorem().sentence(10));

        return repo.save(userProfile);
    }
}
