package io.github.daniil547.workspace;

import io.github.daniil547.common.services.Service;

import java.util.UUID;

public interface WorkspaceService extends Service<Workspace, UUID> {
    Workspace create(String workspaceTitle, String workspacePageName, String workspaceDescription);
}
