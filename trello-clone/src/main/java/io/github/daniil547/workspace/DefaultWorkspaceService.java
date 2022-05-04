package io.github.daniil547.workspace;

import io.github.daniil547.common.services.DefaultPageService;
import io.github.daniil547.user.member.WorkspaceMemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class DefaultWorkspaceService extends DefaultPageService<WorkspaceDto, Workspace> implements WorkspaceService {
    private final WorkspaceRepository workspaceRepo;
    private final WorkspaceMemberRepository workspaceMemberRepo;

//    @Autowired
//    public DefaultWorkspaceService(WorkspaceRepository workspaceRepository) {
//        this.repo = workspaceRepository;
//    }

    @Override
    public WorkspaceRepository repository() {
        return this.workspaceRepo;
    }
}
