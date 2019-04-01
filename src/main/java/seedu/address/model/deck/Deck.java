package seedu.address.model.deck;

import java.util.List;
import java.util.Objects;

import seedu.address.logic.ListItem;
import seedu.address.model.deck.exceptions.CardNotFoundException;

/**
 * Represents a Deck inside TopDeck.
 */
public class Deck implements ListItem {

    private final Name name;
    private final UniqueCardList cards;

    public Deck(Name name) {
        this.name = name;
        cards = new UniqueCardList();
    }

    public Deck(Name name, List<Card> cards) {
        this(name);
        for (Card card : cards) {
            this.cards.add(card);
        }
    }

    public Deck(Deck other) {
        this.name = new Name(other.name.toString());
        this.cards = new UniqueCardList();
        this.cards.setCards(other.getCards());
    }

    public Name getName() {
        return name;
    }

    public UniqueCardList getCards() {
        return cards;
    }

    /**
     * Checks if a card is already in a deck.
     */
    public boolean hasCard(Card toCheck) {
        return cards.contains(toCheck);
    }

    /**
     * Checks if a deck is empty.
     */
    public boolean isEmpty() {
        return cards.internalList.isEmpty();
    }


    /**
     * Adds a card to the current deck.
     */
    public void addCard(Card newCard) {
        this.cards.add(newCard);
    }

    /**
     * Replaces {@code target} in the deck with {@code editedCard}.
     */
    public void setCard(Card target, Card editedCard) {
        assert hasCard(target);

        cards.setCard(target, editedCard);
    }

    /**
     * Removes {@code target} in the current deck.
     */
    public void removeCard(Card target) throws CardNotFoundException {
        assert hasCard(target);

        this.cards.remove(target);
    }

    /**
     * Returns true if 2 decks are the same, or have identical name.
     */
    public boolean isSameDeck(Deck otherDeck) {
        if (otherDeck == this) {
            return true;
        }

        if (otherDeck == null) {
            return false;
        }

        return otherDeck.getName().equals(getName());
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Deck)) {
            return false;
        }

        return isSameDeck((Deck) other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }

}

