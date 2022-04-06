package io.github.daniil547.user;


import io.github.daniil547.common.controllers.PageWebMvcController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Api(tags = "User Resource", description = "CRUD endpoint for users")
public class UserWebMvcController extends PageWebMvcController<UserDto, User> {
    private final UserService service;
    private final UserConverter converter;

    @Autowired
    public UserWebMvcController(UserService service, UserConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @Override
    public UserService service() {
        return this.service;
    }

    @Override
    public UserConverter converter() {
        return this.converter;
    }
}
