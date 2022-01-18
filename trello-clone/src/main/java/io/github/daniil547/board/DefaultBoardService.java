package io.github.daniil547.board;

import io.github.daniil547.common.services.DefaultPageService;

import java.util.UUID;

public class DefaultBoardService extends DefaultPageService<Board> implements BoardService {
    private final BoardRepository repo;

    public DefaultBoardService(BoardRepository repo) {
        this.repo = repo;
    }

    public Board create(String boardPageName, String boardTitle, String boardDescr, UUID workspaceId) {
        Board board = new Board();
        super.init(board, boardPageName, boardTitle, boardDescr);

        board.setWorkspaceId(workspaceId);

        return board;
    }

    @Override
    protected BoardRepository getRepository() {
        return this.repo;
    }
}
