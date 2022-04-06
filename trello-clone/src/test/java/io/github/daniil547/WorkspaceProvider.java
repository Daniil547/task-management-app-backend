package io.github.daniil547;

import io.github.daniil547.workspace.Workspace;
import io.github.daniil547.workspace.WorkspaceRepository;
import io.github.daniil547.workspace.WorkspaceVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class WorkspaceProvider extends AbstractProvider<Workspace> {
    private final WorkspaceRepository repo;

    @Autowired
    public WorkspaceProvider(WorkspaceRepository repo) {
        this.repo = repo;
    }

    public Workspace ensureExists() {
        Workspace workspace = new Workspace();
        workspace.setPageName(faker.lorem().fixedString(7) + "Workspace");
        workspace.setPageTitle(faker.lorem().sentence(1, 1));
        workspace.setVisibility(WorkspaceVisibility.PRIVATE);
        workspace.setCompanyWebsiteUrl(faker.company().url());
        workspace.setPageDescription(faker.lorem().sentence(10));

        return repo.save(workspace);
    }
}
