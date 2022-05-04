package io.github.daniil547.board;

import io.github.daniil547.common.controllers.PageWebMvcController;
import io.github.daniil547.common.rest_api_search.SearchCriterion;
import io.github.daniil547.common.rest_api_search.SearchOperation;
import io.github.daniil547.common.rest_api_search.SearchSpecification;
import io.github.daniil547.user.member.BoardMemberRepository;
import io.github.daniil547.workspace.WorkspaceVisibility;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/boards")
@Api(tags = "Board Resource", description = "CRUD endpoint for workspaces")
public class BoardWebMvcController extends PageWebMvcController<BoardDto, Board> {
    private final BoardService boardService;
    private final BoardConverter converter;
    private final BoardMemberRepository boardMemberRepo;

    @Autowired
    public BoardWebMvcController(BoardService boardService,
                                 BoardConverter converter,
                                 BoardMemberRepository boardMemberRepo) {
        this.boardService = boardService;
        this.converter = converter;
        this.boardMemberRepo = boardMemberRepo;
    }

    @Override
    public BoardService service() {
        return this.boardService;
    }

    @Override
    public BoardConverter converter() {
        return this.converter;
    }

    @Override
    protected ResponseEntity<Page<BoardDto>> handleGetAllWithRespectToUser(UUID userId, String search, Pageable pageable) {
        Triple<UUID, String, Pageable> args = Triple.of(userId, search, pageable);
        if (cacheForGetAll.containsKey(args)) {
            return cacheForGetAll.get(args);
        }
        Specification<Board> spec = service().searchQueryParser().parse(search);
        if (userId == null) {
            spec = spec.and(new SearchSpecification<>(
                    new SearchCriterion("visibility",
                                        SearchOperation.EQUALITY,
                                        WorkspaceVisibility.PUBLIC))
            );
        } else {
            List<UUID> associatedWorkspaces = boardMemberRepo.findBoardIdsByUserId(userId);

            Specification<Board> tmpSpec = new SearchSpecification<>(
                    new SearchCriterion("id",
                                        SearchOperation.EQUALITY,
                                        associatedWorkspaces.get(0))
            );

            for (UUID uuid : associatedWorkspaces.subList(1, associatedWorkspaces.size())) {
                tmpSpec = tmpSpec.or(new SearchSpecification<>(
                        new SearchCriterion("visibility",
                                            SearchOperation.EQUALITY,
                                            WorkspaceVisibility.PUBLIC))
                );
            }

            spec.and(tmpSpec);
        }
        Page<BoardDto> res = service().repository()
                                      .findAll(spec, pageable)
                                      .map(converter()::dtoFromEntity);

        ResponseEntity<Page<BoardDto>> response = new ResponseEntity<>(res, HttpStatus.OK);
        cacheForGetAll.put(args, response);

        return response;
    }
}
