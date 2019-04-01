package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalCards.ADDITION;
import static seedu.address.testutil.TypicalCards.UNIQUE;
import static seedu.address.testutil.TypicalDecks.DECK_A;
import static seedu.address.testutil.TypicalDecks.DECK_F;
import static seedu.address.testutil.TypicalDecks.DECK_WITH_CARDS;
import static seedu.address.testutil.TypicalDecks.getTypicalTopDeck;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.exceptions.CardNotFoundException;
import seedu.address.model.deck.exceptions.DeckNotFoundException;
import seedu.address.model.deck.exceptions.DuplicateCardException;
import seedu.address.model.deck.exceptions.DuplicateDeckException;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.DeckBuilder;

public class TopDeckTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TopDeck topDeck = new TopDeck();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), topDeck.getDeckList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        topDeck.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTopDeck_replacesData() {
        TopDeck newData = getTypicalTopDeck();
        topDeck.resetData(newData);
        assertEquals(newData, topDeck);
    }

    @Test
    public void resetData_withDuplicateDecks_throwsDuplicateDeckException() {
        // Two decks with the same name
        Deck editedDeckA = new DeckBuilder(DECK_A).build();
        List<Deck> newDecks = Arrays.asList(DECK_A, editedDeckA);
        TopDeckStub newData = new TopDeckTest.TopDeckStub(newDecks);

        thrown.expect(DuplicateDeckException.class);
        topDeck.resetData(newData);
    }

    @Test
    public void hasDeck_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        topDeck.hasDeck(null);
    }

    @Test
    public void hasDeck_deckNotInTopDeck_returnsFalse() {
        assertFalse(topDeck.hasDeck(DECK_A));
    }

    @Test
    public void hasDeck_deckInTopDeck_returnsTrue() {
        topDeck.addDeck(DECK_A);
        Assert.assertTrue(topDeck.hasDeck(DECK_A));
    }

    @Test
    public void hasDeck_deckWithSameIdentityInTopDeck_returnsTrue() {
        topDeck.addDeck(DECK_A);
        Deck editedDeckA = new DeckBuilder(DECK_A).build();
        Assert.assertTrue(topDeck.hasDeck(editedDeckA));
    }

    @Test
    public void getDeckList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        topDeck.getDeckList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        topDeck.addListener(listener);
        topDeck.addDeck(DECK_A);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        topDeck.addListener(listener);
        topDeck.removeListener(listener);
        topDeck.addDeck(DECK_A);
        assertEquals(0, counter.get());
    }

    @Test
    public void addCard_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        topDeck.addCard(null, null);
    }

    @Test
    public void addCard_deckNotInList_throwsDeckNotFoundException() {
        TopDeck typicalTopDeck = getTypicalTopDeck();
        thrown.expect(DeckNotFoundException.class);
        typicalTopDeck.addCard(UNIQUE, DECK_F);
    }

    @Test
    public void addCard_cardInList_throwsDuplicateCardException() {
        TopDeck typicalTopDeck = getTypicalTopDeck();
        thrown.expect(DuplicateCardException.class);
        typicalTopDeck.addCard(ADDITION, DECK_WITH_CARDS);
    }

    @Test
    public void addCard_validCard_returnsEditedDeck() {
        TopDeck typicalTopDeck = getTypicalTopDeck();
        Deck newDeck = typicalTopDeck.addCard(ADDITION, DECK_A);

        Deck expectedDeck = new DeckBuilder(DECK_A).withCards(Arrays.asList(ADDITION)).build();

        assertEquals(newDeck, expectedDeck);
    }

    @Test
    public void deleteCard_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        topDeck.deleteCard(null, null);
    }

    @Test
    public void deleteCard_deckNotInList_throwsDeckNotFoundException() {
        TopDeck typicalTopDeck = getTypicalTopDeck();
        thrown.expect(DeckNotFoundException.class);
        typicalTopDeck.deleteCard(UNIQUE, DECK_F);
    }

    @Test
    public void deleteCard_cardNotInList_throwsCardNotFoundException() {
        TopDeck typicalTopDeck = getTypicalTopDeck();
        thrown.expect(CardNotFoundException.class);
        typicalTopDeck.deleteCard(ADDITION, DECK_A);
    }

    @Test
    public void deleteCard_cardInList_returnsEditedDeck() {
        TopDeck typicalTopDeck = getTypicalTopDeck();
        Deck newDeck = typicalTopDeck.deleteCard(ADDITION, DECK_WITH_CARDS);

        Deck expectedDeck = new DeckBuilder(DECK_WITH_CARDS).build();
        expectedDeck.removeCard(ADDITION);

        assertEquals(newDeck, expectedDeck);
    }

    @Test
    public void setCard_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        topDeck.setCard(null, null, null);
    }

    @Test
    public void setCard_deckNotInList_throwsDeckNotFoundException() {
        TopDeck typicalTopDeck = getTypicalTopDeck();
        thrown.expect(DeckNotFoundException.class);
        typicalTopDeck.setCard(UNIQUE, ADDITION, DECK_F);
    }

    @Test
    public void setCard_cardNotInList_throwsCardNotFoundException() {
        TopDeck typicalTopDeck = getTypicalTopDeck();
        thrown.expect(CardNotFoundException.class);
        typicalTopDeck.setCard(ADDITION, UNIQUE, DECK_A);
    }

    @Test
    public void setCard_cardInList_returnsEditedDeck() {
        TopDeck typicalTopDeck = getTypicalTopDeck();
        Card newCard = new CardBuilder().withQuestion("Edited Question").withAnswer("Edited Answer").build();
        Deck newDeck = typicalTopDeck.setCard(ADDITION, newCard, DECK_WITH_CARDS);

        Deck expectedDeck = new DeckBuilder(DECK_WITH_CARDS).build();
        expectedDeck.setCard(ADDITION, newCard);

        assertEquals(newDeck, expectedDeck);
    }

    /**
     * A stub ReadOnlyTopDeck whose cards list can violate interface constraints.
     */
    private static class TopDeckStub implements ReadOnlyTopDeck {
        private final ObservableList<Deck> decks = FXCollections.observableArrayList();

        TopDeckStub(Collection<Deck> decks) {
            this.decks.setAll(decks);
        }

        @Override
        public ObservableList<Deck> getDeckList() {
            return decks;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
