package io.github.daniil547.user.member;

import io.github.daniil547.common.repositories.DomainRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMemberRepository extends DomainRepository<BoardMember> {
}
