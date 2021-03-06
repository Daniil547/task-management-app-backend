package io.github.daniil547.board;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.board.label.LabelDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@ApiModel(value = "Board", parent = PageDto.class)
public class BoardDto extends PageDto {

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    private UUID workspaceId;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private BoardVisibility visibility = BoardVisibility.PRIVATE;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private Boolean active = true;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @Getter(onMethod_ = @JsonProperty("labels"))
    @Setter(onMethod_ = @JsonProperty("labels"))
    private List<@Valid LabelDto> labelDtos = new ArrayList<>();
}