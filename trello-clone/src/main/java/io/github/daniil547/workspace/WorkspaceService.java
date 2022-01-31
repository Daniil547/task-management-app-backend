package io.github.daniil547.workspace;

import io.github.daniil547.common.services.PageService;

public interface WorkspaceService extends PageService<Workspace> {
    Workspace create(String workspaceTitle, String workspacePageName, String workspaceDescription);

    Workspace create(WorkspaceDto workspaceDto);

    Workspace update(WorkspaceDto dto);

    Workspace entityFromDto(WorkspaceDto dto);

    WorkspaceDto dtoFromEntity(Workspace workspace);
}
