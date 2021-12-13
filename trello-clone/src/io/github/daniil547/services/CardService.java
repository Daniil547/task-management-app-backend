package io.github.daniil547.services;

import io.github.daniil547.domain.Card;
import io.github.daniil547.domain.Member;

import java.util.ArrayList;
import java.util.HashSet;

public class CardService extends NestedPageService<Card> {

    public Card create(Member creator, String title) {
        Card card = new Card();
        super.create(card, creator, title);

        card.setListableCardElements(new ArrayList<>());
        card.setAssignedMembers(new HashSet<>());
        card.setAttachedLabels(new ArrayList<>());
        /* pretend we properly initialize our card*/

        return card;
    }

}
