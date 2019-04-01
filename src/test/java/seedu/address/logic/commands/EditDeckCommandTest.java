package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_DECK;
import static seedu.address.commons.core.Messages.MESSAGE_EDIT_DECK_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.DESC_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARD_LIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertUpdateCommandSuccess;
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
import seedu.address.testutil.DeckBuilder;
import seedu.address.testutil.EditDeckDescriptorBuilder;


/**
 * Contains integration tests (interaction with the TopDeck Model, UndoCommand and RedoCommand) and unit
 * tests for
 * EditCommand.
 */
public class EditDeckCommandTest {

    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private DecksView decksView = (DecksView) model.getViewState();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Deck editedDeck = new DeckBuilder().build();
        EditDeckCommand.EditDeckDescriptor descriptor = new EditDeckDescriptorBuilder(editedDeck).build();
        EditDeckCommand editCommand = new EditDeckCommand(decksView, INDEX_FIRST_DECK, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_DECK_SUCCESS, editedDeck);

        ModelManager expectedModel = new ModelManager(model.getTopDeck(), new UserPrefs());
        expectedModel.updateDeck(decksView.getFilteredList().get(0), editedDeck);
        expectedModel.commitTopDeck();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTopDeckDeck = Index.fromOneBased(decksView.getFilteredList().size());
        Deck lastDeck = decksView.getFilteredList().get(indexLastTopDeckDeck.getZeroBased());

        DeckBuilder deckInList = new DeckBuilder(lastDeck);
        Deck editedDeck = deckInList.withName(VALID_NAME_JOHN).withCards(VALID_CARD_LIST).build();

        EditDeckCommand.EditDeckDescriptor descriptor = new EditDeckDescriptorBuilder()
                .withName(VALID_NAME_JOHN).withCards(VALID_CARD_LIST).build();
        EditDeckCommand editCommand = new EditDeckCommand(decksView, indexLastTopDeckDeck, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_DECK_SUCCESS, editedDeck);

        Model expectedModel = new ModelManager(model.getTopDeck(), new UserPrefs());
        expectedModel.updateDeck(lastDeck, editedDeck);
        expectedModel.commitTopDeck();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditDeckCommand editCommand = new EditDeckCommand(decksView, INDEX_FIRST_DECK,
                                                          new EditDeckCommand.EditDeckDescriptor());
        Deck editedDeck = decksView.getFilteredList().get(INDEX_FIRST_DECK.getZeroBased());

        String expectedMessage = String.format(MESSAGE_EDIT_DECK_SUCCESS, editedDeck);

        ModelManager expectedModel = new ModelManager(model.getTopDeck(), new UserPrefs());
        expectedModel.commitTopDeck();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showDeckAtIndex(model, INDEX_FIRST_DECK);

        Deck deckInFilteredList = decksView.getFilteredList().get(INDEX_FIRST_DECK.getZeroBased());
        Deck editedDeck = new DeckBuilder(deckInFilteredList).withName(VALID_NAME_JOHN).build();
        EditDeckCommand editCommand = new EditDeckCommand(decksView, INDEX_FIRST_DECK,
                                                          new EditDeckDescriptorBuilder()
                                                                  .withName(VALID_NAME_JOHN).build());

        String expectedMessage = String.format(MESSAGE_EDIT_DECK_SUCCESS, editedDeck);

        ModelManager expectedModel = new ModelManager(model.getTopDeck(), new UserPrefs());
        expectedModel.updateDeck(decksView.getFilteredList().get(0), editedDeck);
        expectedModel.commitTopDeck();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTopDeckDeckUnfilteredList_failure() {
        Deck firstDeck = decksView.getFilteredList().get(INDEX_FIRST_DECK.getZeroBased());
        EditDeckCommand.EditDeckDescriptor descriptor = new EditDeckDescriptorBuilder(firstDeck).build();
        EditDeckCommand editCommand = new EditDeckCommand(decksView, INDEX_SECOND_DECK, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, MESSAGE_DUPLICATE_DECK);
    }

    @Test
    public void execute_duplicateTopDeckDeckFilteredList_failure() {
        showDeckAtIndex(model, INDEX_FIRST_DECK);

        // edit deck in filtered list into a duplicate in TopDeck
        Deck deckInList = model.getTopDeck().getDeckList().get(INDEX_SECOND_DECK.getZeroBased());
        EditDeckCommand editCommand = new EditDeckCommand(decksView, INDEX_FIRST_DECK,
                                                          new EditDeckDescriptorBuilder(deckInList).build());

        assertCommandFailure(editCommand, model, commandHistory, MESSAGE_DUPLICATE_DECK);
    }

