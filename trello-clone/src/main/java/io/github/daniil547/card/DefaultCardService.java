package io.github.daniil547.card;

import io.github.daniil547.common.services.DefaultPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultCardService extends DefaultPageService<CardDto, Card> implements CardService {
    private final CardRepository repo;

    @Autowired
    public DefaultCardService(CardRepository cardRepository) {
        this.repo = cardRepository;
    }

    @Override
    public CardRepository repository() {
        return this.repo;
    }
}
