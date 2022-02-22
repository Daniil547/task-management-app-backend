package io.github.daniil547.board;

import io.github.daniil547.common.repositories.PageRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends PageRepository<Board> {
}
