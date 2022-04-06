package io.github.daniil547.card;

import io.github.daniil547.common.services.PageService;

public interface CardService extends PageService<CardDto, Card> {

    @Override
    CardRepository repository();
}
