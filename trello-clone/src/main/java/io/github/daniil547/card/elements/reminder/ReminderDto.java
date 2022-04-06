package io.github.daniil547.card.elements.reminder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.dto.DomainDto;
import io.github.daniil547.common.dto.PageDto;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@ApiModel(value = "Reminder", parent = PageDto.class)
public class ReminderDto extends DomainDto {
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSSXXX'['VV']'"; // format equivalent to ZonedDateTime's toString()

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    @Size(min = 3, max = 60)
    private String message;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @JsonFormat(pattern = DATE_FORMAT)
    @NotNull
    @FutureOrPresent
    private ZonedDateTime startOrDue;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @JsonFormat(pattern = DATE_FORMAT)
    @FutureOrPresent
    private ZonedDateTime end;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @JsonFormat(pattern = DATE_FORMAT)
    @NotNull
    @FutureOrPresent
    private ZonedDateTime remindOn;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private Boolean completed = false;
}
