package io.github.daniil547.card_list;

import io.github.daniil547.common.services.PageService;

import java.util.UUID;

public interface CardListService extends PageService<CardList> {
    CardList create(String cardListPageName, String cardListTitle, String cardListDescr, UUID boardId, Integer position);
}
