package io.github.daniil547;

import io.github.daniil547.card.Card;
import io.github.daniil547.card.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
@AllArgsConstructor(onConstructor_ = @Autowired)
public class CardProvider extends AbstractProvider<Card> {
    CardRepository repo;
    CardListProvider cardListProvider;

    @Override
    public Card ensureExists() {
        Card card = new Card();
        card.setCardListId(cardListProvider.ensureExists().getId());
        card.setPageName(faker.lorem().fixedString(7) + "Card");
        card.setPageTitle(faker.lorem().sentence(1, 1));
        card.setPosition(faker.number().numberBetween(10, 100));
        card.setPageDescription(faker.lorem().sentence(10));

        return repo.save(card);
    }
}
