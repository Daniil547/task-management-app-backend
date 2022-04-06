package io.github.daniil547.user.member;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.dto.DomainDto;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@ApiModel(value = "Member", parent = DomainDto.class)
public class MemberDto extends DomainDto {
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    @ApiModelProperty("An id of the workspace or board you want to register the user to." +
                      " Which one exactly is determined by the endpoint the request is sent to.")
    @JsonAlias({"workspaceIde", "boardId"})
    UUID placeId;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    UUID userId;
    @JsonView({JsonDtoView.Basic.class,
               JsonDtoView.Creation.class})
    Role role;
}
