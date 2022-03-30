package io.github.daniil547.card;

import io.github.daniil547.AbstractIntegrationTest;
import io.github.daniil547.CardListProvider;
import io.github.daniil547.board.BoardRepository;
import io.github.daniil547.board.label.LabelConverter;
import io.github.daniil547.board.label.LabelDto;
import io.github.daniil547.card.elements.CheckListDto;
import io.github.daniil547.card.elements.CheckableItemDto;
import io.github.daniil547.card.elements.ReminderDto;
import io.github.daniil547.card_list.CardList;
import io.github.daniil547.card_list.CardListRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class CardIntegrationTest extends AbstractIntegrationTest<CardDto> {
    public CardIntegrationTest() {
        super("/cards/");
    }

    @Autowired
    private CardListProvider cardListProvider;

    @Autowired
    private LabelConverter labelConverter;

    @Autowired
    private CardListRepository cardListRepository;
    @Autowired
    private BoardRepository boardRepository;

    private CardDto firstCard;
    private CardDto secondCard;


    @BeforeAll
    public void init() {
        CardList cardList = cardListProvider.ensureExists();
        UUID cardListId = cardList.getId();

        firstCard = new CardDto();
        firstCard.setPageName("firstCard");
        firstCard.setPageTitle("First card");
        firstCard.setCardListId(cardListId);
        firstCard.setPosition(0);
        secondCard = new CardDto();
        secondCard.setPageName("secondCard");
        secondCard.setPageTitle("Second card");
        secondCard.setCardListId(cardListId);
        secondCard.setPosition(1);
    }

    @Test
    @Order(1)
    public void testCreate() throws Exception {
        MvcResult mvcResult = super.testCreate(firstCard);

        firstCard = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                           CardDto.class);
        secondCard = objectMapper.readValue(super.testCreate(secondCard).getResponse().getContentAsString(),
                                            CardDto.class);
    }

    @Test
    @Order(2)
    public void testCreatingArchivedFailure() throws Exception {
        CardDto archivedCard = new CardDto();
        archivedCard.setPageName("archivedCard1");
        archivedCard.setPageTitle("Archived card1");
        archivedCard.setCardListId(firstCard.getCardListId());
        archivedCard.setPosition(5);
        archivedCard.setActive(false);

        super.testCreateFailure(archivedCard,
                                status().isBadRequest(),
                                jsonPath("$.constraint", is("creation of a card that is archived is forbidden")),
                                jsonPath("$.property", is("active")),
                                jsonPath("$.value", is(false))
        );
    }

    @Test
    @Order(3)
    public void testGetAllSearchValidQuery() {
        List<String> ids = List.of(firstCard.getId().toString(),
                                   secondCard.getId().toString());

        super.testGetAllSearchValidQuery(List.of(
                // test get by parent ID
                Pair.of("cardListId:" + firstCard.getCardListId().toString(), new ResultMatcher[]{
                        jsonPath("$.content", hasSize(2)),
                        jsonPath("$.content.[0].id", in(ids)),
                        jsonPath("$.content.[1].id", in(ids))
                }),
                Pair.of("position>0", new ResultMatcher[]{
                        jsonPath("$.content", hasSize(1)),
                        jsonPath("$.content.[0].id", is(ids.get(1)))
                })
        ));
    }

    @Test
    @Order(4)
    public void testGetById() throws Exception {
        super.testGetById(secondCard);
    }

    @Test
    @Order(5)
    public void testUpdate() throws Exception {
        firstCard.setPageTitle("NEW first card");
        firstCard.setPageName("NEWfirstCard");
        firstCard.setPosition(2);
        firstCard.setPageDescription("Lorem ipsum dolor sit amet");

        super.testUpdate(firstCard);
    }

    @Test
    @Order(6)
    public void testUpdateArchive() throws Exception {
        secondCard.setActive(false);

        super.testUpdate(secondCard);
    }

    @Test
    @Order(7)
    public void testUpdatingArchivedFailure() throws Exception {
        secondCard.setPageTitle("This shouldn't be persisted");

        super.testUpdateFailure(secondCard,
                                status().isBadRequest(),
                                jsonPath("$.constraint", is("update of an archived card is forbidden")),
                                jsonPath("$.property", is("active")),
                                jsonPath("$.value", is(false))
        );
    }

    @Test
    @Order(8)
    @Transactional
    public void testUpdateAddLabel() throws Exception {
        LabelDto labelDto = labelConverter.dtoFromEntity(
                boardRepository.getById(
                        cardListRepository.getById(
                                firstCard.getCardListId()
                        ).getBoardId()
                ).getLabels().get(0));

        firstCard.setAttachedLabelDtos(List.of(labelDto));

        super.testUpdate(firstCard);
    }

    @Test
    @Order(9)
    public void testUpdateAddCheckList() throws Exception {
        CheckListDto checkList = new CheckListDto();
        checkList.setCardId(secondCard.getId());
        checkList.setName("a checklist i guess");
        checkList.setPosition(0);

        firstCard.setCheckListDtos(List.of(checkList));

        firstCard = objectMapper.readValue(super.testUpdate(firstCard).getResponse().getContentAsString(), CardDto.class);

        CheckableItemDto checkableItem1 = new CheckableItemDto();
        checkableItem1.setDescription("tra-ta-ta my vezyom s soboy kota");
        checkableItem1.setPosition(0);
        checkableItem1.setCheckListId(firstCard.getCheckListDtos().get(0).getId());

        firstCard.getCheckListDtos().get(0).setItemDtos(List.of(checkableItem1));

        firstCard = objectMapper.readValue(super.testUpdate(firstCard).getResponse().getContentAsString(), CardDto.class);
    }

    @Test
    @Order(10)
    public void testUpdateAddReminder() throws Exception {
        ReminderDto reminderDto = new ReminderDto();
        reminderDto.setMessage("Don't forget to test Reminder! Oh, wait...");
        reminderDto.setStartOrDue(ZonedDateTime.now().plus(2, ChronoUnit.DAYS));
        reminderDto.setRemindOn(ZonedDateTime.now().plus(1, ChronoUnit.DAYS));

        firstCard.setReminderDto(reminderDto);

        super.testUpdate(firstCard);
    }

    @Test
    @Order(11)
    public void testUpdateAddReminderFailure() throws Exception {
        assertAll(
                () -> {
                    ReminderDto reminderNullStart = new ReminderDto();
                    reminderNullStart.setMessage("Don't forget to test Reminder! Oh, wait...");
                    reminderNullStart.setStartOrDue(null);
                    reminderNullStart.setRemindOn(ZonedDateTime.now().plus(1, ChronoUnit.DAYS));
                    firstCard.setReminderDto(reminderNullStart);

                    super.testUpdateFailure(firstCard,
                                            status().isBadRequest(),
                                            jsonPath("$.[0].constraint", is("must not be null")),
                                            jsonPath("$.[0].property", is("startOrDue")),
                                            jsonPath("$.[0].value", is(nullValue()))
                    );
                },
                () -> {
                    ReminderDto reminderNullRemind = new ReminderDto();
                    reminderNullRemind.setMessage("Don't forget to test Reminder! Oh, wait...");
                    reminderNullRemind.setStartOrDue(ZonedDateTime.now().plus(1, ChronoUnit.DAYS));
                    reminderNullRemind.setRemindOn(null);
                    firstCard.setReminderDto(reminderNullRemind);

                    super.testUpdateFailure(firstCard,
                                            status().isBadRequest(),
                                            jsonPath("$.[0].constraint", is("must not be null")),
                                            jsonPath("$.[0].property", is("remindOn")),
                                            jsonPath("$.[0].value", is(nullValue()))
                    );
                },
                () -> {
                    ReminderDto reminderEndBeforeStart = new ReminderDto();
                    reminderEndBeforeStart.setMessage("Don't forget to test Reminder! Oh, wait...");
                    reminderEndBeforeStart.setRemindOn(ZonedDateTime.now().plus(1, ChronoUnit.DAYS));
                    reminderEndBeforeStart.setEnd(ZonedDateTime.now().plus(2, ChronoUnit.DAYS));
                    reminderEndBeforeStart.setStartOrDue(ZonedDateTime.now().plus(3, ChronoUnit.DAYS));
                    firstCard.setReminderDto(reminderEndBeforeStart);

                    super.testUpdateFailure(firstCard,
                                            status().isBadRequest(),
                                            jsonPath("$.constraint", is("end date must be after start date")),
                                            jsonPath("$.property", is("endDate")),
                                            jsonPath("$.value", is(reminderEndBeforeStart.getEnd().toString()))
                    );
                }
        );
    }

    @Test
    @Order(12)
    public void testDelete() {
        super.testDelete(secondCard.getId());
    }
}
