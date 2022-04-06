package io.github.daniil547;

import io.github.daniil547.card_list.CardList;
import io.github.daniil547.card_list.CardListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class CardListProvider extends AbstractProvider<CardList> {
    private final CardListRepository repo;
    private final BoardProvider boardProvider;

    @Autowired
    public CardListProvider(CardListRepository repo, BoardProvider workspaceProvider) {
        this.repo = repo;
        this.boardProvider = workspaceProvider;
    }

    @Override
    public CardList ensureExists() {
        CardList cardList = new CardList();
        cardList.setBoardId(boardProvider.ensureExists().getId());
        cardList.setPageName(faker.lorem().fixedString(7) + "Workspace");
        cardList.setPageTitle(faker.lorem().sentence(1, 1));
        cardList.setPosition(faker.number().numberBetween(10, 100));
        cardList.setPageDescription(faker.lorem().sentence(10));

        return repo.save(cardList);
    }
}
