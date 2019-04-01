package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertUpdateCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.extractActiveDeck;
import static seedu.address.logic.commands.CommandTestUtil.showCardAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.updateCardsView;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CardsView;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TopDeck;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCardCommand}. TopDeck should be in CardsView before any test
 */
public class DeleteCardCommandTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();
    private CardsView cardsView;

    @Before
    public void initialize() {
        model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
        model.changeDeck(getTypicalDeck());
        assertTrue(model.isAtCardsView());
        cardsView = (CardsView) model.getViewState();
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Card cardToDelete = cardsView.getFilteredList().get(INDEX_FIRST_CARD.getZeroBased());
        DeleteCardCommand deleteCommand = new DeleteCardCommand(cardsView, INDEX_FIRST_CARD);

        String expectedMessage = String.format(DeleteCardCommand.MESSAGE_DELETE_CARD_SUCCESS, cardToDelete);

        ModelManager expectedModel = new ModelManager(model.getTopDeck(), new UserPrefs());
        expectedModel.changeDeck(getTypicalDeck());

        Deck activeDeck = extractActiveDeck(expectedModel);
        expectedModel.deleteCard(cardToDelete, activeDeck);
        expectedModel.commitTopDeck();

        assertUpdateCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(cardsView.getFilteredList().size() + 1);
        DeleteCardCommand deleteCommand = new DeleteCardCommand(cardsView, outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCardAtIndex(model, INDEX_FIRST_CARD);

        Card cardToDelete = cardsView.getFilteredList().get(INDEX_FIRST_CARD.getZeroBased());
        DeleteCardCommand deleteCommand = new DeleteCardCommand(cardsView, INDEX_FIRST_CARD);

        String expectedMessage = String.format(DeleteCardCommand.MESSAGE_DELETE_CARD_SUCCESS, cardToDelete);

        Model expectedModel = new ModelManager(model.getTopDeck(), new UserPrefs());
        expectedModel.changeDeck(getTypicalDeck());
        Deck activeDeck = extractActiveDeck(expectedModel);
        expectedModel.deleteCard(cardToDelete, activeDeck);
        expectedModel.commitTopDeck();

        assertUpdateCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCardAtIndex(model, INDEX_FIRST_CARD);

        Index outOfBoundIndex = INDEX_SECOND_CARD;
        // ensures that outOfBoundIndex is still in bounds of cards list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTopDeck().getDeckList().get(0)
                                                         .getCards().internalList.size());

        DeleteCardCommand deleteCommand = new DeleteCardCommand(cardsView, outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Card cardToDelete = cardsView.getFilteredList().get(INDEX_FIRST_CARD.getZeroBased());
        DeleteCardCommand deleteCommand = new DeleteCardCommand(cardsView, INDEX_FIRST_CARD);

        Model expectedModel = new ModelManager(model.getTopDeck(), new UserPrefs());
        expectedModel.changeDeck(getTypicalDeck());
        Deck activeDeck = extractActiveDeck(expectedModel);
        expectedModel.deleteCard(cardToDelete, activeDeck);
        expectedModel.commitTopDeck();

        // delete -> first card deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts TopDeck back to previous state and filtered card list to show all persons
        expectedModel.undoTopDeck();
        updateCardsView(expectedModel);

        assertUpdateCommandSuccess(new UndoCommand(cardsView), model, commandHistory,
                                   UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first card deleted again
        expectedModel.redoTopDeck();
        updateCardsView(expectedModel);

        assertUpdateCommandSuccess(new RedoCommand(cardsView), model, commandHistory,
                                   RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(cardsView.getFilteredList().size() + 1);
        DeleteCardCommand deleteCommand = new DeleteCardCommand(cardsView, outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(cardsView), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(cardsView), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Card} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted card in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the card object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameCardDeleted() throws Exception {
        DeleteCardCommand deleteCommand = new DeleteCardCommand(cardsView, INDEX_FIRST_CARD);
        Model expectedModel = new ModelManager(new TopDeck(model.getTopDeck()), new UserPrefs());

        showCardAtIndex(model, INDEX_SECOND_CARD);
        Card cardToDelete = cardsView.getFilteredList().get(INDEX_FIRST_CARD.getZeroBased());

        expectedModel.changeDeck(getTypicalDeck());
        Deck activeDeck = extractActiveDeck(expectedModel);
        expectedModel.deleteCard(cardToDelete, activeDeck);
        expectedModel.commitTopDeck();

        // delete -> deletes second card in unfiltered card list / first card in filtered card list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts topdeck back to previous state and filtered card list to show all cards
        expectedModel.undoTopDeck();
        updateCardsView(expectedModel);
        assertUpdateCommandSuccess(new UndoCommand(cardsView), model, commandHistory,
                                   UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> deletes same second card in unfiltered card list
        expectedModel.redoTopDeck();
        updateCardsView(expectedModel);
        assertUpdateCommandSuccess(new RedoCommand(cardsView), model, commandHistory,
                                   RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCardCommand deleteFirstCommand = new DeleteCardCommand(cardsView, INDEX_FIRST_CARD);
        DeleteCardCommand deleteSecondCommand = new DeleteCardCommand(cardsView, INDEX_SECOND_CARD);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCardCommand deleteFirstCommandCopy = new DeleteCardCommand(cardsView, INDEX_FIRST_CARD);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different card -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoCard(Model model) {
        cardsView.updateFilteredList(p -> false);

        assertTrue(cardsView.getFilteredList().isEmpty());
    }
}
