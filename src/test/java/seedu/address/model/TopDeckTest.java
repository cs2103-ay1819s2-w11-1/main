package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalCards.ADDITION;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;

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
import seedu.address.model.deck.exceptions.DuplicateCardException;
import seedu.address.testutil.CardBuilder;

public class TopDeckTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TopDeck topDeck = new TopDeck();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), topDeck.getCardList());
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
    public void resetData_withDuplicateCards_throwsDuplicateCardException() {
        // Two cards with the same question
        Card editedAddition = new CardBuilder(ADDITION).withAnswer("Some answer").build();
        List<Card> newCards = Arrays.asList(ADDITION, editedAddition);
        TopDeckStub newData = new TopDeckTest.TopDeckStub(newCards);

        thrown.expect(DuplicateCardException.class);
        topDeck.resetData(newData);
    }

    @Test
    public void hasCard_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        topDeck.hasCard(null);
    }

    @Test
    public void hasCard_cardNotInTopDeck_returnsFalse() {
        assertFalse(topDeck.hasCard(ADDITION));
    }

    @Test
    public void hasCard_cardInTopDeck_returnsTrue() {
        topDeck.addCard(ADDITION);
        Assert.assertTrue(topDeck.hasCard(ADDITION));
    }

    @Test
    public void hasCard_cardWithSameIdentityInTopDeck_returnsTrue() {
        topDeck.addCard(ADDITION);
        Card editedAddition = new CardBuilder(ADDITION).withAnswer("Different answer").build();
        Assert.assertTrue(topDeck.hasCard(editedAddition));
    }

    @Test
    public void getCardList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        topDeck.getCardList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        topDeck.addListener(listener);
        topDeck.addCard(ADDITION);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        topDeck.addListener(listener);
        topDeck.removeListener(listener);
        topDeck.addCard(ADDITION);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyTopDeck whose cards list can violate interface constraints.
     */
    private static class TopDeckStub implements ReadOnlyTopDeck {
        private final ObservableList<Card> cards = FXCollections.observableArrayList();

        TopDeckStub(Collection<Card> cards) {
            this.cards.setAll(cards);
        }

        @Override
        public ObservableList<Card> getCardList() {
            return cards;
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
