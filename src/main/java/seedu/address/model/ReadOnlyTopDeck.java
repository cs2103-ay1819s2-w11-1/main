package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.deck.Card;

/**
 * Unmodifiable view of TopDeck
 */
public interface ReadOnlyTopDeck extends Observable {
    /**
     * Returns an unmodifiable view of the cards list.
     * This list will not contain any duplicate cards.
     */
    ObservableList<Card> getCardList();
}
