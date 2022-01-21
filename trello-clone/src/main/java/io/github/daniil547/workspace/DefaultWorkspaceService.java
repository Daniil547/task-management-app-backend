package io.github.daniil547.workspace;

import io.github.daniil547.common.services.DefaultPageService;

public class DefaultWorkspaceService extends DefaultPageService<Workspace> implements WorkspaceService {
    private final WorkspaceRepository repo;

    public DefaultWorkspaceService(WorkspaceRepository workspaceRepository) {
        this.repo = workspaceRepository;
    }

    public Workspace create(String workspaceTitle, String workspacePageName, String workspaceDescription) {
        Workspace workspace = new Workspace();

        super.init(workspace, workspacePageName, workspaceTitle, workspaceDescription);

        return save(workspace);
    }

    @Override
    protected WorkspaceRepository getRepository() {
        return this.repo;
    }
}