    @Test
    public void execute_invalidTopDeckDeckIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(decksView.getFilteredList().size() + 1);
        EditDeckCommand.EditDeckDescriptor descriptor = new EditDeckDescriptorBuilder()
                .withName(VALID_NAME_JOHN).build();
        EditDeckCommand editCommand = new EditDeckCommand(decksView, outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory,
                             Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of TopDeck
     */
    @Test
    public void execute_invalidTopDeckDeckIndexFilteredList_failure() {
        showDeckAtIndex(model, INDEX_FIRST_DECK);
        Index outOfBoundIndex = INDEX_SECOND_DECK;
        // ensures that outOfBoundIndex is still in bounds of TopDeck list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTopDeck().getDeckList().size());

        EditDeckCommand editCommand = new EditDeckCommand(decksView, outOfBoundIndex,
                                                          new EditDeckDescriptorBuilder()
                                                                  .withName(VALID_NAME_JOHN).build());

        assertCommandFailure(editCommand, model, commandHistory,
                             Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Deck editedDeck = new DeckBuilder().build();
        Deck deckToEdit = decksView.getFilteredList().get(INDEX_FIRST_DECK.getZeroBased());
        EditDeckCommand.EditDeckDescriptor descriptor = new EditDeckDescriptorBuilder(editedDeck).build();
        EditDeckCommand editCommand = new EditDeckCommand(decksView, INDEX_FIRST_DECK, descriptor);
        ModelManager expectedModel = new ModelManager(model.getTopDeck(), new UserPrefs());
        expectedModel.updateDeck(deckToEdit, editedDeck);
        expectedModel.commitTopDeck();

        // edit -> first deck edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts TopDeck back to previous state and filtered deck list to show all decks
        expectedModel.undoTopDeck();
        assertUpdateCommandSuccess(new UndoCommand(decksView), model, commandHistory,
                                   UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first deck edited again
        expectedModel.redoTopDeck();
        assertUpdateCommandSuccess(new RedoCommand(decksView), model, commandHistory,
                                   RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(decksView.getFilteredList().size() + 1);
        EditDeckCommand.EditDeckDescriptor descriptor = new EditDeckDescriptorBuilder()
                .withName(VALID_NAME_JOHN).build();
        EditDeckCommand editCommand = new EditDeckCommand(decksView, outOfBoundIndex, descriptor);

        // execution failed -> TopDeck state not added into model
        assertCommandFailure(editCommand, model, commandHistory,
                             Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);

        // single TopDeck state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(decksView), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(decksView), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Deck} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited deck in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the deck object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameTopDeckDeckEdited() throws Exception {
        Deck editedDeck = new DeckBuilder().build();
        EditDeckCommand.EditDeckDescriptor descriptor = new EditDeckDescriptorBuilder(editedDeck).build();
        EditDeckCommand editCommand = new EditDeckCommand(decksView, INDEX_FIRST_DECK, descriptor);
        ModelManager expectedModel = new ModelManager(model.getTopDeck(), new UserPrefs());

        showDeckAtIndex(model, INDEX_SECOND_DECK);
        Deck deckToEdit = decksView.getFilteredList().get(INDEX_FIRST_DECK.getZeroBased());
        expectedModel.updateDeck(deckToEdit, editedDeck);
        expectedModel.commitTopDeck();

        // edit -> edits second deck in unfiltered deck list / first deck in filtered deck list
        editCommand.execute(model, commandHistory);

        // undo -> reverts TopDeck back to previous state and filtered deck list to show all decks
        expectedModel.undoTopDeck();
        assertUpdateCommandSuccess(new UndoCommand(decksView), model, commandHistory,
                                   UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(decksView.getFilteredList().get(INDEX_FIRST_DECK.getZeroBased()), deckToEdit);
        // redo -> edits same second deck in unfiltered deck list
        expectedModel.redoTopDeck();
        assertUpdateCommandSuccess(new RedoCommand(decksView), model, commandHistory,
                                   RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditDeckCommand standardCommand = new EditDeckCommand(decksView, INDEX_FIRST_DECK, DESC_A);

        // same values -> returns true
        EditDeckCommand.EditDeckDescriptor copyDescriptor = new EditDeckCommand.EditDeckDescriptor(DESC_A);
        EditDeckCommand commandWithSameValues = new EditDeckCommand(decksView, INDEX_FIRST_DECK,
                                                                    copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditDeckCommand(decksView, INDEX_SECOND_DECK, DESC_A)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditDeckCommand(decksView, INDEX_FIRST_DECK, DESC_B)));
    }

}
