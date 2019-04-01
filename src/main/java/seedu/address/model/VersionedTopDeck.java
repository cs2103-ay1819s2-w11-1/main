package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code TopDeck} that keeps track of its own history.
 */
public class VersionedTopDeck extends TopDeck {
    private final List<ReadOnlyTopDeck> topDeckStateList;
    private int currentStatePointer;

    public VersionedTopDeck(ReadOnlyTopDeck initialState) {
        super(initialState);

        topDeckStateList = new ArrayList<>();
        topDeckStateList.add(new TopDeck(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code TopDeck} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        topDeckStateList.add(new TopDeck(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        topDeckStateList.subList(currentStatePointer + 1, topDeckStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new VersionedTopDeck.NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(topDeckStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new VersionedTopDeck.NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(topDeckStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < topDeckStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedTopDeck)) {
            return false;
        }

        VersionedTopDeck otherVersionedTopDeck = (VersionedTopDeck) other;

        // state check
        return super.equals(otherVersionedTopDeck) && topDeckStateList
                .equals(otherVersionedTopDeck.topDeckStateList)
                && currentStatePointer == otherVersionedTopDeck.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of addressBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of addressBookState list, unable to redo.");
        }
    }
}
