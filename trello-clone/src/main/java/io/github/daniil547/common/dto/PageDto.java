package io.github.daniil547.common.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@ApiModel(parent = DomainDto.class)
public abstract class PageDto extends DomainDto {
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    @Size(min = 3)
    private String pageName;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private String pageTitle;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private String pageDescription;
}
