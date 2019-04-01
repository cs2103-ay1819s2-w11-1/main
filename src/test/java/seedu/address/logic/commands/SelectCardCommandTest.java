package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCardAtIndex;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CARD;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CardsView;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCardCommandTest {
    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private CardsView cardsView;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void initialize() {
        model.changeDeck(getTypicalDeck());
        assertTrue(model.isAtCardsView());
        cardsView = (CardsView) model.getViewState();
        expectedModel.changeDeck(getTypicalDeck());
        assertTrue(expectedModel.isAtCardsView());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(cardsView.getFilteredList().size());

        assertExecutionSuccess(INDEX_FIRST_CARD);
        assertExecutionSuccess(INDEX_THIRD_CARD);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(cardsView.getFilteredList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCardAtIndex(model, INDEX_FIRST_CARD);
        showCardAtIndex(expectedModel, INDEX_FIRST_CARD);

        assertExecutionSuccess(INDEX_FIRST_CARD);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showCardAtIndex(model, INDEX_FIRST_CARD);
        showCardAtIndex(expectedModel, INDEX_FIRST_CARD);

        Index outOfBoundsIndex = INDEX_SECOND_CARD;
        // ensures that outOfBoundIndex is still in bounds of TopDeck list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getTopDeck().getDeckList().get(0)
                                                          .getCards().internalList.size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCardCommand(cardsView, INDEX_FIRST_CARD);
        SelectCommand selectSecondCommand = new SelectCardCommand(cardsView, INDEX_SECOND_CARD);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCardCommand(cardsView, INDEX_FIRST_CARD);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different card -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index},
     * and checks that the model's selected card is set to the card at {@code index} in the filtered card
     * list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCardCommand(cardsView, index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_SUCCESS, index.getOneBased());
        expectedModel.setSelectedItem(cardsView.getFilteredList().get(index.getZeroBased()));

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code
     * CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCardCommand(cardsView, index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
    }
}
