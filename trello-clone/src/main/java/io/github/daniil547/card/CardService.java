package io.github.daniil547.card;

import io.github.daniil547.common.services.PageService;

import java.util.ArrayList;
import java.util.HashSet;

public class CardService extends PageService<Card> {
    private CardRepository repo = new CardRepository();

    public Card create(String pageName, String title, String descr) {
        Card card = new Card();
        super.create(card, pageName, title, descr);


        card.setListableCardElements(new ArrayList<>());
        card.setAssignedMembers(new HashSet<>());
        card.setAttachedLabels(new ArrayList<>());
        /* TODO properly initialize our card */


        return card;
    }
}
