package io.github.daniil547.card_list;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.dto.PageDto;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@ApiModel(value = "CardList", parent = PageDto.class)
public class CardListDto extends PageDto {

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    private UUID boardId;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    @PositiveOrZero
    private Integer position;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private Boolean active = true;
}
