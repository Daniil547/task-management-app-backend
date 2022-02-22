package io.github.daniil547.board.label;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.dto.DomainDto;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@ApiModel(value = "Label", parent = DomainDto.class)
public class LabelDto extends DomainDto {
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private UUID boardId;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private int color;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private String name;
}
