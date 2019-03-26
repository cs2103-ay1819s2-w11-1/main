package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.UniqueDeckList;
import seedu.address.model.deck.exceptions.CardNotFoundException;
import seedu.address.model.deck.exceptions.DeckNotFoundException;
import seedu.address.model.deck.exceptions.DuplicateCardException;
import seedu.address.model.deck.exceptions.DuplicateDeckException;

/**
 * Wraps all data at the TopDeck level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class TopDeck implements ReadOnlyTopDeck {
    private final UniqueDeckList decks;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */ {
        decks = new UniqueDeckList();
    }

    public TopDeck() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public TopDeck(ReadOnlyTopDeck toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the deck list with {@code decks}.
     * {@code decks} must not contain duplicate decks.
     */
    public void setDecks(List<Deck> decks) {
        this.decks.setDecks(decks);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code TopDeck} with {@code newData}.
     */
    public void resetData(ReadOnlyTopDeck newData) {
        requireNonNull(newData);

        setDecks(newData.getDeckList());
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// card operations

    /**
     * Adds a card to TopDeck
     * The card should not already exist in the {@code deck} activeDeck.
     */
    public void addCard(Card card, Deck activeDeck) throws DuplicateCardException, DeckNotFoundException {
        if (!decks.contains(activeDeck)) {
            throw new DeckNotFoundException();
        }

        if (activeDeck.hasCard(card)) {
            throw new DuplicateCardException();
        }

        activeDeck.addCard(card);

        decks.setDeck(activeDeck, activeDeck);

        indicateModified();
    }

    /**
     * Deletes a card in TopDeck
     * The {@code Card} target should exist in the {@code deck} activeDeck.
     */
    public void deleteCard(Card target, Deck activeDeck) throws DeckNotFoundException, CardNotFoundException {
        if (!decks.contains(activeDeck)) {
            throw new DeckNotFoundException();
        }

        if (!activeDeck.hasCard(target)) {
            throw new CardNotFoundException();
        }

        activeDeck.removeCard(target);

        decks.setDeck(activeDeck, activeDeck);

        indicateModified();
    }

    /**
     * Sets a card in TopDeck
     * The {@code Card} target should exist in the {@code deck} activeDeck.
     */
    public void setCard(Card target, Card newCard, Deck activeDeck) throws DeckNotFoundException,
            CardNotFoundException {
        if (!decks.contains(activeDeck)) {
            throw new DeckNotFoundException();
        }

        if (!activeDeck.hasCard(target)) {
            throw new CardNotFoundException();
        }

        activeDeck.removeCard(target);
        activeDeck.addCard(newCard);

        decks.setDeck(activeDeck, activeDeck);

        indicateModified();
    }

    //// deck operations

    /**
     * Adds a deck to the TopDeck.
     * The deck must not already exist in the TopDeck.
     */
    public void addDeck(Deck deck) throws DuplicateDeckException {
        decks.add(deck);
        indicateModified();
    }

    /**
     * Returns true if a deck with the same identity as {@code deck} exists in TopDeck.
     */
    public boolean hasDeck(Deck deck) {
        requireNonNull(deck);
        return decks.contains(deck);
    }

    /**
     * Deletes {@code deck} from this {@code TopDeck}.
     * The {@code deck} target should exist in {@code TopDeck}.
     */
    public void deleteDeck(Deck target) throws DeckNotFoundException {
        if (!decks.contains(target)) {
            throw new DeckNotFoundException();
        }

        decks.remove(target);
    }

    /**
     * Replaces the given deck {@code target} in the list with {@code editedDeck}.
     * {@code target} must exist in TopDeck.
     * The deck identity of {@code editedDeck} must not be the same as another existing deck in TopDeck.
     */
    public void updateDeck(Deck target, Deck editedDeck) {
        requireNonNull(editedDeck);
        decks.setDeck(target, editedDeck);
    }

    //// util methods

    @Override
    public String toString() {
        return decks.asUnmodifiableObservableList().size() + " decks";
        // TODO: refine later
    }

    @Override
    public ObservableList<Deck> getDeckList() {
        return decks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TopDeck // instanceof handles nulls
                && decks.equals(((TopDeck) other).decks));
    }

    @Override
    public int hashCode() {
        return decks.hashCode();
    }
}
