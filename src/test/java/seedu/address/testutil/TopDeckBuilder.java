package seedu.address.testutil;

import seedu.address.model.TopDeck;
import seedu.address.model.deck.Deck;

/**
 * A utility class to help with building TopDeck objects.
 * Example usage: <br>
 * {@code TopDeck td = new TopDeckBuilder().withCard("Question1", "Question2").build();}
 */
public class TopDeckBuilder {

    private TopDeck topDeck;

    public TopDeckBuilder() {
        topDeck = new TopDeck();
    }

    public TopDeckBuilder(TopDeck topDeck) {
        this.topDeck = topDeck;
    }

    /**
     * Adds a new {@code Deck} to the {@code TopDeck} that we are building.
     */
    public TopDeckBuilder withDeck(Deck deck) {
        topDeck.addDeck(deck);
        return this;
    }

    public TopDeck build() {
        return topDeck;
    }
}
