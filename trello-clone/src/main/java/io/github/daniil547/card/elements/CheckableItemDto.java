package io.github.daniil547.card.elements;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.dto.DomainDto;
import io.github.daniil547.common.dto.PageDto;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@ApiModel(value = "CheckableItem", parent = PageDto.class)
public class CheckableItemDto extends DomainDto {
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private UUID checkListId;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private Boolean checked = false;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private String description;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private Integer position;

}
