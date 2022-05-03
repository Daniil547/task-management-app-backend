package io.github.daniil547.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.dto.DomainDto;
import io.github.daniil547.common.dto.PageDto;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@ApiModel(value = "UserCredentials", parent = PageDto.class)
@JsonIgnoreProperties({"pageName", "pageDescription"})
public class UserCredentialsDto extends DomainDto {
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @Size(min = 3)
    @NotNull
    private String username;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    @Email
    private String email;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @Size(min = 8)
    @NotNull
    private String password;
}
