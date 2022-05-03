package io.github.daniil547.user;

import io.github.daniil547.common.repositories.DomainRepository;
import io.github.daniil547.common.services.DefaultDomainService;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialsService extends DefaultDomainService<UserCredentialsDto, UserCredentials> {
    @Override
    public DomainRepository<UserCredentials> repository() {
        return null;
    }
}
