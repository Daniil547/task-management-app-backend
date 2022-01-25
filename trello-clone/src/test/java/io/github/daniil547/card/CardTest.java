package io.github.daniil547.card;

import io.github.daniil547.BaseTest;
import io.github.daniil547.board.Board;
import io.github.daniil547.board.BoardTest;
import io.github.daniil547.card_list.CardList;
import io.github.daniil547.card_list.CardListTest;
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

@Order(4)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CardTest extends BaseTest {
    public static final DefaultCardService CARD_SERVICE = new DefaultCardService(
            new CardJdbcRepository(dataSource)
    );

    private static final String PAGE_NAME = "card123";
    private static final String CARD_TITLE = "Smth Card";
    private static Card card;

    private static final UUID DUMMY_ID = UUID.fromString("123e4567-e89b-12d3-a456-434414174961");

    private static CardList cardList;


    @Test
    @Order(1)
    public void createNew() {
        Workspace workspace = WorkspaceTest.WORKSPACE_SERVICE.create("aaa", "bbb", "ccc");
        Board board = BoardTest.BOARD_SERVICE.create("aaa", "bbb", "ccc", workspace.getId());
        cardList = CardListTest.CARD_LIST_SERVICE.create("aaa", "bbb", "ccc", board.getId(), 10);

        card = CARD_SERVICE.create(PAGE_NAME, CARD_TITLE, "", cardList.getId(), 0);

        assertAll(
                () -> assertNotNull(card),
                () -> assertEquals(PAGE_NAME, card.getPageName()),
                () -> assertEquals(CARD_TITLE, card.getPageTitle()),
                () -> assertEquals("", card.getPageDescription())
                 );
    }

    @Test
    @Order(2)
    public void createConflicting() {
        assertThrows(IllegalStateException.class,
                     () -> CARD_SERVICE.create(PAGE_NAME, CARD_TITLE, "", cardList.getId(), 0));
    }

    @Test
    @Order(3)
    public void updateExisting() {
        String newPageName = "newname987";
        String newTitle = "New Name";
        String newDescr = "somethingSomethingNew";


        card.setPageName(newPageName);
        card.setPageTitle(newTitle);
        card.setPageDescription(newDescr);

        card = CARD_SERVICE.update(card);

        assertAll(
                () -> assertEquals(newPageName, card.getPageName()),
                () -> assertEquals(newTitle, card.getPageTitle()),
                () -> assertEquals(newDescr, card.getPageDescription())
                 );
    }

    @Test
    @Order(4)
    public void updateNonExisting() {
        UUID actualId = card.getId();
        card.setId(DUMMY_ID);

        assertThrows(IllegalStateException.class, () -> CARD_SERVICE.update(card));

        card.setId(actualId);
    }

    @Test
    @Order(5)
    public void getExistingById() {
        assertEquals(card, CARD_SERVICE.getById(card.getId()).get());
    }

    @Test
    @Order(6)
    public void getNonExistingById() {
        assertEquals(Optional.empty(), CARD_SERVICE.getById(DUMMY_ID));
    }

    @Test
    @Order(7)
    public void getAll() {
        //this is a new one, as we have updated the previous one
        Card temp = CARD_SERVICE.create(PAGE_NAME, CARD_TITLE, "", cardList.getId(), 1);
        List<Card> allUsers = CARD_SERVICE.getAll();
        assertAll(
                () -> assertTrue(allUsers.contains(card)),
                () -> assertTrue(allUsers.contains(temp))
                 );
    }

    @Test
    @Order(8)
    public void deleteById() {
        CARD_SERVICE.deleteById(card.getId());
        assertEquals(Optional.empty(), CARD_SERVICE.getById(card.getId()));
    }
}



