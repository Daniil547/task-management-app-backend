package io.github.daniil547.domain;

import lombok.Data;

@Data
public class Member extends Domain {
    private final User user;
    private Role role = Role.GUEST;

}
