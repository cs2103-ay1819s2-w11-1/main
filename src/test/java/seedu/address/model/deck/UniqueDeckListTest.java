package seedu.address.model.deck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARD_LIST;
import static seedu.address.testutil.TypicalDecks.DECK_A;
import static seedu.address.testutil.TypicalDecks.DECK_B;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.deck.exceptions.DeckNotFoundException;
import seedu.address.model.deck.exceptions.DuplicateDeckException;
import seedu.address.testutil.DeckBuilder;

public class UniqueDeckListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueDeckList uniqueDeckList = new UniqueDeckList();

    @Test
    public void contains_nullDeck_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDeckList.contains(null);
    }

    @Test
    public void contains_deckNotInList_returnsFalse() {
        assertFalse(uniqueDeckList.contains(DECK_A));
    }

    @Test
    public void contains_deckInList_returnsTrue() {
        uniqueDeckList.add(DECK_A);
        assertTrue(uniqueDeckList.contains(DECK_A));
    }

    @Test
    public void contains_deckWithSameIdentityFieldsInList_returnsTrue() {
        uniqueDeckList.add(DECK_A);
        Deck editedDeckA = new DeckBuilder(DECK_A).withCards(VALID_CARD_LIST).build();
        assertTrue(uniqueDeckList.contains(editedDeckA));
    }

    @Test
    public void add_nullDeck_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDeckList.add(null);
    }

    @Test
    public void add_duplicateDeck_throwsDuplicateDeckException() {
        uniqueDeckList.add(DECK_A);
        thrown.expect(DuplicateDeckException.class);
        uniqueDeckList.add(DECK_A);
    }

    @Test
    public void getDeck_nullTargetDeck_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDeckList.getDeck(null);
    }

    @Test
    public void getDeck_targetDeckNotInList_throwsDeckNotFoundException() {
        thrown.expect(DeckNotFoundException.class);
        uniqueDeckList.getDeck(DECK_A);
    }

    @Test
    public void getDeck_targetDeckInList_success() {
        uniqueDeckList.add(DECK_A);
        uniqueDeckList.add(DECK_B);
        Deck retrievedDeck = uniqueDeckList.getDeck(DECK_A);
        assertTrue(retrievedDeck.isSameDeck(DECK_A));
    }

    @Test
    public void setDeck_nullTargetDeck_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDeckList.setDeck(null, DECK_A);
    }

    @Test
    public void setDeck_nullEditedDeck_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDeckList.setDeck(DECK_A, null);
    }

    @Test
    public void setDeck_targetDeckNotInList_throwsDeckNotFoundException() {
        thrown.expect(DeckNotFoundException.class);
        uniqueDeckList.setDeck(DECK_B, DECK_B);
    }

    @Test
    public void setDeck_editedDeckIsSameDeck_success() {
        uniqueDeckList.add(DECK_A);
        uniqueDeckList.setDeck(DECK_A, DECK_A);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        expectedUniqueDeckList.add(DECK_A);
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDeck_editedDeckHasSameIdentity_success() {
        uniqueDeckList.add(DECK_A);
        Deck editedDeckA = new DeckBuilder(DECK_A).withCards(VALID_CARD_LIST).build();
        uniqueDeckList.setDeck(DECK_A, editedDeckA);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        expectedUniqueDeckList.add(editedDeckA);
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDeck_editedDeckHasDifferentIdentity_success() {
        uniqueDeckList.add(DECK_A);
        uniqueDeckList.setDeck(DECK_A, DECK_B);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        expectedUniqueDeckList.add(DECK_B);
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDeck_editedDeckHasNonUniqueIdentity_throwsDuplicateDeckException() {
        uniqueDeckList.add(DECK_A);
        uniqueDeckList.add(DECK_B);
        thrown.expect(DuplicateDeckException.class);
        uniqueDeckList.setDeck(DECK_A, DECK_B);
    }

    @Test
    public void remove_nullDeck_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDeckList.remove(null);
    }

    @Test
    public void remove_deckDoesNotExist_throwsDeckNotFoundException() {
        thrown.expect(DeckNotFoundException.class);
        uniqueDeckList.remove(DECK_A);
    }

    @Test
    public void remove_existingDeck_removesDeck() {
        uniqueDeckList.add(DECK_A);
        uniqueDeckList.remove(DECK_A);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDecks_nullUniqueDeckList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDeckList.setDecks((UniqueDeckList) null);
    }

    @Test
    public void setDecks_uniqueDeckList_replacesOwnListWithProvidedUniqueDeckList() {
        uniqueDeckList.add(DECK_A);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        expectedUniqueDeckList.add(DECK_B);
        uniqueDeckList.setDecks(expectedUniqueDeckList);
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDecks_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDeckList.setDecks((List<Deck>) null);
    }

    @Test
    public void setDecks_list_replacesOwnListWithProvidedList() {
        uniqueDeckList.add(DECK_A);
        List<Deck> deckList = Collections.singletonList(DECK_B);
        uniqueDeckList.setDecks(deckList);
        UniqueDeckList expectedUniqueDeckList = new UniqueDeckList();
        expectedUniqueDeckList.add(DECK_B);
        assertEquals(expectedUniqueDeckList, uniqueDeckList);
    }

    @Test
    public void setDecks_listWithDuplicateDecks_throwsDuplicateDeckException() {
        List<Deck> listWithDuplicateDecks = Arrays.asList(DECK_A, DECK_A);
        thrown.expect(DuplicateDeckException.class);
        uniqueDeckList.setDecks(listWithDuplicateDecks);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueDeckList.asUnmodifiableObservableList().remove(0);
    }

}
