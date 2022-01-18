package io.github.daniil547.card_list;

import io.github.daniil547.BaseTest;
import io.github.daniil547.board.Board;
import io.github.daniil547.board.BoardTest;
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

@Order(3)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CardListTest extends BaseTest {
    public static final DefaultCardListService CARD_LIST_SERVICE = new DefaultCardListService(
            new CardListJdbcRepository(dataSource)
    );

    private static final String PAGE_NAME = "cardlist123";
    private static final String CARD_LIST_TITLE = "Smth Card List";
    private static CardList cardList;

    private static final UUID DUMMY_ID = UUID.fromString("123e4567-e89b-12d3-a456-434414174961");

    private static Board board;


    @Test
    @Order(1)
    public void createNew() {
        Workspace workspace = WorkspaceTest.WORKSPACE_SERVICE.create("aa", "bb", "cc");
        board = BoardTest.BOARD_SERVICE.create("aa", "bb", "cc", workspace.getId());

        cardList = CARD_LIST_SERVICE.create(PAGE_NAME, CARD_LIST_TITLE, "", board.getId(), 0);

        assertAll(
                () -> assertNotNull(cardList),
                () -> assertEquals(PAGE_NAME, cardList.getPageName()),
                () -> assertEquals(CARD_LIST_TITLE, cardList.getTitle()),
                () -> assertEquals("", cardList.getDescription())
                 );
    }

    @Test
    @Order(2)
    public void createConflicting() {
        assertThrows(IllegalStateException.class,
                     () -> CARD_LIST_SERVICE.create(PAGE_NAME, CARD_LIST_TITLE, "", board.getId(), 0));
    }

    @Test
    @Order(3)
    public void updateExisting() {
        String newPageName = "newname987";
        String newTitle = "New Name";
        String newDescr = "somethingSomethingNew";


        cardList.setPageName(newPageName);
        cardList.setTitle(newTitle);
        cardList.setDescription(newDescr);

        cardList = CARD_LIST_SERVICE.update(cardList);

        assertAll(
                () -> assertEquals(newPageName, cardList.getPageName()),
                () -> assertEquals(newTitle, cardList.getTitle()),
                () -> assertEquals(newDescr, cardList.getDescription())
                 );
    }

    @Test
    @Order(4)
    public void updateNonExisting() {
        UUID actualId = cardList.getId();
        cardList.setId(DUMMY_ID);

        assertThrows(IllegalStateException.class, () -> CARD_LIST_SERVICE.update(cardList));

        cardList.setId(actualId);
    }

    @Test
    @Order(5)
    public void getExistingById() {
        assertEquals(cardList, CARD_LIST_SERVICE.getById(cardList.getId()).get());
    }

    @Test
    @Order(6)
    public void getNonExistingById() {
        assertEquals(Optional.empty(), CARD_LIST_SERVICE.getById(DUMMY_ID));
    }

    @Test
    @Order(7)
    public void getAll() {
        //this is a new one, as we have updated the previous one
        CardList temp = CARD_LIST_SERVICE.create(PAGE_NAME, CARD_LIST_TITLE, "", board.getId(), 1);
        List<CardList> allUsers = CARD_LIST_SERVICE.getAll();
        assertAll(
                () -> assertTrue(allUsers.contains(cardList)),
                () -> assertTrue(allUsers.contains(temp))
                 );
    }

    @Test
    @Order(8)
    public void deleteById() {
        CARD_LIST_SERVICE.deleteById(cardList.getId());
        assertEquals(Optional.empty(), CARD_LIST_SERVICE.getById(cardList.getId()));
    }
}


