package io.github.daniil547.board;

import io.github.daniil547.common.services.PageService;

import java.util.UUID;

public interface BoardService extends PageService<Board> {
    Board create(String boardPageName, String boardTitle, String boardDescr, UUID workspaceId);
}
