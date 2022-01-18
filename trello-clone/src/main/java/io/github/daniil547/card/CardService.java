package io.github.daniil547.card;

import io.github.daniil547.common.services.PageService;

import java.util.UUID;

public interface CardService extends PageService<Card> {
    Card create(String cardPageName, String cardTitle, String cardDescr, UUID cardListId, Integer position);
}
