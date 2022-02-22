package io.github.daniil547.workspace;

import io.github.daniil547.common.services.DefaultPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultWorkspaceService extends DefaultPageService<WorkspaceDto, Workspace> implements WorkspaceService {
    private final WorkspaceRepository repo;

    @Autowired
    public DefaultWorkspaceService(WorkspaceRepository workspaceRepository) {
        this.repo = workspaceRepository;
    }

    @Override
    public WorkspaceRepository repository() {
        return this.repo;
    }
}
