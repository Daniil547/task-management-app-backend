package io.github.daniil547.board;

import io.github.daniil547.common.controllers.PageWebMvcController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boards")
@Api(tags = "Board Resource", description = "CRUD endpoint for workspaces")
public class BoardWebMvcController extends PageWebMvcController<BoardDto, Board> {
    private final BoardService boardService;
    private final BoardConverter converter;

    @Autowired
    public BoardWebMvcController(BoardService boardService, BoardConverter converter) {
        this.boardService = boardService;
        this.converter = converter;
    }

    @Override
    public BoardService service() {
        return this.boardService;
    }

    @Override
    public BoardConverter converter() {
        return this.converter;
    }
}
