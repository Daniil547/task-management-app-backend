package io.github.daniil547.user.member;

import io.github.daniil547.common.controllers.DomainWebMvcController;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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

    @Override
    protected ResponseEntity<Page<MemberDto>> handleGetAllWithRespectToUser(UUID userId, String search, Pageable pageable) {
        // FIXME
        throw new NotImplementedException();
    }
}
