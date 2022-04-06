package io.github.daniil547.user.member;

import io.github.daniil547.common.services.DefaultDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultBoardMemberService extends DefaultDomainService<MemberDto, BoardMember> implements BoardMemberService {
    private final BoardMemberRepository repo;
    private final BoardMemberSearchQueryParser searchQueryParser;

    @Autowired
    public DefaultBoardMemberService(BoardMemberRepository repo, BoardMemberSearchQueryParser searchQueryParser) {
        super();
        this.repo = repo;
        this.searchQueryParser = searchQueryParser;
    }

    @Override
    public BoardMemberRepository repository() {
        return this.repo;
    }

    @Override
    public BoardMemberSearchQueryParser searchQueryParser() {
        return this.searchQueryParser;
    }
}
