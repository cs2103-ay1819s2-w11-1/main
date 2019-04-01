package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalDecks.DECK_A;
import static seedu.address.testutil.TypicalDecks.DECK_B;
import static seedu.address.testutil.TypicalDecks.DECK_C;
import static seedu.address.testutil.TypicalDecks.DECK_D;
import static seedu.address.testutil.TypicalDecks.DECK_E;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.TopDeckBuilder;

public class VersionedTopDeckTest {

    //Empty decks
    private final ReadOnlyTopDeck topDeckWithDeckA = new TopDeckBuilder().withDeck(DECK_A).build();
    private final ReadOnlyTopDeck topDeckWithDeckB = new TopDeckBuilder().withDeck(DECK_B).build();
    private final ReadOnlyTopDeck topDeckWithDeckC = new TopDeckBuilder().withDeck(DECK_C).build();

    //Decks with cards
    private final ReadOnlyTopDeck topDeckWithDeckD = new TopDeckBuilder().withDeck(DECK_D).build();
    private final ReadOnlyTopDeck topDeckWithDeckE = new TopDeckBuilder().withDeck(DECK_E).build();


    private final ReadOnlyTopDeck emptyTopDeck = new TopDeckBuilder().build();


    //    private final ReadOnlyTopDeck topDeckWithAdd = new TopDeckBuilder().withCard(ADDITION).build();
    //    private final ReadOnlyTopDeck topDeckWithSub = new TopDeckBuilder().withCard(SUBTRACTION).build();
    //    private final ReadOnlyTopDeck topDeckWithMul = new TopDeckBuilder().withCard(MULTIPLICATION)
    //    .build();
    //    private final ReadOnlyTopDeck emptyTopDeck = new TopDeckBuilder().build();

