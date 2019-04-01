package systemtests;

import static seedu.address.logic.commands.DeleteCardCommand.MESSAGE_DELETE_CARD_SUCCESS;
import static seedu.address.testutil.TestUtil.getDeck;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCardCommand;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;

public class DeleteDeckCommandSystemTest extends TopDeckSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT = String
            .format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCardCommand.MESSAGE_USAGE);

    //    @Test
    //    public void delete() {
    //        /* ----------------- Performing delete operation while an unfiltered list is being shown
    //        -------------------- */
    //
    //        /* Case: delete the first card in the list, command with leading spaces and trailing spaces
    //        -> deleted */
    //        Model expectedModel = getModel();
    //        String command = "     " + DeleteCardCommand.COMMAND_WORD + "      " + INDEX_FIRST_CARD
    //        .getOneBased() + "       ";
    //        Card deletedCard = removeCard(expectedModel, INDEX_FIRST_CARD);
    //        String expectedResultMessage = String.format(MESSAGE_DELETE_CARD_SUCCESS, deletedCard);
    //        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    //
    //        /* Case: delete the last card in the list -> deleted */
    //        Model modelBeforeDeletingLast = getModel();
    //        Index lastPersonIndex = getLastIndex(modelBeforeDeletingLast);
    //        assertCommandSuccess(lastPersonIndex);
    //
    //        /* Case: undo deleting the last card in the list -> last card restored */
    //        command = UndoCommand.COMMAND_WORD;
    //        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
    //        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);
    //
    //        /* Case: redo deleting the last card in the list -> last card deleted again */
    //        command = RedoCommand.COMMAND_WORD;
    //        removeCard(modelBeforeDeletingLast, lastPersonIndex);
    //        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
    //        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);
    //
    //        /* Case: delete the middle card in the list -> deleted */
    //        Index middlePersonIndex = getMidIndex(getModel());
    //        assertCommandSuccess(middlePersonIndex);
    //
    //        /* ------------------ Performing delete operation while a filtered list is being shown
    //        ---------------------- */
    //
    //        /* Case: filtered card list, delete index within bounds of address book and card list ->
    //        deleted */
    //        showDecksWithQuestion(KEYWORD_MATCHING_HTTP);
    //        Index index = INDEX_FIRST_CARD;
    //        assertTrue(index.getZeroBased() < getModel().getFilteredList().size());
    //        assertCommandSuccess(index);
    //
    //        /* Case: filtered card list, delete index within bounds of address book but out of bounds of
    //        card list
    //         * -> rejected
    //         */
    //        showDecksWithQuestion(KEYWORD_MATCHING_HTTP);
    //        int invalidIndex = getModel().getTopDeck().getDeckList().size();
    //        command = DeleteCardCommand.COMMAND_WORD + " " + invalidIndex;
    //        assertCommandFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);
    //
    //        /* --------------------- Performing delete operation while a card card is selected
    //        ------------------------ */
    //
    //        /* Case: delete the selected card -> card list panel selects the card before the deleted card */
    //        showAllDecks();
    //        expectedModel = getModel();
    //        Index selectedIndex = getLastIndex(expectedModel);
    //        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
    //        selectDeck(selectedIndex);
    //        command = DeleteCardCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
    //        deletedCard = removeCard(expectedModel, selectedIndex);
    //        expectedResultMessage = String.format(MESSAGE_DELETE_CARD_SUCCESS, deletedCard);
    //        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);
    //
    //        /* --------------------------------- Performing invalid delete operation
    //        ------------------------------------ */
    //
    //        /* Case: invalid index (0) -> rejected */
    //        command = DeleteCardCommand.COMMAND_WORD + " 0";
    //        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);
    //
    //        /* Case: invalid index (-1) -> rejected */
    //        command = DeleteCardCommand.COMMAND_WORD + " -1";
    //        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);
    //
    //        /* Case: invalid index (size + 1) -> rejected */
    //        Index outOfBoundsIndex = Index.fromOneBased(
    //                getModel().getTopDeck().getDeckList().size() + 1);
    //        command = DeleteCardCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
    //        assertCommandFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);
    //
    //        /* Case: invalid arguments (alphabets) -> rejected */
    //        assertCommandFailure(DeleteCardCommand.COMMAND_WORD + " abc",
    //        MESSAGE_INVALID_DELETE_COMMAND_FORMAT);
    //
    //        /* Case: invalid arguments (extra argument) -> rejected */
    //        assertCommandFailure(DeleteCardCommand.COMMAND_WORD + " 1 abc",
    //        MESSAGE_INVALID_DELETE_COMMAND_FORMAT);
    //
    //        /* Case: mixed case command word -> rejected */
    //        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    //    }

    /**
     * Removes the {@code Card} at the specified {@code index} in {@code model}'s deck.
     *
     * @return the removed card
     */
    private Deck removeDeck(Model model, Index index) {
        Deck targetDeck = getDeck(model, index);
        model.deleteDeck(targetDeck);
        return targetDeck;
    }

    /**
     * Deletes the card at {@code toDelete} by creating a default {@code DeleteCardCommand} using {@code
     * toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     *
     * @see DeleteDeckCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Deck deletedDeck = removeDeck(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_CARD_SUCCESS, deletedDeck);

        assertCommandSuccess(DeleteCardCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel,
                             expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that
     * the browser url
     * and selected card are expected to update accordingly depending on the card at {@code
     * expectedSelectedCardIndex}.
     *
     * @see DeleteDeckCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see TopDeckSystemTest#assertSelectedDeckChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedDeckChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedDeckUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedDeckUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
