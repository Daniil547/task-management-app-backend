package io.github.daniil547.card_list;

import io.github.daniil547.AbstractIntegrationTest;
import io.github.daniil547.BoardProvider;
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
public class CardListIntegrationTest extends AbstractIntegrationTest<CardListDto> {
    public CardListIntegrationTest() {
        super("/card-lists/");
    }

    @Autowired
    private BoardProvider boardProvider;

    private CardListDto firstCardList;
    private CardListDto secondCardList;


    @BeforeAll
    public void init() {
        UUID boardId = boardProvider.ensureExists().getId();

        firstCardList = new CardListDto();
        firstCardList.setPageName("firstCardLis");
        firstCardList.setPageTitle("First card list");
        firstCardList.setBoardId(boardId);
        firstCardList.setPosition(0);
        secondCardList = new CardListDto();
        secondCardList.setPageName("secondCardList");
        secondCardList.setPageTitle("Second card list");
        secondCardList.setBoardId(boardId);
        secondCardList.setPosition(1);
    }

    @Test
    @Order(1)
    public void testCreate() throws Exception {
        MvcResult mvcResult = super.testCreate(firstCardList);

        firstCardList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                               CardListDto.class);
        secondCardList = objectMapper.readValue(super.testCreate(secondCardList).getResponse().getContentAsString(),
                                                CardListDto.class);
    }

    @Test
    @Order(2)
    public void testGetAllSearchValidQuery() {
        List<String> ids = List.of(firstCardList.getId().toString(),
                                   secondCardList.getId().toString());

        super.testGetAllSearchValidQuery(List.of(
                // test get by parent ID
                Pair.of("boardId:" + firstCardList.getBoardId().toString(), new ResultMatcher[]{
                        jsonPath("$.content", hasSize(2)),
                        jsonPath("$.content.[0].id", in(ids)),
                        jsonPath("$.content.[1].id", in(ids))
                }),
                Pair.of("position<1", new ResultMatcher[]{
                        jsonPath("$.content", hasSize(1)),
                        jsonPath("$.content.[0].id", is(ids.get(0)))
                })
        ));
    }

    @Test
    @Order(3)
    public void testGetById() throws Exception {
        super.testGetById(secondCardList);
    }

    @Test
    @Order(4)
    public void testUpdate() throws Exception {
        firstCardList.setPageTitle("NEW first board");
        firstCardList.setPageName("NEWfirstBoard");
        firstCardList.setPosition(2);
        firstCardList.setPageDescription("Lorem ipsum dolor sit amet");

        super.testUpdate(firstCardList);
    }

    @Test
    @Order(5)
    public void testDelete() {
        super.testDelete(secondCardList.getId());
    }
}

