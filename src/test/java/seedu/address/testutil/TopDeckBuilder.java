package seedu.address.testutil;

import seedu.address.model.TopDeck;
import seedu.address.model.deck.Card;

/**
 * A utility class to help with building TopDeck objects.
 * Example usage: <br>
 *     {@code TopDeck td = new TopDeckBuilder().withCard("Question1", "Question2").build();}
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
     * Adds a new {@code Card} to the {@code TopDeck} that we are building.
     */
    public TopDeckBuilder withCard(Card card) {
        topDeck.addCard(card);
        return this;
    }

    public TopDeck build() {
        return topDeck;
    }
}
