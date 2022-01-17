package io.github.daniil547.card_list;

import io.github.daniil547.common.services.DefaultPageService;

import java.util.UUID;

public class DefaultCardListService extends DefaultPageService<CardList> {
    private final CardListRepository repo;

    public DefaultCardListService(CardListRepository cardListRepository) {
        this.repo = cardListRepository;
    }

    public CardList create(String cardListPageName, String cardListTitle, String cardListDescr, UUID boardId, Integer position) {
        CardList cardList = new CardList();
        super.init(cardList, cardListPageName, cardListTitle, cardListDescr);

        cardList.setBoardId(boardId);
        cardList.setPosition(position);

        return cardList;
    }

    @Override
    protected CardListRepository getRepository() {
        return this.repo;
    }
}
