package io.github.daniil547.card;

import io.github.daniil547.common.services.DefaultPageService;

import java.util.UUID;

public class DefaultCardService extends DefaultPageService<Card> implements CardService {
    private final CardRepository repo;

    public DefaultCardService(CardRepository cardRepository) {
        this.repo = cardRepository;
    }

    public Card create(String cardPageName, String cardTitle, String cardDescr, UUID cardListId, Integer position) {
        Card card = new Card();
        super.init(card, cardPageName, cardTitle, cardDescr);

        card.setCardListId(cardListId);
        card.setPosition(position);

        return save(card);
    }

    @Override
    protected CardRepository getRepository() {
        return this.repo;
    }
}
