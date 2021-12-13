package io.github.daniil547.domain;

import lombok.Data;

@Data
public class User extends Resource {
    private String fullName;
    private String username;
    private String email;
    private String about;

    /*TODO other account info*/
}
