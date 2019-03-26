package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_CARDS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.LAYER;
import static seedu.address.testutil.TypicalCards.OK_STATUS;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.QuestionContainsKeywordsPredicate;

public class FindCardCommandTest {
    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void initialise() {
        model.changeDeck(getTypicalDeck());
        assertTrue(model.isAtCardsView());
        expectedModel.changeDeck(getTypicalDeck());
        assertTrue(model.isAtCardsView());
        model.updateFilteredList(Model.PREDICATE_SHOW_ALL_CARDS);
        expectedModel.updateFilteredList(Model.PREDICATE_SHOW_ALL_CARDS);
    }

    @Test
    public void equals() {
        QuestionContainsKeywordsPredicate firstPredicate = new QuestionContainsKeywordsPredicate(
                Collections.singletonList("first"));
        QuestionContainsKeywordsPredicate secondPredicate = new QuestionContainsKeywordsPredicate(
                Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different card -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noCardFound() {
        String expectedMessage = String.format(MESSAGE_CARDS_LISTED_OVERVIEW, 0);
        QuestionContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCardCommand command = new FindCardCommand(predicate);
        expectedModel.updateFilteredList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredList());
    }

    @Test
    public void execute_multipleKeywords_multipleCardsFound() {
        String expectedMessage = String.format(MESSAGE_CARDS_LISTED_OVERVIEW, 2);
        QuestionContainsKeywordsPredicate predicate = preparePredicate("status layer");
        FindCardCommand command = new FindCardCommand(predicate);
        expectedModel.updateFilteredList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(LAYER, OK_STATUS), model.getFilteredList());
    }

    @Test
    public void execute_sentence_foundSpecificCard() {
        String expectedMessage = String.format(MESSAGE_CARDS_LISTED_OVERVIEW, 1);
        String questionString = LAYER.getQuestion().replace("?", "");
        QuestionContainsKeywordsPredicate predicate = prepareStringPredicate(questionString);
        FindCardCommand command = new FindCardCommand(predicate);
        expectedModel.updateFilteredList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(LAYER), model.getFilteredList());
    }

    /**
     * Parses {@code userInput} into a {@code QuestionContainsKeywordsPredicate}.
     */
    private QuestionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new QuestionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses a {@code sentence} into a {@code QuestionContainsKeywordsPredicate}.
     */
    private QuestionContainsKeywordsPredicate prepareStringPredicate(String userInput) {
        return new QuestionContainsKeywordsPredicate(Arrays.asList(userInput));
    }
}
