package io.github.daniil547.workspace;

import io.github.daniil547.common.services.DefaultPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultWorkspaceService extends DefaultPageService<Workspace> implements WorkspaceService {
    private final WorkspaceRepository repo;

    @Autowired
    public DefaultWorkspaceService(WorkspaceRepository workspaceRepository) {
        this.repo = workspaceRepository;
    }

    public Workspace create(String workspaceTitle, String workspacePageName, String workspaceDescription) {
        Workspace workspace = new Workspace();

        super.init(workspace, workspacePageName, workspaceTitle, workspaceDescription);

        return save(workspace);
    }

    @Override
    public Workspace create(WorkspaceDto workspaceDto) {
        Workspace workspace = entityFromDto(workspaceDto);

        initEntity(workspace);

        return save(workspace);
    }

    @Override
    public Workspace update(WorkspaceDto workspaceDto) {
        Workspace workspace = entityFromDto(workspaceDto);

        return update(workspace);
    }

    @Override
    public Workspace entityFromDto(WorkspaceDto dto) {
        Workspace workspace = new Workspace();

        workspace.setId(dto.getId());
        workspace.setPageTitle(dto.getTitle());
        workspace.setPageName(dto.getName());
        workspace.setPageDescription(dto.getDescription());
        workspace.setVisibility(dto.getVisibility());
        workspace.setCompanyWebsiteUrl(dto.getCompanyWebsiteUrl());

        return workspace;
    }

    @Override
    public WorkspaceDto dtoFromEntity(Workspace workspace) {
        WorkspaceDto dto = new WorkspaceDto();

        dto.setId(workspace.getId());
        dto.setTitle(workspace.getPageTitle());
        dto.setName(workspace.getPageName());
        dto.setDescription(workspace.getPageDescription());
        dto.setVisibility(workspace.getVisibility());
        dto.setCompanyWebsiteUrl(workspace.getCompanyWebsiteUrl());

        return dto;
    }

    @Override
    protected WorkspaceRepository getRepository() {
        return this.repo;
    }
}
