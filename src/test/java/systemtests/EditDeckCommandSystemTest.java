package systemtests;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCardCommand;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;

public class EditDeckCommandSystemTest extends TopDeckSystemTest {

    //    @Test
    //    public void edit() {
    //        Model model = getModel();
    //
    //        /* ----------------- Performing edit operation while an unfiltered list is being shown
    //        ---------------------- */
    //
    //        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces
    //        between each field
    //         * -> edited
    //         */
    //        Index index = INDEX_FIRST_CARD;
    //        String command = " " + EditCardCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
    //            + QUESTION_DESC_ADDITION + "  " + ANSWER_DESC_ADDITION + " " + TAG_DESC_MATH + " ";
    //        Card editedCard = new CardBuilder(ADDITION).withTags(VALID_TAG_MATH).build();
    //        assertCommandSuccess(command, index, editedCard);
    //
    //        /* Case: undo editing the last card in the list -> last card restored */
    //        command = UndoCommand.COMMAND_WORD;
    //        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
    //        assertCommandSuccess(command, model, expectedResultMessage);
    //
    //        /* Case: redo editing the last card in the list -> last card edited again */
    //        command = RedoCommand.COMMAND_WORD;
    //        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
    //        model.setCard(getModel().getFilteredList().get(INDEX_FIRST_CARD.getZeroBased()), editedCard);
    //        assertCommandSuccess(command, model, expectedResultMessage);
    //
    //        /* Case: edit a card with new values same as existing values -> edited */
    //        command = EditCardCommand.COMMAND_WORD + " " + index.getOneBased() + QUESTION_DESC_ADDITION +
    //        ANSWER_DESC_ADDITION
    //                + TAG_DESC_MATH;
    //        assertCommandSuccess(command, index, ADDITION);
    //
    //        /* Case: edit a card with new answer same as another card's answer but with different
    //        question -> edited */
    //        assertTrue(getModel().getTopDeck().getDeckList().contains(ADDITION));
    //        index = INDEX_SECOND_CARD;
    //        assertNotEquals(getModel().getFilteredList().get(index.getZeroBased()), ADDITION);
    //        command = EditCardCommand.COMMAND_WORD + " " + index.getOneBased() + QUESTION_DESC_SUBTRACTION
    //            + ANSWER_DESC_ADDITION + TAG_DESC_MATH;
    //        editedCard = new CardBuilder(ADDITION).withQuestion(VALID_QUESTION_SUBTRACTION).build();
    //        assertCommandSuccess(command, index, editedCard);
    //
    //        /* Case: clear tags -> cleared */
    //        index = INDEX_FIRST_CARD;
    //        command = EditCardCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG
    //        .getPrefix();
    //        Card cardToEdit = getModel().getFilteredList().get(index.getZeroBased());
    //        editedCard = new CardBuilder(cardToEdit).withTags().build();
    //        assertCommandSuccess(command, index, editedCard);
    //
    //        /* ------------------ Performing edit operation while a filtered list is being shown
    //        ------------------------ */
    //
    //        /* Case: filtered card list, edit index within bounds of the deck and card list -> edited */
    //        showDecksWithQuestion(KEYWORD_MATCHING_HTTP);
    //        index = INDEX_FIRST_CARD;
    //        assertTrue(index.getZeroBased() < getModel().getFilteredList().size());
    //        command = EditCardCommand.COMMAND_WORD + " " + index.getOneBased() + " " + QUESTION_DESC_UNIQUE;
    //        cardToEdit = getModel().getFilteredList().get(index.getZeroBased());
    //        editedCard = new CardBuilder(cardToEdit).withQuestion(VALID_QUESTION_UNIQUE).build();
    //        assertCommandSuccess(command, index, editedCard);
    //
    //        /* Case: filtered card list, edit index within bounds of the deck but out of bounds of card list
    //         * -> rejected
    //         */
    //        showDecksWithQuestion(KEYWORD_MATCHING_HTTP);
    //        int invalidIndex = getModel().getTopDeck().getCardList().size();
    //        assertCommandFailure(EditCardCommand.COMMAND_WORD + " " + invalidIndex + QUESTION_DESC_HELLO,
    //                Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    //
    //        /* --------------------- Performing edit operation while a card card is selected
    //        -------------------------- */
    //
    //        /* Case: selects first card in the card list, edit a card -> edited, card selection remains
    //        unchanged but
    //         * browser url changes
    //         */
    //        showAllDecks();
    //        index = INDEX_FIRST_CARD;
    //        selectDeck(index);
    //        command = EditCardCommand.COMMAND_WORD + " " + index.getOneBased() + QUESTION_DESC_ADDITION +
    //        ANSWER_DESC_ADDITION
    //                + TAG_DESC_MATH;
    //        // this can be misleading: card selection actually remains unchanged but the
    //        // browser's url is updated to reflect the new card's name
    //        assertCommandSuccess(command, index, ADDITION, index);
    //
    //        /* --------------------------------- Performing invalid edit operation
    //        -------------------------------------- */
    //
    //        /* Case: invalid index (0) -> rejected */
    //        assertCommandFailure(EditCardCommand.COMMAND_WORD + " 0" + QUESTION_DESC_SUBTRACTION,
    //                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE));
    //
    //        /* Case: invalid index (-1) -> rejected */
    //        assertCommandFailure(EditCardCommand.COMMAND_WORD + " -1" + QUESTION_DESC_SUBTRACTION,
    //                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE));
    //
    //        /* Case: invalid index (size + 1) -> rejected */
    //        invalidIndex = getModel().getFilteredList().size() + 1;
    //        assertCommandFailure(EditCardCommand.COMMAND_WORD + " " + invalidIndex +
    //        QUESTION_DESC_SUBTRACTION,
    //                Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    //
    //        /* Case: missing index -> rejected */
    //        assertCommandFailure(EditCardCommand.COMMAND_WORD + QUESTION_DESC_SUBTRACTION,
    //                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE));
    //
    //        /* Case: invalid tag -> rejected */
    //        assertCommandFailure(EditCardCommand.COMMAND_WORD + " " + INDEX_FIRST_CARD.getOneBased() +
    //        INVALID_TAG_DESC,
    //                Tag.MESSAGE_CONSTRAINTS);
    //
    //        /* Case: edit a card with new values same as another card's values -> rejected */
    //        executeCommand(CardUtil.getAddCommand(ADDITION));
    //        assertTrue(getModel().getTopDeck().getCardList().contains(ADDITION));
    //        index = INDEX_FIRST_CARD;
    //        assertFalse(getModel().getFilteredList().get(index.getZeroBased()).equals(HELLO_WORLD));
    //        command = EditCardCommand.COMMAND_WORD + " " + index.getOneBased() + QUESTION_DESC_HELLO +
    //        ANSWER_DESC_HELLO
    //                + TAG_DESC_SUBJECT + TAG_DESC_SIMPLE;
    //        assertCommandFailure(command, EditCardCommand.MESSAGE_DUPLICATE_CARD);
    //
    //        /* Case: edit a card with new values same as another card's values but with different tags ->
    //        rejected */
    //        command = EditCardCommand.COMMAND_WORD + " " + index.getOneBased() + QUESTION_DESC_HELLO +
    //        ANSWER_DESC_HELLO
    //                + TAG_DESC_SUBJECT;
    //        assertCommandFailure(command, EditCardCommand.MESSAGE_DUPLICATE_CARD);
    //
    //        /* Case: edit a card with new values same as another card's question but with different
    //        answer -> rejected */
    //        command = EditCardCommand.COMMAND_WORD + " " + index.getOneBased() + QUESTION_DESC_HELLO +
    //        ANSWER_DESC_SUBTRACTION
    //                + TAG_DESC_SUBJECT + TAG_DESC_SIMPLE;
    //        assertCommandFailure(command, EditCardCommand.MESSAGE_DUPLICATE_CARD);
    //
    //    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Person, Index)} except
     * that
     * the browser url and selected card remain unchanged.
     *
     * @param toEdit the index of the current model's filtered list
     * @see EditDeckCommandSystemTest#assertCommandSuccess(String, Index, Card, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Card editedCard) {
        assertCommandSuccess(command, toEdit, editedCard, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in
     * addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCardCommand}
     * .<br>
     * 2. Asserts that the model related components are updated to reflect the card at index {@code toEdit}
     * being
     * updated to values specified {@code editedCard}.<br>
     *
     * @param toEdit the index of the current model's filtered list.
     * @see EditDeckCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Card editedCard,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        //expectedModel.setCard(expectedModel.getFilteredList().get(toEdit.getZeroBased()), editedCard);
        expectedModel.updateFilteredList(PREDICATE_SHOW_ALL_CARDS);

        assertCommandSuccess(command, expectedModel,
                             String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard),
                             expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except
     * that the
     * browser url and selected card remain unchanged.
     *
     * @see EditDeckCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see TopDeckSystemTest#assertSelectedDeckChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredList(PREDICATE_SHOW_ALL_CARDS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedDeckChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedDeckUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
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
