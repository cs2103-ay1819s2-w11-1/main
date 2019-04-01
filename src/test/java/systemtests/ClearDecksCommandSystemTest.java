package systemtests;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ClearDecksCommandSystemTest extends TopDeckSystemTest {

    //    @Test
    //    public void clear() {
    //        final Model defaultModel = getModel();
    //
    //        /* Case: clear non-empty address book, command with leading spaces and trailing alphanumeric
    //        characters and
    //         * spaces -> cleared
    //         */
    //        assertCommandSuccess("   " + ClearCommand.COMMAND_WORD + " ab12   ");
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: undo clearing address book -> original address book restored */
    //        String command = UndoCommand.COMMAND_WORD;
    //        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
    //        assertCommandSuccess(command, expectedResultMessage, defaultModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: redo clearing address book -> cleared */
    //        command = RedoCommand.COMMAND_WORD;
    //        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
    //        assertCommandSuccess(command, expectedResultMessage, new ModelManager());
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: selects first card in card list and clears address book -> cleared and no card
    //        selected */
    //        executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
    //        selectDeck(Index.fromOneBased(1));
    //        assertCommandSuccess(ClearCommand.COMMAND_WORD);
    //        assertSelectedDeckDeselected();
    //
    //        /* Case: filters the card list before clearing -> entire address book cleared */
    //        executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
    //        showDecksWithQuestion(KEYWORD_MATCHING_HTTP);
    //        assertCommandSuccess(ClearCommand.COMMAND_WORD);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: clear empty address book -> cleared */
    //        assertCommandSuccess(ClearCommand.COMMAND_WORD);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: mixed case command word -> rejected */
    //        assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);
    //    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty
     * model.
     * These verifications are done by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status
     * changes.
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, ClearCommand.MESSAGE_SUCCESS, new ModelManager());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box
     * displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     *
     * @see ClearDecksCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command
     * box has the
     * error style.
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
