package io.github.daniil547.card_list;

import io.github.daniil547.common.services.DefaultPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultCardListService extends DefaultPageService<CardListDto, CardList> implements CardListService {
    private final CardListRepository repo;

    @Autowired
    public DefaultCardListService(CardListRepository cardListRepository) {
        this.repo = cardListRepository;
    }

    @Override
    public CardListRepository repository() {
        return this.repo;
    }
}
