package io.github.daniil547.card.elements.attachment;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.dto.DomainDto;
import io.github.daniil547.common.dto.PageDto;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@ApiModel(value = "Attachment", parent = PageDto.class)
public class AttachmentDto extends DomainDto {
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    private String name;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    private String extension;

    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @NotNull
    private UUID fileContentId;

    @NotNull
    private UUID cardId;
}
