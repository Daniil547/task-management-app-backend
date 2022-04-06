package io.github.daniil547;

import io.github.daniil547.board.Board;
import io.github.daniil547.board.BoardRepository;
import io.github.daniil547.board.BoardVisibility;
import io.github.daniil547.board.label.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;
import java.util.UUID;

@TestComponent
public class BoardProvider extends AbstractProvider<Board> {
    private final BoardRepository repo;
    private final WorkspaceProvider workspaceProvider;

    @Autowired
    public BoardProvider(BoardRepository repo, WorkspaceProvider workspaceProvider) {
        this.repo = repo;
        this.workspaceProvider = workspaceProvider;
    }

    @Override
    public Board ensureExists() {
        Board board = new Board();
        board.setWorkspaceId(workspaceProvider.ensureExists().getId());
        board.setPageName(faker.lorem().fixedString(7) + "Workspace");
        board.setPageTitle(faker.lorem().sentence(1, 1));
        board.setVisibility(BoardVisibility.PRIVATE);
        board.setPageDescription(faker.lorem().sentence(10));

        board = repo.save(board);

        UUID boardId = board.getId();

        Label label1 = new Label();
        label1.setColor(0x00ff0000);
        label1.setName("Label 1 for Board " + boardId);
        label1.setBoardId(boardId);
        Label label2 = new Label();
        label2.setColor(0x00ff0000);
        label2.setName("Label 2 for Board " + boardId);
        label2.setBoardId(boardId);

        board.setLabels(List.of(label1, label2));

        return repo.save(board);
    }
}
