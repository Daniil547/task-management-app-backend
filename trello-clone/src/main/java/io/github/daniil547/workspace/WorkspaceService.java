package io.github.daniil547.workspace;

import io.github.daniil547.common.services.PageService;

public interface WorkspaceService extends PageService<WorkspaceDto, Workspace> {

    @Override
    WorkspaceRepository repository();
}
