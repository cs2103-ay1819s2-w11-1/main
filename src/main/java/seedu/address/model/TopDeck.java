package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.UniqueDeckList;
import seedu.address.model.deck.exceptions.CardNotFoundException;
import seedu.address.model.deck.exceptions.DeckImportException;
import seedu.address.model.deck.exceptions.DeckNotFoundException;
import seedu.address.model.deck.exceptions.DuplicateCardException;
import seedu.address.model.deck.exceptions.DuplicateDeckException;
import seedu.address.storage.portmanager.PortManager;

/**
 * Wraps all data at the TopDeck level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class TopDeck implements ReadOnlyTopDeck {
    private final UniqueDeckList decks;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    // Manager to handle imports/exports
    private PortManager portManager;
    /*
    * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid
    * duplication
    * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
    *
    * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
    *   among constructors.
    */
    {
        decks = new UniqueDeckList();
        portManager = new PortManager();
    }

    public TopDeck() {
    }

    /**
     * Creates TopDeck using the data in the {@code toBeCopied}
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
     * Notifies listeners that TopDeck has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// card operations

    /**
     * Adds a card to TopDeck
     * The card should not already exist in the {@code deck} activeDeck.
     */
    public Deck addCard(Card card, Deck activeDeck) throws DuplicateCardException, DeckNotFoundException {
        requireAllNonNull(card, activeDeck);

        if (!decks.contains(activeDeck)) {
            throw new DeckNotFoundException();
        }

        if (activeDeck.hasCard(card)) {
            throw new DuplicateCardException();
        }

        Deck editedDeck = new Deck(activeDeck);
        editedDeck.addCard(card);
        decks.setDeck(activeDeck, editedDeck);

        indicateModified();

        return editedDeck;
    }

    /**
     * Deletes a card in TopDeck
     * The {@code Card} target should exist in the {@code deck} activeDeck.
     */
    public Deck deleteCard(Card target, Deck activeDeck) throws DeckNotFoundException, CardNotFoundException {
        if (!decks.contains(activeDeck)) {
            throw new DeckNotFoundException();
        }

        if (!activeDeck.hasCard(target)) {
            throw new CardNotFoundException();
        }

        Deck editedDeck = new Deck(activeDeck);
        editedDeck.removeCard(target);
        decks.setDeck(activeDeck, editedDeck);

        indicateModified();

        return editedDeck;
    }

    /**
     * Sets a card in TopDeck
     * The {@code Card} target should exist in the {@code deck} activeDeck.
     */
    public Deck setCard(Card target, Card newCard, Deck activeDeck) throws DeckNotFoundException,
            CardNotFoundException {
        if (!decks.contains(activeDeck)) {
            throw new DeckNotFoundException();
        }

        if (!activeDeck.hasCard(target)) {
            throw new CardNotFoundException();
        }

        Deck editedDeck = new Deck(activeDeck);
        editedDeck.setCard(target, newCard);

        decks.setDeck(activeDeck, editedDeck);

        indicateModified();

        return editedDeck;
    }

    //// deck operations

    /**
     * Returns the deck in TopDeck that has the same identifier as {@code target}.
     * The deck must already be in TopDeck.
     */
    public Deck getDeck(Deck target) {
        return decks.getDeck(target);
    }

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

    /**
     * Attempts to import a deck at the specified file location.
     * If there is an existing duplicate deck, throw DuplicateDeckException.
     * If there was a problem with the import action, throw DeckImportException
     */
    public Deck importDeck(String filepath) throws DuplicateDeckException, DeckImportException {
        Deck targetDeck = portManager.importDeck(filepath);
        if (decks.contains(targetDeck)) {
            throw new DuplicateDeckException();
        }
        return targetDeck;
    }

    /**
     * Attempts to export {@deck}
     * Returns the exported file location as a string.
     */
    public String exportDeck(Deck deck) {
        try {
            return portManager.exportDeck(deck);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //// util methods

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TopDeck: " + decks.asUnmodifiableObservableList().size() + " decks\n");
        Iterator<Deck> iterator = decks.iterator();
        while (iterator.hasNext()) {
            Deck cur = iterator.next();
            stringBuilder.append(cur.toString() + "\n");
            Iterator<Card> cardIterator = cur.getCards().iterator();
            while (cardIterator.hasNext()) {
                stringBuilder.append("\t" + cardIterator.next().toString() + "\n");
            }
        }
        return stringBuilder.toString();
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
