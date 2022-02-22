package io.github.daniil547.card_list;

import com.fasterxml.jackson.annotation.JsonView;
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

@ApiModel(value = "CardList", parent = PageDto.class)
public class CardListDto extends PageDto {

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private UUID boardId;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private Integer position;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private Boolean active = true;
}
