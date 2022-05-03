package io.github.daniil547.workspace;

import io.github.daniil547.common.repositories.PageRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkspaceRepository extends PageRepository<Workspace> {
    WorkspaceVisibility getVisibilityById(UUID id);
}
