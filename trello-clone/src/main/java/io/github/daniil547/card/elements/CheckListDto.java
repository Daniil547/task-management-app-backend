package io.github.daniil547.card.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.dto.DomainDto;
import io.github.daniil547.common.dto.PageDto;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@ApiModel(value = "CheckList", parent = PageDto.class)
public class CheckListDto extends DomainDto {
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private UUID cardId;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private String name;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @JsonProperty("items")
    private List<CheckableItemDto> itemDtos = new java.util.ArrayList<>();

}
