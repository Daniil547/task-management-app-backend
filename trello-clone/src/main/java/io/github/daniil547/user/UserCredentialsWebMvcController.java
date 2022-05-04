package io.github.daniil547.user;

import io.github.daniil547.common.controllers.DomainWebMvcController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users-credentials")
@Api(tags = "UserProfile Resource", description = "CRUD endpoint for users")
public class UserCredentialsWebMvcController extends DomainWebMvcController<UserCredentialsDto, UserCredentials> {
    private final UserCredentialsService service;
    private final UserCredentialsConverter converter;

    @Autowired
    public UserCredentialsWebMvcController(UserCredentialsService service, UserCredentialsConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @Override
    public UserCredentialsService service() {
        return this.service;
    }

    @Override
    public UserCredentialsConverter converter() {
        return this.converter;
    }

    @Override
    protected ResponseEntity<Page<UserCredentialsDto>> handleGetAllWithRespectToUser(UUID userId, String search, Pageable pageable) {
        // GET is not allowed for this endpoint, at all
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
