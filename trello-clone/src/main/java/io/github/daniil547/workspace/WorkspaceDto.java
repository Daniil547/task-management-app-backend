package io.github.daniil547.workspace;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.util.JsonDtoView;
import lombok.Data;

import java.util.UUID;

@Data
public class WorkspaceDto {
    private UUID id;
    private String name;
    private String title;
    private String description;
    private WorkspaceVisibility visibility;
    private String companyWebsiteUrl;
}
