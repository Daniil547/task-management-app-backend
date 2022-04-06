package io.github.daniil547.user.member;

import io.github.daniil547.common.controllers.DomainWebMvcController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workspaces/members")
@Api(tags = "Workspace Resource", description = "CRUD endpoint for workspaces")
public class WorkspaceMemberWebMvcController extends DomainWebMvcController<MemberDto, WorkspaceMember> {
    private final WorkspaceMemberService service;
    private final WorkspaceMemberConverter converter;

    @Autowired
    public WorkspaceMemberWebMvcController(WorkspaceMemberService service, WorkspaceMemberConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @Override
    public WorkspaceMemberService service() {
        return this.service;
    }

    @Override
    public WorkspaceMemberConverter converter() {
        return this.converter;
    }
}
