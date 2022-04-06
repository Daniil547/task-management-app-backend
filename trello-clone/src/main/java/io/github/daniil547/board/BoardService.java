package io.github.daniil547.board;

import io.github.daniil547.common.services.PageService;

public interface BoardService extends PageService<BoardDto, Board> {
    @Override
    BoardRepository repository();
}
