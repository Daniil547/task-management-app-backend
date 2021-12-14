package io.github.daniil547.services;

import io.github.daniil547.domain.Card;
import io.github.daniil547.domain.Member;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CardService extends io.github.daniil547.services.NestedPageService<Card> {
    final List<Card> cards = new ArrayList<>(10);

    public Card create(Member creator, String title) {
        Card card = new Card();
        super.create(card, creator, title);

        card.setListableCardElements(new ArrayList<>());
        card.setAssignedMembers(new HashSet<>());
        card.setAttachedLabels(new ArrayList<>());
        /* pretend we properly initialize our card */

        cards.add(card);

        return card;
    }

    public void printCard(Card card) {
        System.out.println(card);
    }

    public void printAllCards() {
        cards.forEach(this::printCard);
    }

    public int cardsCount() {
        return cards.size();
    }
}