    @Test
    public void commit_singleTopDeck_noStatesRemovedCurrentStateSaved() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck);

        versionedTopDeck.commit();
        assertTopDeckListStatus(versionedTopDeck, Collections.singletonList(emptyTopDeck), emptyTopDeck,
                                Collections.emptyList());
    }

    @Test
    public void commit_multipleTopDeckPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck, topDeckWithDeckA,
                                                               topDeckWithDeckB);

        versionedTopDeck.commit();
        assertTopDeckListStatus(versionedTopDeck,
                                Arrays.asList(emptyTopDeck, topDeckWithDeckA, topDeckWithDeckB),
                                topDeckWithDeckB, Collections.emptyList());
    }

    @Test
    public void commit_multipleTopDeckPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck, topDeckWithDeckA,
                                                               topDeckWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedTopDeck, 2);

        versionedTopDeck.commit();
        assertTopDeckListStatus(versionedTopDeck, Collections.singletonList(emptyTopDeck), emptyTopDeck,
                                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleTopDeckPointerAtEndOfStateList_returnsTrue() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck, topDeckWithDeckA,
                                                               topDeckWithDeckB);

        assertTrue(versionedTopDeck.canUndo());
    }

    @Test
    public void canUndo_multipleTopDeckPointerAtStartOfStateList_returnsTrue() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck, topDeckWithDeckA,
                                                               topDeckWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedTopDeck, 1);

        assertTrue(versionedTopDeck.canUndo());
    }

    @Test
    public void canUndo_singleTopDeck_returnsFalse() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck);

        assertFalse(versionedTopDeck.canUndo());
    }

    @Test
    public void canUndo_multipleTopDeckPointerAtStartOfStateList_returnsFalse() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck, topDeckWithDeckA,
                                                               topDeckWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedTopDeck, 2);

        assertFalse(versionedTopDeck.canUndo());
    }

    @Test
    public void canRedo_multipleTopDeckPointerNotAtEndOfStateList_returnsTrue() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck, topDeckWithDeckA,
                                                               topDeckWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedTopDeck, 1);

        assertTrue(versionedTopDeck.canRedo());
    }

    @Test
    public void canRedo_multipleTopDeckPointerAtStartOfStateList_returnsTrue() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck, topDeckWithDeckA,
                                                               topDeckWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedTopDeck, 2);

        assertTrue(versionedTopDeck.canRedo());
    }

    @Test
    public void canRedo_singleTopDeck_returnsFalse() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck);

        assertFalse(versionedTopDeck.canRedo());
    }

    @Test
    public void canRedo_multipleTopDeckPointerAtEndOfStateList_returnsFalse() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck, topDeckWithDeckA,
                                                               topDeckWithDeckB);

        assertFalse(versionedTopDeck.canRedo());
    }

    @Test
    public void undo_multipleTopDeckPointerAtEndOfStateList_success() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck, topDeckWithDeckA,
                                                               topDeckWithDeckB);

        versionedTopDeck.undo();
        assertTopDeckListStatus(versionedTopDeck, Collections.singletonList(emptyTopDeck), topDeckWithDeckA,
                                Collections.singletonList(topDeckWithDeckB));
    }

    @Test
    public void undo_multipleTopDeckPointerNotAtStartOfStateList_success() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck, topDeckWithDeckA,
                                                               topDeckWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedTopDeck, 1);

        versionedTopDeck.undo();
        assertTopDeckListStatus(versionedTopDeck, Collections.emptyList(), emptyTopDeck,
                                Arrays.asList(topDeckWithDeckA, topDeckWithDeckB));
    }

    @Test
    public void undo_singleTopDeck_throwsNoUndoableStateException() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck);

        assertThrows(VersionedTopDeck.NoUndoableStateException.class, versionedTopDeck::undo);
    }

    @Test
    public void undo_multipleTopDeckPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck, topDeckWithDeckA,
                                                               topDeckWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedTopDeck, 2);

        assertThrows(VersionedTopDeck.NoUndoableStateException.class, versionedTopDeck::undo);
    }

    @Test
    public void redo_multipleTopDeckPointerNotAtEndOfStateList_success() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck, topDeckWithDeckA,
                                                               topDeckWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedTopDeck, 1);

        versionedTopDeck.redo();
        assertTopDeckListStatus(versionedTopDeck, Arrays.asList(emptyTopDeck, topDeckWithDeckA),
                                topDeckWithDeckB, Collections.emptyList());
    }

    @Test
    public void redo_multipleTopDeckPointerAtStartOfStateList_success() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck, topDeckWithDeckA,
                                                               topDeckWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedTopDeck, 2);

        versionedTopDeck.redo();
        assertTopDeckListStatus(versionedTopDeck, Collections.singletonList(emptyTopDeck), topDeckWithDeckA,
                                Collections.singletonList(topDeckWithDeckB));
    }

    @Test
    public void redo_singleTopDeck_throwsNoRedoableStateException() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck);

        assertThrows(VersionedTopDeck.NoRedoableStateException.class, versionedTopDeck::redo);
    }

    @Test
    public void redo_multipleTopDeckPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(emptyTopDeck, topDeckWithDeckA,
                                                               topDeckWithDeckB);

        assertThrows(VersionedTopDeck.NoRedoableStateException.class, versionedTopDeck::redo);
    }

    @Test
    public void equals() {
        VersionedTopDeck versionedTopDeck = prepareTopDeckList(topDeckWithDeckA, topDeckWithDeckB);

        // same values -> returns true
        VersionedTopDeck copy = prepareTopDeckList(topDeckWithDeckA, topDeckWithDeckB);
        assertTrue(versionedTopDeck.equals(copy));

        // same object -> returns true
        assertTrue(versionedTopDeck.equals(versionedTopDeck));

        // null -> returns false
        assertFalse(versionedTopDeck.equals(null));

        // different types -> returns false
        assertFalse(versionedTopDeck.equals(1));

        // different state list -> returns false
        VersionedTopDeck differentAddressBookList = prepareTopDeckList(topDeckWithDeckB, topDeckWithDeckC);
        assertFalse(versionedTopDeck.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedTopDeck differentCurrentStatePointer = prepareTopDeckList(topDeckWithDeckA,
                                                                           topDeckWithDeckB);
        shiftCurrentStatePointerLeftwards(versionedTopDeck, 1);
        assertFalse(versionedTopDeck.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedTopDeck} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedTopDeck#currentStatePointer} is equal to {@code
     * expectedStatesBeforePointer},
     * and states after {@code versionedTopDeck#currentStatePointer} is equal to {@code
     * expectedStatesAfterPointer}.
     */
    private void assertTopDeckListStatus(VersionedTopDeck versionedTopDeck,
                                         List<ReadOnlyTopDeck> expectedStatesBeforePointer,
                                         ReadOnlyTopDeck expectedCurrentState,
                                         List<ReadOnlyTopDeck> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new TopDeck(versionedTopDeck), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedTopDeck.canUndo()) {
            versionedTopDeck.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyTopDeck expectedTopDeck : expectedStatesBeforePointer) {
            assertEquals(expectedTopDeck, new TopDeck(versionedTopDeck));
            versionedTopDeck.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyTopDeck expectedTopDeck : expectedStatesAfterPointer) {
            versionedTopDeck.redo();
            assertEquals(expectedTopDeck, new TopDeck(versionedTopDeck));
        }

        // check that there are no more states after pointer
        assertFalse(versionedTopDeck.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedTopDeck.undo());
    }

    /**
     * Creates and returns a {@code VersionedTopDeck} with the {@code topDeckStates} added into it, and the
     * {@code VersionedTopDeck#currentStatePointer} at the end of list.
     */
    private VersionedTopDeck prepareTopDeckList(ReadOnlyTopDeck... topDeckStates) {
        assertFalse(topDeckStates.length == 0);

        VersionedTopDeck versionedTopDeck = new VersionedTopDeck(topDeckStates[0]);
        for (int i = 1; i < topDeckStates.length; i++) {
            versionedTopDeck.resetData(topDeckStates[i]);
            versionedTopDeck.commit();
        }

        return versionedTopDeck;
    }

    /**
     * Shifts the {@code versionedTopDeck#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedTopDeck versionedTopDeck, int count) {
        for (int i = 0; i < count; i++) {
            versionedTopDeck.undo();
        }
    }
}
