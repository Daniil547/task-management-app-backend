package io.github.daniil547.card;

import io.github.daniil547.common.services.PageService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CardService extends PageService<Card> {
    private CardRepository repo = new CardRepository();
    private List<Card> cachedCards;// = new ArrayList<>();

    public Card create(String title, String descr) {
        Card card = new Card();
        String cardName = String.join("", title.split("\\s")) + repo.lastCardPosition();
        super.create(card, cardName.toLowerCase(), title, descr);


        card.setListableCardElements(new ArrayList<>());
        card.setAssignedMembers(new HashSet<>());
        card.setAttachedLabels(new ArrayList<>());
        /* pretend we properly initialize our card */

        cachedCards.add(card);
        repo.saveCard(card);

        return card;
    }

    public void loadAllCards() {
        cachedCards = repo.getAllCards();
    }

    private String cardToString(Card card) {
        return "\"" + card.getTitle() + "\" - \"" + card.getDescription();
    }

    public void printAllCards() {
        int pos = 1;
        for (Card x : cachedCards) {
            System.out.println(pos++ + ") " + cardToString(x));
        }
    }

    public int cardsCount() {
        return cachedCards.size();
    }

    public void editTitle(int cardPos, String newTitle) {
        Card theCard = cachedCards.get(cardPos);
        theCard.setTitle(newTitle);
        repo.updateCard(theCard);
    }

    public void editDescription(int cardPos, String newDescr) {
        Card theCard = cachedCards.get(cardPos);
        theCard.setDescription(newDescr);
        repo.updateCard(theCard);
    }

    public void archiveCard(int cardPos) {
        Card theCard = cachedCards.get(cardPos);
        theCard.setActive(false);
        repo.updateCard(theCard);
        cachedCards.remove(cardPos);
    }
}
