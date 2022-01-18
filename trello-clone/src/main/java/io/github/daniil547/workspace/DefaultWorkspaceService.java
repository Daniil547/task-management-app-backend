package io.github.daniil547.workspace;

import io.github.daniil547.common.services.DefaultPageService;

public class DefaultWorkspaceService extends DefaultPageService<Workspace> implements WorkspaceService {
    private WorkspaceRepository repository;

    public DefaultWorkspaceService(WorkspaceRepository workspaceRepository) {
        this.repository = workspaceRepository;
    }

    public Workspace create(String workspaceTitle, String workspacePageName, String workspaceDescription) {
        Workspace workspace = new Workspace();

        super.init(workspace, workspacePageName, workspaceTitle, workspaceDescription);

        return workspace;
    }

    @Override
    protected WorkspaceRepository getRepository() {
        return this.repository;
    }
}
