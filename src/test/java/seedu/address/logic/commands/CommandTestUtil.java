package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.TopDeck;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditCardDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_QUESTION_HELLO = "Hello?";
    public static final String VALID_QUESTION_MOD = "What module is this?";
    public static final String VALID_QUESTION_ADDITION = "Solve 1 + 1.";
    public static final String VALID_QUESTION_SUBTRACTION = "What is 10 - 10?";
    public static final String VALID_QUESTION_UNIQUE = "Is this a unique question?";
    public static final String VALID_ANSWER_HELLO = "World";
    public static final String VALID_ANSWER_MOD = "CS2103T";
    public static final String VALID_ANSWER_ADDITION = "2";
    public static final String VALID_ANSWER_SUBTRACTION = "0";
    public static final String VALID_ANSWER_UNIQUE = "Yes it is unique";
    public static final String VALID_TAG_MOD = "CS2103T";
    public static final String VALID_TAG_SUBJECT = "CS";
    public static final String VALID_TAG_MATH = "Math";
    public static final String VALID_TAG_SIMPLE = "Simple";

    public static final String QUESTION_DESC_HELLO = " " + PREFIX_QUESTION + VALID_QUESTION_HELLO;
    public static final String QUESTION_DESC_MOD = " " + PREFIX_QUESTION + VALID_QUESTION_MOD;
    public static final String QUESTION_DESC_ADDITION = " " + PREFIX_QUESTION + VALID_QUESTION_ADDITION;
    public static final String QUESTION_DESC_SUBTRACTION = " " + PREFIX_QUESTION + VALID_QUESTION_SUBTRACTION;
    public static final String QUESTION_DESC_UNIQUE = " " + PREFIX_QUESTION + VALID_QUESTION_UNIQUE;
    public static final String ANSWER_DESC_HELLO = " " + PREFIX_ANSWER + VALID_ANSWER_HELLO;
    public static final String ANSWER_DESC_MOD = " " + PREFIX_ANSWER + VALID_ANSWER_MOD;
    public static final String ANSWER_DESC_ADDITION = " " + PREFIX_ANSWER + VALID_ANSWER_ADDITION;
    public static final String ANSWER_DESC_SUBTRACTION = " " + PREFIX_ANSWER + VALID_ANSWER_SUBTRACTION;
    public static final String ANSWER_DESC_UNIQUE = " " + PREFIX_ANSWER + VALID_ANSWER_UNIQUE;
    public static final String TAG_DESC_MOD = " " + PREFIX_TAG + VALID_TAG_MOD;
    public static final String TAG_DESC_SUBJECT = " " + PREFIX_TAG + VALID_TAG_SUBJECT;
    public static final String TAG_DESC_MATH = " " + PREFIX_TAG + VALID_TAG_MATH;
    public static final String TAG_DESC_SIMPLE = " " + PREFIX_TAG + VALID_TAG_SIMPLE;

    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditCardDescriptor DESC_HELLO;
    public static final EditCommand.EditCardDescriptor DESC_MOD;
    public static final EditCommand.EditCardDescriptor DESC_ADDITION;

    static {
        DESC_HELLO = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_HELLO)
                .withAnswer(VALID_ANSWER_HELLO).withTags(VALID_TAG_MOD).build();
        DESC_MOD = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_MOD)
                .withAnswer(VALID_ANSWER_MOD).withTags(VALID_TAG_MOD, VALID_TAG_SUBJECT).build();
        DESC_ADDITION = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_ADDITION)
                .withAnswer(VALID_ANSWER_ADDITION).withTags(VALID_TAG_MATH).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the topdeck, filtered card list and selected card in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TopDeck expectedTopDeck = new TopDeck(actualModel.getTopDeck());
        List<Card> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCardList());
        Card expectedSelectedCard = actualModel.getSelectedCard();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedTopDeck, actualModel.getTopDeck());
            assertEquals(expectedFilteredList, actualModel.getFilteredCardList());
            assertEquals(expectedSelectedCard, actualModel.getSelectedCard());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the card at the given {@code targetIndex} in the
     * {@code model}'s deck.
     */
    public static void showCardAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCardList().size());

        Card card = model.getFilteredCardList().get(targetIndex.getZeroBased());
        final String[] splitName = card.getQuestion().split("\\s+");
        model.updateFilteredCardList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        //Gets all the question that starts with what
        assertEquals(1, model.getFilteredCardList().size());
    }

    /**
     * Deletes the first card in {@code model}'s filtered list from {@code model}'s deck.
     */
    public static void deleteFirstCard(Model model) {
        Card firstCard = model.getFilteredCardList().get(0);
        model.deleteCard(firstCard);
        model.commitTopDeck();
    }

}
