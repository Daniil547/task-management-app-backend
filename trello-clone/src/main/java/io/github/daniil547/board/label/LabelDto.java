package io.github.daniil547.board.label;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.dto.DomainDto;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@ApiModel(value = "Label", parent = DomainDto.class)
public class LabelDto extends DomainDto {
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    private UUID boardId;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    private Integer color;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    @NotEmpty
    private String name;
}
