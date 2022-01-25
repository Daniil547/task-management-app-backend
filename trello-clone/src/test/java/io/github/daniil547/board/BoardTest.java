package io.github.daniil547.board;


import io.github.daniil547.BaseTest;
import io.github.daniil547.workspace.Workspace;
import io.github.daniil547.workspace.WorkspaceTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Order(2)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardTest extends BaseTest {
    public static final DefaultBoardService BOARD_SERVICE = new DefaultBoardService(
            new BoardJdbcRepository(dataSource)
    );

    private static final String PAGE_NAME = "board123";
    private static final String BOARD_TITLE = "Smth Board";
    private static Board board;

    private static final UUID DUMMY_ID = UUID.fromString("123e4567-e89b-12d3-a456-434414174961");

    private static Workspace workspace;


    @Test
    @Order(1)
    public void createNew() {
        workspace = WorkspaceTest.WORKSPACE_SERVICE.create("a", "b", "c");

        board = BOARD_SERVICE.create(PAGE_NAME, BOARD_TITLE, "", workspace.getId());

        assertAll(
                () -> assertNotNull(board),
                () -> assertEquals(PAGE_NAME, board.getPageName()),
                () -> assertEquals(BOARD_TITLE, board.getPageTitle()),
                () -> assertEquals(BoardVisibility.PRIVATE, board.getVisibility())
                 );
    }

    @Test
    @Order(2)
    public void createConflicting() {
        assertThrows(IllegalStateException.class,
                     () -> BOARD_SERVICE.create(PAGE_NAME, BOARD_TITLE, "", workspace.getId()));
    }

    @Test
    @Order(3)
    public void updateExisting() {
        String newPageName = "newname987";
        String newTitle = "New Name";
        String newDescr = "somethingSomethingNew";
        BoardVisibility newVisibility = BoardVisibility.PUBLIC;


        board.setPageName(newPageName);
        board.setPageTitle(newTitle);
        board.setPageDescription(newDescr);
        board.setVisibility(newVisibility);

        board = BOARD_SERVICE.update(board);

        assertAll(
                () -> assertEquals(newPageName, board.getPageName()),
                () -> assertEquals(newVisibility, board.getVisibility()),
                () -> assertEquals(newTitle, board.getPageTitle()),
                () -> assertEquals(newDescr, board.getPageDescription())
                 );
    }

    @Test
    @Order(4)
    public void updateNonExisting() {
        UUID actualId = board.getId();
        board.setId(DUMMY_ID);

        assertThrows(IllegalStateException.class, () -> BOARD_SERVICE.update(board));

        board.setId(actualId);
    }

    @Test
    @Order(5)
    public void getExistingById() {
        assertEquals(board, BOARD_SERVICE.getById(board.getId()).get());
    }

    @Test
    @Order(6)
    public void getNonExistingById() {
        assertEquals(Optional.empty(), BOARD_SERVICE.getById(DUMMY_ID));
    }

    @Test
    @Order(7)
    public void getAll() {
        //this is a new one, as we have updated the previous one
        Board temp = BOARD_SERVICE.create(PAGE_NAME, BOARD_TITLE, "", workspace.getId());
        List<Board> allUsers = BOARD_SERVICE.getAll();
        assertAll(
                () -> assertTrue(allUsers.contains(board)),
                () -> assertTrue(allUsers.contains(temp))
                 );
    }

    @Test
    @Order(8)
    public void deleteById() {
        BOARD_SERVICE.deleteById(board.getId());
        assertEquals(Optional.empty(), BOARD_SERVICE.getById(board.getId()));
    }
}

