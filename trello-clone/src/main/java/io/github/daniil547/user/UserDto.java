package io.github.daniil547.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
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

@ApiModel(value = "User", parent = PageDto.class)
@JsonIgnoreProperties({"pageName", "pageDescription"})
public class UserDto extends PageDto {
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    @Size(min = 3)
    private String firstName;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    @Size(min = 3)
    private String lastName;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    @Email
    private String email;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @JsonProperty("username")
    public String getUsername() {
        return getPageName();
    }

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @JsonProperty("username")
    public void setUsername(String username) {
        setPageName(username);
    }

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @JsonProperty("about")
    public String getAbout() {
        return getPageDescription();
    }

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @JsonProperty("about")
    public void setAbout(String about) {
        setPageDescription(about);
    }
}
