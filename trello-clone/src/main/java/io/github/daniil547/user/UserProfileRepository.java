package io.github.daniil547.user;

import io.github.daniil547.common.repositories.PageRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends PageRepository<UserProfile> {
}
