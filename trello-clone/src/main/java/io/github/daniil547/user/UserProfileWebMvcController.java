package io.github.daniil547.user;


import io.github.daniil547.common.controllers.PageWebMvcController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users-profiles")
@Api(tags = "UserProfile Resource", description = "CRUD endpoint for users")
public class UserProfileWebMvcController extends PageWebMvcController<UserProfileDto, UserProfile> {
    private final UserProfileService service;
    private final UserProfileConverter converter;

    @Autowired
    public UserProfileWebMvcController(UserProfileService service, UserProfileConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @Override
    public UserProfileService service() {
        return this.service;
    }

    @Override
    public UserProfileConverter converter() {
        return this.converter;
    }

    @Override
    protected ResponseEntity<Page<UserProfileDto>> handleGetAllWithRespectToUser(UUID userId, String search, Pageable pageable) {
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else { // user can search  for other users
            // might later be changed with addition of privacy settings
            Specification<UserProfile> spec = service().searchQueryParser().parse(search);
            Page<UserProfileDto> res = service().repository()
                                                .findAll(spec, pageable)
                                                .map(converter()::dtoFromEntity);

            return new ResponseEntity<>(res, HttpStatus.OK);

        }
    }
}
