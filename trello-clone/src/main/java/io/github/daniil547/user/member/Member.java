package io.github.daniil547.user.member;

import io.github.daniil547.common.domain.Domain;
import io.github.daniil547.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Member extends Domain {
    private final User user;
    private Role role = Role.GUEST;

}
