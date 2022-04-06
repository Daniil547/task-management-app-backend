package io.github.daniil547.workspace;

import io.github.daniil547.common.services.PageConverter;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceConverter extends PageConverter<WorkspaceDto, Workspace> {
    public WorkspaceConverter() {
    }

    @Override
    protected Workspace transferEntitySpecificFieldsFromDto(WorkspaceDto dto) {
        Workspace workspace = new Workspace();

        workspace.setVisibility(dto.getVisibility());
        workspace.setCompanyWebsiteUrl(dto.getCompanyWebsiteUrl());

        return workspace;
    }

    @Override
    protected WorkspaceDto transferDtoSpecificFieldsFromEntity(Workspace entity) {
        WorkspaceDto dto = new WorkspaceDto();

        dto.setVisibility(entity.getVisibility());
        dto.setCompanyWebsiteUrl(entity.getCompanyWebsiteUrl());

        return dto;
    }
}