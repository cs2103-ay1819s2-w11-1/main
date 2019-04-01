package seedu.address.logic;

import java.util.Collections;
import java.util.Iterator;

import javafx.collections.ObservableList;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

/**
 * Shuffles the deck for study session.
 */
public class DeckShuffler {

    private Deck deck;
    private Iterator<Card> it;
    private ObservableList<Card> cards;

    DeckShuffler(Deck deck) {
        this.deck = deck;
        Deck shuffledDeck = new Deck(deck);
        cards = shuffledDeck.getCards().internalList;
        shuffleCards();

    }

    /**
     * Reshuffles cards once again when end of deck is reached.
     */
    private void shuffleCards() {
        Collections.shuffle(cards);
        it = cards.iterator();
    }

    /**
     * Returns a card from the shuffled deck.
     */
    public Card generateCard() {
        if (it.hasNext()) {
            return it.next();
        } else {
            shuffleCards();
            return it.next();
        }
    }

}
