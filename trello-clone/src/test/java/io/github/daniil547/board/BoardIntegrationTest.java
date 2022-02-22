package io.github.daniil547.board;


import io.github.daniil547.AbstractIntegrationTest;
import io.github.daniil547.WorkspaceProvider;
import io.github.daniil547.board.label.LabelDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class BoardIntegrationTest extends AbstractIntegrationTest<BoardDto> {
    @Autowired
    private WorkspaceProvider workspaceProvider;

    private BoardDto firstBoard;
    private BoardDto secondBoard;

    public BoardIntegrationTest() {
        super("/boards/");
    }

    @BeforeAll
    public void init() {
        UUID workspaceId = workspaceProvider.ensureExists().getId();

        firstBoard = new BoardDto();
        firstBoard.setPageName("firstBoard");
        firstBoard.setPageTitle("First board");
        firstBoard.setVisibility(BoardVisibility.PRIVATE);
        firstBoard.setWorkspaceId(workspaceId);
        secondBoard = new BoardDto();
        secondBoard.setPageName("secondBoard");
        secondBoard.setPageTitle("Second board");
        secondBoard.setVisibility(BoardVisibility.PRIVATE);
        secondBoard.setWorkspaceId(workspaceId);
    }

    @Test
    @Order(1)
    public void testCreate() throws Exception {
        MvcResult mvcResult = super.testCreate(firstBoard,
                                               jsonPath("$.id").exists(),
                                               jsonPath("$.pageName", is(firstBoard.getPageName())),
                                               jsonPath("$.pageTitle", is(firstBoard.getPageTitle())),
                                               jsonPath("$.workspaceId", is(firstBoard.getWorkspaceId().toString())),
                                               jsonPath("$.visibility", is(firstBoard.getVisibility().toString())),
                                               jsonPath("$.pageDescription").isEmpty());

        firstBoard = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                            BoardDto.class);
        secondBoard = objectMapper.readValue(super.testCreate(secondBoard).getResponse().getContentAsString(),
                                             BoardDto.class);
    }

    @Test
    @Order(2)
    public void testGetAllSearchValidQuery() {
        List<String> ids = List.of(firstBoard.getId().toString(),
                                   secondBoard.getId().toString());

        super.testGetAllSearchValidQuery(List.of(
                // test get by parent ID
                Pair.of("workspaceId:" + firstBoard.getWorkspaceId().toString(), new ResultMatcher[]{
                        jsonPath("$.content", hasSize(2)),
                        jsonPath("$.content.[0].id", in(ids)),
                        jsonPath("$.content.[1].id", in(ids))
                }),
                Pair.of("pageName:firstBoard,pageTitle~%board", new ResultMatcher[]{
                        jsonPath("$.content", hasSize(1)),
                        jsonPath("$.content.[0].id", is(ids.get(0)))
                }),
                Pair.of("pageName:firstBoard|pageName:secondBoard", new ResultMatcher[]{
                        jsonPath("$.content", hasSize(2)),
                        jsonPath("$.content.[0].id", in(ids)),
                        jsonPath("$.content.[1].id", in(ids))
                })
        ));
    }

    @Test
    @Order(3)
    public void testGetById() throws Exception {
        super.testGetById(secondBoard.getId(),
                          jsonPath("$.id", is(secondBoard.getId().toString())),
                          jsonPath("$.pageName", is(secondBoard.getPageName())),
                          jsonPath("$.pageTitle", is(secondBoard.getPageTitle())),
                          jsonPath("$.workspaceId", is(secondBoard.getWorkspaceId().toString())),
                          jsonPath("$.visibility", is(secondBoard.getVisibility().toString())),
                          jsonPath("$.pageDescription").isEmpty()
        );
    }

    @Test
    @Order(4)
    public void testUpdate() throws Exception {
        firstBoard.setPageTitle("NEW first board");
        firstBoard.setPageName("NEWfirstBoard");
        firstBoard.setVisibility(BoardVisibility.PUBLIC);
        firstBoard.setPageDescription("Lorem ipsum dolor sit amet");

        LabelDto labelDto1 = new LabelDto();
        labelDto1.setColor(0x00f0f0f0);
        labelDto1.setName("Label 1-1");
        labelDto1.setBoardId(firstBoard.getId());

        firstBoard.setLabelDtos(List.of(labelDto1));

        super.testUpdate(firstBoard,
                         jsonPath("$.id", is(firstBoard.getId().toString())),
                         jsonPath("$.pageName", is(firstBoard.getPageName())),
                         jsonPath("$.pageTitle", is(firstBoard.getPageTitle())),
                         jsonPath("$.workspaceId", is(firstBoard.getWorkspaceId().toString())),
                         jsonPath("$.visibility", is(firstBoard.getVisibility().toString())),
                         jsonPath("$.pageDescription", is(firstBoard.getPageDescription())),
                         jsonPath("$.labels.[0].id").exists(),
                         jsonPath("$.labels.[0].name", is(labelDto1.getName())),
                         jsonPath("$.labels.[0].boardId", is(firstBoard.getId().toString())),
                         jsonPath("$.labels.[0].color", is(labelDto1.getColor()))
        );
    }

    @Test
    @Order(5)
    public void testDelete() {
        super.testDelete(secondBoard.getId());
    }
}