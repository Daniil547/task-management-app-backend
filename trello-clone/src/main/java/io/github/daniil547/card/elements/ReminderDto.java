package io.github.daniil547.card.elements;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.dto.DomainDto;
import io.github.daniil547.common.dto.PageDto;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@ApiModel(value = "Reminder", parent = PageDto.class)
public class ReminderDto extends DomainDto {
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private UUID cardId;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private ZonedDateTime startOrDue;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private ZonedDateTime end;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private ZonedDateTime remindOn;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private Boolean completed = false;
}
