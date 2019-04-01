package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_CARDS_LISTED_OVERVIEW;

import seedu.address.model.Model;

public class FindCommandSystemTest extends TopDeckSystemTest {

    //    @Test
    //    public void find() {
    //        /* Case: find multiple cards in deck, command with leading spaces and trailing spaces
    //         * -> 2 cards found
    //         */
    //        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_HTTP + "   ";
    //        Model expectedModel = getModel();
    //        ModelHelper.setFilteredList(expectedModel, LAYER, TRANSPORT); // first names of Benson and
    //        Daniel are "Meier"
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: repeat previous find command where card list is displaying the cards we are finding
    //         * -> 2 cards found
    //         */
    //        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_HTTP;
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: find card where card list is not displaying the card we are finding -> 1 card found */
    //        command = FindCommand.COMMAND_WORD + " Hello?";
    //        ModelHelper.setFilteredList(expectedModel, HELLO_WORLD);
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: find multiple cards in deck, 2 keywords -> 2 cards found */
    //        command = FindCommand.COMMAND_WORD + " transport layer";
    //        ModelHelper.setFilteredList(expectedModel, TRANSPORT, LAYER);
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: find multiple cards in deck, 2 keywords in reversed order -> 2 cards found */
    //        command = FindCommand.COMMAND_WORD + " layer transport";
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: find multiple cards in deck, 2 keywords with 1 repeat -> 2 cards found */
    //        command = FindCommand.COMMAND_WORD + " transport layer transport";
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: find multiple cards in deck, 2 matching keywords and 1 non-matching keyword
    //         * -> 2 cards found
    //         */
    //        command = FindCommand.COMMAND_WORD + " transport layer NonMatchingKeyWord";
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: undo previous find command -> rejected */
    //        command = UndoCommand.COMMAND_WORD;
    //        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
    //        assertCommandFailure(command, expectedResultMessage);
    //
    //        /* Case: redo previous find command -> rejected */
    //        command = RedoCommand.COMMAND_WORD;
    //        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
    //        assertCommandFailure(command, expectedResultMessage);
    //
    //        /* Case: find same cards in deck after deleting 1 of them -> 1 card found */
    //        executeCommand(DeleteCardCommand.COMMAND_WORD + " 1");
    //        assertFalse(getModel().getTopDeck().getDeckList().contains(LAYER));
    //        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_HTTP;
    //        expectedModel = getModel();
    //        ModelHelper.setFilteredList(expectedModel, TRANSPORT);
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: find card in deck, keyword is same as name but of different case -> 1 card found */
    //        command = FindCommand.COMMAND_WORD + " HttP";
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: find card in deck, keyword is substring of name -> 0 cards found */
    //        command = FindCommand.COMMAND_WORD + " HT";
    //        ModelHelper.setFilteredList(expectedModel);
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: find card in deck, name is substring of keyword -> 0 cards found */
    //        command = FindCommand.COMMAND_WORD + " Transpo";
    //        ModelHelper.setFilteredList(expectedModel);
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: find card not in deck -> 0 cards found */
    //        command = FindCommand.COMMAND_WORD + " NotInBook";
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: find answer of card in deck -> 0 cards found */
    //        command = FindCommand.COMMAND_WORD + " " + TRANSPORT.getAnswer();
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: find tags of card in deck -> 0 cards found */
    //        List<Tag> tags = new ArrayList<>(TRANSPORT.getTags());
    //        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: find while a card is selected -> selected card deselected */
    //        showAllDecks();
    //        selectDeck(Index.fromOneBased(1));
    //        assertFalse(getCardListPanel().getHandleToSelectedDeck().getQuestion().equals(TRANSPORT
    //        .getQuestion()));
    //        command = FindCommand.COMMAND_WORD + " transport";
    //        ModelHelper.setFilteredList(expectedModel, TRANSPORT);
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckDeselected();
    //
    //        /* Case: find card in empty deck -> 0 cards found */
    //        deleteAllDecks();
    //        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_HTTP;
    //        expectedModel = getModel();
    //        ModelHelper.setFilteredList(expectedModel, TRANSPORT);
    //        assertCommandSuccess(command, expectedModel);
    //        assertSelectedDeckUnchanged();
    //
    //        /* Case: mixed case command word -> rejected */
    //        command = "FiNd HTTP";
    //        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    //    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the
     * filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style
     * class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String
                .format(MESSAGE_CARDS_LISTED_OVERVIEW, expectedModel.getFilteredList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
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
