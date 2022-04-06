package io.github.daniil547.card.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.dto.DomainDto;
import io.github.daniil547.common.dto.PageDto;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@ApiModel(value = "CheckList", parent = PageDto.class)
public class CheckListDto extends DomainDto {
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    private UUID cardId;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    @Size(min = 3, max = 60)
    private String name;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    @PositiveOrZero
    private Integer position;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @Getter(onMethod_ = @JsonProperty("items"))
    @Setter(onMethod_ = @JsonProperty("items"))
    private List<@Valid CheckableItemDto> itemDtos = new java.util.ArrayList<>();

}
