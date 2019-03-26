package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_MOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_MOD;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCardAtIndex;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TopDeck;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Card;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.EditCardDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditCardCommand.
 */
public class EditCardCommandTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void initialize() {
        model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
        model.changeDeck(getTypicalDeck());
        assertTrue(model.isAtCardsView());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Card editedCard = new CardBuilder().build();
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCardCommand editCommand = new EditCardCommand(INDEX_FIRST_CARD, descriptor);

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(model.getTopDeck(), new UserPrefs());
        expectedModel.changeDeck(getTypicalDeck());

        assertTrue(expectedModel.isAtCardsView());
        Card currentCard = (Card) expectedModel.getFilteredList().get(INDEX_FIRST_CARD.getZeroBased());

        expectedModel.setCard(currentCard, editedCard);
        expectedModel.commitTopDeck();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCard = Index.fromOneBased(model.getFilteredList().size());
        Card lastCard = (Card) model.getFilteredList().get(indexLastCard.getZeroBased());

        CardBuilder cardInList = new CardBuilder(lastCard);
        Card editedCard = cardInList.withQuestion(VALID_QUESTION_MOD).withAnswer(VALID_ANSWER_MOD).build();

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_MOD)
                                                                                       .withAnswer(VALID_ANSWER_MOD)
                                                                                       .build();
        EditCardCommand editCommand = new EditCardCommand(indexLastCard, descriptor);

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(model.getTopDeck(), new UserPrefs());
        expectedModel.changeDeck(getTypicalDeck());
        expectedModel.setCard(lastCard, editedCard);
        expectedModel.commitTopDeck();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCardCommand editCommand = new EditCardCommand(INDEX_FIRST_CARD);
        Card firstCard = (Card) model.getFilteredList().get(INDEX_FIRST_CARD.getZeroBased());
        final StringBuilder builder = new StringBuilder();
        for (Tag tag : firstCard.getTags()) {
            builder.append(" ").append(PREFIX_TAG).append(tag.tagName);
        }
        String prefilledText = String
                .format("%s %d %s%s %s%s %s", EditCardCommand.COMMAND_WORD, 1, PREFIX_QUESTION, firstCard.getQuestion(),
                        PREFIX_ANSWER, firstCard.getAnswer(), builder.toString());
        CommandResult expectedResult = new PrefillCommandBoxCommandResult(
                EditCardCommand.MESSAGE_EDIT_CARD_AUTOCOMPLETE, prefilledText);

        assertCommandSuccess(editCommand, model, commandHistory, expectedResult, model);
    }

    @Test
    public void execute_filteredList_success() {
        showCardAtIndex(model, INDEX_FIRST_CARD);

        Card cardInFilteredList = (Card) model.getFilteredList().get(INDEX_FIRST_CARD.getZeroBased());
        Card editedCard = new CardBuilder(cardInFilteredList).withQuestion(VALID_QUESTION_MOD).build();
        EditCardCommand editCommand = new EditCardCommand(INDEX_FIRST_CARD, new EditCardDescriptorBuilder()
                .withQuestion(VALID_QUESTION_MOD).build());

        String expectedMessage = String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new TopDeck(model.getTopDeck()), new UserPrefs());
        expectedModel.changeDeck(getTypicalDeck());
        expectedModel.setCard((Card) model.getFilteredList().get(INDEX_FIRST_CARD.getZeroBased()), editedCard);
        expectedModel.commitTopDeck();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCardUnfilteredList_failure() {
        Card firstCard = (Card) model.getFilteredList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder(firstCard).build();
        EditCardCommand editCommand = new EditCardCommand(INDEX_SECOND_CARD, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCardCommand.MESSAGE_DUPLICATE_CARD);
    }

    @Test
    public void execute_duplicateCardFilteredList_failure() {
        Card cardInList = (Card) model.getFilteredList().get(INDEX_SECOND_CARD.getZeroBased());

        showCardAtIndex(model, INDEX_FIRST_CARD);

        // edit card in filtered list into a duplicate card in typical deck
        EditCardCommand editCommand = new EditCardCommand(INDEX_FIRST_CARD,
                                                          new EditCardDescriptorBuilder(cardInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCardCommand.MESSAGE_DUPLICATE_CARD);
    }

    @Test
    public void execute_invalidCardIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredList().size() + 1);
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_MOD)
                                                                                       .build();
        EditCardCommand editCommand = new EditCardCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of the deck
     */
    @Test
    public void execute_invalidCardIndexFilteredList_failure() {
        showCardAtIndex(model, INDEX_FIRST_CARD);
        Index outOfBoundIndex = INDEX_SECOND_CARD;
        // ensures that outOfBoundIndex is still in bounds of deck list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTopDeck().getDeckList().get(0).getCards().internalList
                .size());

        EditCardCommand editCommand = new EditCardCommand(outOfBoundIndex, new EditCardDescriptorBuilder()
                .withQuestion(VALID_QUESTION_MOD).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Card editedCard = new CardBuilder().build();
        Card cardToEdit = (Card) model.getFilteredList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCardCommand editCommand = new EditCardCommand(INDEX_FIRST_CARD, descriptor);

        Model expectedModel = new ModelManager(new TopDeck(model.getTopDeck()), new UserPrefs());
        expectedModel.changeDeck(getTypicalDeck());
        expectedModel.setCard(cardToEdit, editedCard);
        expectedModel.commitTopDeck();

        // edit -> first card edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts topdeck back to previous state and filtered card list to show all persons
        expectedModel.undoTopDeck();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first card edited again
        expectedModel.redoTopDeck();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredList().size() + 1);
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_MOD)
                                                                                       .build();
        EditCardCommand editCommand = new EditCardCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Card} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited card in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the card object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameCardEdited() throws Exception {
        Card editedCard = new CardBuilder().build();
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCardCommand editCommand = new EditCardCommand(INDEX_FIRST_CARD, descriptor);

        Model expectedModel = new ModelManager(new TopDeck(model.getTopDeck()), new UserPrefs());
        showCardAtIndex(model, INDEX_SECOND_CARD);
        Card cardToEdit = (Card) model.getFilteredList().get(INDEX_FIRST_CARD.getZeroBased());
        expectedModel.changeDeck(getTypicalDeck());
        expectedModel.setCard(cardToEdit, editedCard);
        expectedModel.commitTopDeck();

        // edit -> edits second card in unfiltered card list / first card in filtered card list
        editCommand.execute(model, commandHistory);

        // undo -> reverts topdeck back to previous state and filtered card list to show all persons
        expectedModel.undoTopDeck();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredList().get(INDEX_FIRST_CARD.getZeroBased()), cardToEdit);
        // redo -> edits same second card in unfiltered card list
        expectedModel.redoTopDeck();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCardCommand standardCommand = new EditCardCommand(INDEX_FIRST_CARD, DESC_HELLO);

        // same values -> returns true
        EditCardCommand.EditCardDescriptor copyDescriptor = new EditCardCommand.EditCardDescriptor(DESC_HELLO);
        EditCardCommand commandWithSameValues = new EditCardCommand(INDEX_FIRST_CARD, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCardCommand(INDEX_SECOND_CARD, DESC_HELLO)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCardCommand(INDEX_FIRST_CARD, DESC_MOD)));
    }

}
