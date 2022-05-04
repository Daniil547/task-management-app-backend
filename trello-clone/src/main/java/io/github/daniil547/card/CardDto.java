package io.github.daniil547.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.board.label.LabelDto;
import io.github.daniil547.card.elements.attachment.AttachmentDto;
import io.github.daniil547.card.elements.check_list.CheckListDto;
import io.github.daniil547.card.elements.reminder.ReminderDto;
import io.github.daniil547.common.dto.PageDto;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@ApiModel(value = "Card", parent = PageDto.class)
public class CardDto extends PageDto {
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private Boolean active = true;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    private UUID cardListId;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    @PositiveOrZero
    private Integer position;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @Getter(onMethod_ = @JsonProperty("attachedLabels"))
    @Setter(onMethod_ = @JsonProperty("attachedLabels"))
    private List<@Valid LabelDto> attachedLabelDtos = new ArrayList<>();

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @UniqueElements
    private List<UUID> assignedMembers = new ArrayList<>();

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @Getter(onMethod_ = @JsonProperty("checkLists"))
    @Setter(onMethod_ = @JsonProperty("checkLists"))
    private List<@Valid CheckListDto> checkListDtos = new ArrayList<>();

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @Getter(onMethod_ = @JsonProperty("attachments"))
    @Setter(onMethod_ = @JsonProperty("attachments"))
    private List<@Valid AttachmentDto> attachmentDtos = new ArrayList<>();

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @Getter(onMethod_ = @JsonProperty("reminder"))
    @Setter(onMethod_ = @JsonProperty("reminder"))
    @Valid
    private ReminderDto reminderDto;
}
