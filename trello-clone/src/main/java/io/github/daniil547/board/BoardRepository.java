package io.github.daniil547.board;

import io.github.daniil547.common.repositories.PageRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoardRepository extends PageRepository<Board> {
    BoardVisibility getVisibilityById(UUID id);

    @Query("select b.workspaceId from Board b where b.id = ?1")
    UUID getWorkspaceIdById(UUID id);
}
