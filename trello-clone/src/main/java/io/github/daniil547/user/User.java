package io.github.daniil547.user;

import io.github.daniil547.common.domain.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class User extends Page {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String about;

    /*TODO other account info*/
}
