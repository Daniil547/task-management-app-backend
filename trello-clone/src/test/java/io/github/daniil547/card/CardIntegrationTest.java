package io.github.daniil547.card;

import io.github.daniil547.AbstractIntegrationTest;
import io.github.daniil547.CardListProvider;
import io.github.daniil547.board.BoardRepository;
import io.github.daniil547.board.label.LabelConverter;
import io.github.daniil547.board.label.LabelDto;
import io.github.daniil547.card.elements.CheckListDto;
import io.github.daniil547.card.elements.CheckableItemDto;
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

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
        MvcResult mvcResult = super.testCreate(firstCard,
                                               jsonPath("$.id").exists(),
                                               jsonPath("$.pageName", is(firstCard.getPageName())),
                                               jsonPath("$.pageTitle", is(firstCard.getPageTitle())),
                                               jsonPath("$.cardListId", is(firstCard.getCardListId().toString())),
                                               jsonPath("$.position", is(firstCard.getPosition())),
                                               jsonPath("$.pageDescription").isEmpty());

        firstCard = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                           CardDto.class);
        secondCard = objectMapper.readValue(super.testCreate(secondCard).getResponse().getContentAsString(),
                                            CardDto.class);
    }

    @Test
    @Order(2)
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
    @Order(3)
    public void testGetById() throws Exception {
        super.testGetById(secondCard.getId(),
                          jsonPath("$.id", is(secondCard.getId().toString())),
                          jsonPath("$.pageName", is(secondCard.getPageName())),
                          jsonPath("$.pageTitle", is(secondCard.getPageTitle())),
                          jsonPath("$.cardListId", is(secondCard.getCardListId().toString())),
                          jsonPath("$.position", is(secondCard.getPosition())),
                          jsonPath("$.pageDescription").isEmpty()
        );
    }

    @Test
    @Order(4)
    @Transactional
    public void testUpdate() throws Exception {
        firstCard.setPageTitle("NEW first card");
        firstCard.setPageName("NEWfirstCard");
        firstCard.setPosition(2);
        firstCard.setPageDescription("Lorem ipsum dolor sit amet");

        LabelDto labelDto = labelConverter.dtoFromEntity(
                boardRepository.getById(
                        cardListRepository.getById(
                                firstCard.getCardListId()
                        ).getBoardId()
                ).getLabels().get(0));

        firstCard.setAttachedLabelDtos(List.of(labelDto));


        super.testUpdate(firstCard,
                         jsonPath("$.id", is(firstCard.getId().toString())),
                         jsonPath("$.pageName", is(firstCard.getPageName())),
                         jsonPath("$.pageTitle", is(firstCard.getPageTitle())),
                         jsonPath("$.cardListId", is(firstCard.getCardListId().toString())),
                         jsonPath("$.position", is(firstCard.getPosition())),
                         jsonPath("$.pageDescription", is(firstCard.getPageDescription())),
                         jsonPath("$.attachedLabels.[0].id").exists(),
                         jsonPath("$.attachedLabels.[0].name", is(labelDto.getName())),
                         jsonPath("$.attachedLabels.[0].boardId", is(labelDto.getBoardId().toString())),
                         jsonPath("$.attachedLabels.[0].color", is(labelDto.getColor()))
        );
    }

    @Test
    @Order(5)
    @Transactional
    public void testUpdateAddLabel() throws Exception {
        LabelDto labelDto = labelConverter.dtoFromEntity(
                boardRepository.getById(
                        cardListRepository.getById(
                                firstCard.getCardListId()
                        ).getBoardId()
                ).getLabels().get(0));

        firstCard.setAttachedLabelDtos(List.of(labelDto));


        super.testUpdate(firstCard,
                         jsonPath("$.attachedLabels.[0].id").exists(),
                         jsonPath("$.attachedLabels.[0].name", is(labelDto.getName())),
                         jsonPath("$.attachedLabels.[0].boardId", is(labelDto.getBoardId().toString())),
                         jsonPath("$.attachedLabels.[0].color", is(labelDto.getColor()))
        );
    }

    @Test
    @Order(6)
    @Transactional
    public void testUpdateAddCheckList() throws Exception {
        CheckListDto checkList = new CheckListDto();
        checkList.setCardId(secondCard.getId());
        checkList.setName("a checklist i guess");

        CheckableItemDto checkableItem1 = new CheckableItemDto();
        checkableItem1.setDescription("tra-ta-ta my vezyom s soboy kota");

        checkList.setItemDtos(List.of(checkableItem1));

        firstCard.setCheckListDtos(List.of(checkList));

        super.testUpdate(firstCard,
                         jsonPath("$.checkLists.[0].id").exists(),
                         jsonPath("$.checkLists.[0].name", is(checkList.getName())),
                         jsonPath("$.checkLists.[0].cardId", is(checkList.getCardId().toString())),
                         jsonPath("$.checkLists.[0].items.[0].description", is(checkableItem1.getDescription()))
        );
    }

    @Test
    @Order(7)
    public void testDelete() {
        super.testDelete(secondCard.getId());
    }
}
