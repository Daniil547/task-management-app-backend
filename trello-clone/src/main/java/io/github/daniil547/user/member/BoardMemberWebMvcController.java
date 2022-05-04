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
@RequestMapping("/boards/members")
@Api(tags = "Board Resource", description = "CRUD endpoint for workspaces")
public class BoardMemberWebMvcController extends DomainWebMvcController<MemberDto, BoardMember> {
    private final BoardMemberService service;
    private final BoardMemberConverter converter;

    @Autowired
    public BoardMemberWebMvcController(BoardMemberService service, BoardMemberConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @Override
    public BoardMemberService service() {
        return this.service;
    }

    @Override
    public BoardMemberConverter converter() {
        return this.converter;
    }

    @Override
    protected ResponseEntity<Page<MemberDto>> handleGetAllWithRespectToUser(UUID userId, String search, Pageable pageable) {
        // FIXME
        throw new NotImplementedException();
    }
}
