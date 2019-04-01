package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDeckAtIndex;
import static seedu.address.testutil.TypicalDecks.getTypicalTopDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DECK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.DecksView;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Deck;

/**
 * Contains integration tests (interaction with the TopDeck Model) and unit tests for
 * {@code DeleteDeckCommand}.
 */
public class DeleteDeckCommandTest {

    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private DecksView decksView = (DecksView) model.getViewState();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Deck deckToDelete = decksView.getFilteredList().get(INDEX_FIRST_DECK.getZeroBased());
        DeleteDeckCommand deleteCommand = new DeleteDeckCommand(decksView, INDEX_FIRST_DECK);

        String expectedMessage = String.format(DeleteDeckCommand.MESSAGE_DELETE_DECK_SUCCESS, deckToDelete);

        ModelManager expectedModel = new ModelManager(model.getTopDeck(), new UserPrefs());
        expectedModel.deleteDeck(deckToDelete);
        expectedModel.commitTopDeck();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(decksView.getFilteredList().size() + 1);
        DeleteDeckCommand deleteCommand = new DeleteDeckCommand(decksView, outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                             Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showDeckAtIndex(model, INDEX_FIRST_DECK);

        Deck deckToDelete = decksView.getFilteredList().get(INDEX_FIRST_DECK.getZeroBased());
        DeleteDeckCommand deleteCommand = new DeleteDeckCommand(decksView, INDEX_FIRST_DECK);

        String expectedMessage = String.format(DeleteDeckCommand.MESSAGE_DELETE_DECK_SUCCESS, deckToDelete);

        Model expectedModel = new ModelManager(model.getTopDeck(), new UserPrefs());
        expectedModel.deleteDeck(deckToDelete);
        expectedModel.commitTopDeck();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showDeckAtIndex(model, INDEX_FIRST_DECK);

        Index outOfBoundIndex = INDEX_SECOND_DECK;
        // ensures that outOfBoundIndex is still in bounds of TopDeck list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTopDeck().getDeckList().size());

        DeleteDeckCommand deleteCommand = new DeleteDeckCommand(decksView, outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                             Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteDeckCommand deleteFirstCommand = new DeleteDeckCommand(decksView, INDEX_FIRST_DECK);
        DeleteDeckCommand deleteSecondCommand = new DeleteDeckCommand(decksView, INDEX_SECOND_DECK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteDeckCommand deleteFirstCommandCopy = new DeleteDeckCommand(decksView, INDEX_FIRST_DECK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different deck -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoDeck(Model model) {
        decksView.updateFilteredList(p -> false);

        assertTrue(decksView.getFilteredList().isEmpty());
    }
}

