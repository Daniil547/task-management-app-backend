package io.github.daniil547.workspace;

import io.github.daniil547.common.controllers.PageWebMvcController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workspaces")
@Api(tags = "Workspace Resource", description = "CRUD endpoint for workspaces")
public class WorkspaceWebMvcController extends PageWebMvcController<WorkspaceDto, Workspace> {
    private final WorkspaceService service;
    private final WorkspaceConverter converter;

    @Autowired
    public WorkspaceWebMvcController(WorkspaceService service, WorkspaceConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @Override
    public WorkspaceService service() {
        return this.service;
    }

    @Override
    public WorkspaceConverter converter() {
        return this.converter;
    }
}
