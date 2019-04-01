package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CardsView;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.DecksView;
import seedu.address.logic.ListItem;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.TopDeck;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.DeckNameContainsKeywordsPredicate;
import seedu.address.model.deck.QuestionContainsKeywordsPredicate;
import seedu.address.testutil.DeckBuilder;
import seedu.address.testutil.EditCardDescriptorBuilder;
import seedu.address.testutil.EditDeckDescriptorBuilder;

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

    public static final String VALID_NAME = "My Deck";
    public static final String INVALID_NAME = "B@d_Deck_Name";
    public static final String VALID_NAME_JOHN = "John Phua";

    public static final String VALID_NAME_DECK_A = "My Deck A";
    public static final String VALID_NAME_DECK_B = "Your deck";
    public static final Deck VALID_DECK_A = new DeckBuilder().withName(VALID_NAME_DECK_A).build();
    public static final Deck VALID_DECK_B = new DeckBuilder().withName(VALID_NAME_DECK_B).build();

    public static final String VALID_DECK_NAME_A_ARGS = " " + PREFIX_NAME + VALID_NAME_DECK_A;
    public static final String VALID_DECK_NAME_B_ARGS = " " + PREFIX_NAME + VALID_NAME_DECK_B;
    public static final String INVALID_DECK_NAME_ARGS = " " + PREFIX_NAME + " Bad_Deck_Name!";

    public static final List<Card> VALID_CARD_LIST = new ArrayList<>();

    public static final EditCardCommand.EditCardDescriptor DESC_HELLO;
    public static final EditCardCommand.EditCardDescriptor DESC_MOD;
    public static final EditCardCommand.EditCardDescriptor DESC_ADDITION;
    public static final EditDeckCommand.EditDeckDescriptor DESC_A;
    public static final EditDeckCommand.EditDeckDescriptor DESC_B;

    static {
        DESC_HELLO = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_HELLO)
                                                    .withAnswer(VALID_ANSWER_HELLO).withTags(VALID_TAG_MOD)
                                                    .build();
        DESC_MOD = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_MOD)
                                                  .withAnswer(VALID_ANSWER_MOD)
                                                  .withTags(VALID_TAG_MOD, VALID_TAG_SUBJECT).build();
        DESC_ADDITION = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_ADDITION)
                                                       .withAnswer(VALID_ANSWER_ADDITION)
                                                       .withTags(VALID_TAG_MATH).build();
    }

    static {
        DESC_A = new EditDeckDescriptorBuilder().withName(VALID_NAME_DECK_A).withCards(VALID_CARD_LIST)
                                                .build();
        DESC_B = new EditDeckDescriptorBuilder().withName(VALID_NAME_DECK_B).withCards(VALID_CARD_LIST)
                                                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
                                            CommandHistory actualCommandHistory,
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
     * Convenience wrapper to
     * {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage} and checks for {@code CommandResult}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
                                            CommandHistory actualCommandHistory, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult,
                             expectedModel);
    }

    /**
     * Convenience wrapper to
     * {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage} and checks for {@code UpdatePanelCommandResult}.
     */
    public static void assertUpdateCommandSuccess(Command command, Model actualModel,
                                                  CommandHistory actualCommandHistory, String expectedMessage,
                                                  Model expectedModel) {
        CommandResult expectedCommandResult = new UpdatePanelCommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult,
                             expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the topdeck, filtered card list and selected card in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    // TODO: update this
    public static void assertCommandFailure(Command command, Model actualModel,
                                            CommandHistory actualCommandHistory, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TopDeck expectedTopDeck = new TopDeck(actualModel.getTopDeck());
        List<ListItem> expectedFilteredList = new ArrayList<>(actualModel.getFilteredList());
        ListItem expectedSelectedItem = actualModel.getSelectedItem();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedTopDeck, actualModel.getTopDeck());
            assertEquals(expectedFilteredList, actualModel.getFilteredList());
            assertEquals(expectedSelectedItem, actualModel.getSelectedItem());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the deck at the given {@code targetIndex} in
     * {@code model}'s Topdeck.
     */

    public static void showDeckAtIndex(Model model, Index targetIndex) {
        assertTrue(model.isAtDecksView());
        DecksView decksView = (DecksView) model.getViewState();
        assertTrue(targetIndex.getZeroBased() < decksView.getFilteredList().size());

        Deck deck = decksView.getFilteredList().get(targetIndex.getZeroBased());

        final String[] splitName = deck.getName().fullName.split("\\s+");
        decksView.updateFilteredList(new DeckNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, decksView.getFilteredList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the card at the given {@code targetIndex} in the
     * {@code model}'s deck.
     */
    public static void showCardAtIndex(Model model, Index targetIndex) {
        assertTrue(model.isAtCardsView());
        CardsView cardsView = (CardsView) model.getViewState();
        assertTrue(targetIndex.getZeroBased() < cardsView.getFilteredList().size());

        Card card = cardsView.getFilteredList().get(targetIndex.getZeroBased());
        final String question = card.getQuestion().replace("?", "").replace(".", "");
        cardsView.updateFilteredList(new QuestionContainsKeywordsPredicate(Arrays.asList(question)));

        //Gets all the question that starts with what
        assertEquals(1, cardsView.getFilteredList().size());
    }

    /**
     * Deletes the first deck in {@code model}'s filtered list from {@code model}'s deck.
     */
    public static void deleteFirstDeck(Model model) {
        assertTrue(model.isAtDecksView());
        DecksView decksView = (DecksView) model.getViewState();
        Deck firstDeck = decksView.getFilteredList().get(INDEX_FIRST_DECK.getZeroBased());
        model.deleteDeck(firstDeck);
        model.commitTopDeck();
    }

    /**
     * Updates the given {@code model}'s CardsView.
     */
    public static void updateCardsView(Model model) {
        assertTrue(model.isAtCardsView());

        CardsView prevModelCardsView = (CardsView) model.getViewState();
        Deck newDeck = model.getDeck(prevModelCardsView.getActiveDeck());
        model.changeDeck(newDeck);
    }

    /**
     * Returns the current active deck in {@code model}'s CardsView.
     * The model must be in CardsView.
     */
    public static Deck extractActiveDeck(Model model) {
        CardsView cardsView = (CardsView) model.getViewState();
        Deck activeDeck = cardsView.getActiveDeck();

        return activeDeck;
    }
}
