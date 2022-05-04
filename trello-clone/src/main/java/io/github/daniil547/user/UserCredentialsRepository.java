package io.github.daniil547.user;

import io.github.daniil547.common.repositories.DomainRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends DomainRepository<UserCredentials> {
    @Query("SELECT uc FROM UserCredentials uc WHERE uc.email = ?1 or uc.username = ?1")
    Optional<UserCredentials> findByIdentifier(String identifier);
}
