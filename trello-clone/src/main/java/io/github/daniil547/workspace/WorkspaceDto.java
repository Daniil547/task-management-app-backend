package io.github.daniil547.workspace;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.UUID;

@ApiModel("Workspace")
@Data
public class WorkspaceDto {
    @JsonView(JsonDtoView.Basic.class)
    private UUID id;
    @JsonView({JsonDtoView.Basic.class, JsonDtoView.Creation.class})
    private String name;
    @JsonView({JsonDtoView.Basic.class, JsonDtoView.Creation.class})
    private String title;
    @JsonView({JsonDtoView.Basic.class, JsonDtoView.Creation.class})
    private String description;
    @JsonView({JsonDtoView.Basic.class, JsonDtoView.Creation.class})
    private WorkspaceVisibility visibility;
    @JsonView({JsonDtoView.Basic.class, JsonDtoView.Creation.class})
    private String companyWebsiteUrl;
}
