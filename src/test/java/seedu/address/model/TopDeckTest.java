package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;
import static seedu.address.testutil.TypicalDecks.DECK_A;

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
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.exceptions.DuplicateDeckException;
import seedu.address.testutil.DeckBuilder;

public class TopDeckTest {
    private final TopDeck topDeck = new TopDeck();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
