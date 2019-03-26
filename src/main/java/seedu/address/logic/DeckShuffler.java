package seedu.address.logic;

import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.UniqueCardList;

/**
 * Shuffles the deck for study session.
 */
public class DeckShuffler {

    private Deck deck;

    DeckShuffler(Deck deck) {
        this.deck = deck;
    }

    /**
     * Returns a card from the shuffled deck.
     */
    public Card generateCard() {
        UniqueCardList cards = deck.getCards();
        return cards.internalList.get((int) (Math.random() * cards.internalList.size()));
    }

}
