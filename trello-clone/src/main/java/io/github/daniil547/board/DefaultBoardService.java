package io.github.daniil547.board;

import io.github.daniil547.common.services.DefaultPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultBoardService extends DefaultPageService<BoardDto, Board> implements BoardService {
    private final BoardRepository repo;

    @Autowired
    public DefaultBoardService(BoardRepository boardRepository) {
        this.repo = boardRepository;
    }

    @Override
    public BoardRepository repository() {
        return this.repo;
    }
}
