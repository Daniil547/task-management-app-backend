package io.github.daniil547.board;

import io.github.daniil547.common.services.DefaultPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultBoardService extends DefaultPageService<Board> implements BoardService {
    private final BoardRepository repo;

    @Autowired
    public DefaultBoardService(BoardRepository boardRepository) {
        this.repo = boardRepository;
    }

    public Board create(String boardPageName, String boardTitle, String boardDescr, UUID workspaceId) {
        Board board = new Board();
        super.init(board, boardPageName, boardTitle, boardDescr);

        board.setWorkspaceId(workspaceId);

        return save(board);
    }

    @Override
    protected BoardRepository getRepository() {
        return this.repo;
    }
}
