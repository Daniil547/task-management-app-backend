package io.github.daniil547.user.member;

import io.github.daniil547.common.services.DefaultDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultWorkspaceMemberService extends DefaultDomainService<MemberDto, WorkspaceMember> implements WorkspaceMemberService {
    private final WorkspaceMemberRepository repo;
    private final WorkspaceMemberSearchQueryParser searchQueryParser;

    @Autowired
    public DefaultWorkspaceMemberService(WorkspaceMemberRepository repo, WorkspaceMemberSearchQueryParser searchQueryParser) {
        this.repo = repo;
        this.searchQueryParser = searchQueryParser;
    }

    @Override
    public WorkspaceMemberRepository repository() {
        return this.repo;
    }

    @Override
    public WorkspaceMemberSearchQueryParser searchQueryParser() {
        return this.searchQueryParser;
    }
}
