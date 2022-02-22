package io.github.daniil547.card_list;

import io.github.daniil547.common.services.PageService;

public interface CardListService extends PageService<CardListDto, CardList> {

    @Override
    CardListRepository repository();
}
