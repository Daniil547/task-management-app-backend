package io.github.daniil547.user.member;

import io.github.daniil547.common.repositories.DomainRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardMemberRepository extends DomainRepository<BoardMember> {
    Optional<BoardMember> findByUserIdAndBoardId(UUID userId, UUID boardId);

    @Query("SELECT bm.boardId FROM BoardMember bm where bm.userId = ?1")
    List<UUID> findBoardIdsByUserId(UUID userId);

    boolean existsByUserIdAndBoardId(UUID userId, UUID boardId);

    default boolean checkIsAMemberAndRoleAtLeast(UUID userId, UUID boardId, Role atLeast) {
        Optional<BoardMember> member = findByUserIdAndBoardId(userId, boardId);
        return member.map(m -> m.getRole().ordinal() <= atLeast.ordinal())
                     .orElse(false);
    }
}
