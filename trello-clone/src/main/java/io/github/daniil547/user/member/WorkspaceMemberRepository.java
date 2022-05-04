package io.github.daniil547.user.member;

import io.github.daniil547.common.repositories.DomainRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkspaceMemberRepository extends DomainRepository<WorkspaceMember> {
    Optional<WorkspaceMember> findByUserIdAndWorkspaceId(UUID userId, UUID workspaceId);

    @Query("SELECT wm.workspaceId FROM WorkspaceMember wm where wm.userId = ?1")
    List<UUID> findWorkspaceIdsByUserId(UUID userId);

    boolean existsByUserIdAndWorkspaceId(UUID userId, UUID workspaceId);

    default boolean checkIsAMemberAndRoleAtLeast(UUID userId, UUID workspaceId, Role atLeast) {
        Optional<WorkspaceMember> member = findByUserIdAndWorkspaceId(userId, workspaceId);
        return member.map(m -> m.getRole().ordinal() <= atLeast.ordinal())
                     .orElse(false);
    }
}
