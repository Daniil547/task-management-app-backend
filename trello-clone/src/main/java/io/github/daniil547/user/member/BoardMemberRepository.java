package io.github.daniil547.user.member;

import io.github.daniil547.common.repositories.DomainRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BoardMemberRepository extends DomainRepository<BoardMember> {

    @Query("SELECT wm.userId FROM WorkspaceMember wm where wm.id in ?1")
    List<UUID> findUsersIdsByMembersIds(List<UUID> membersIds);
}
