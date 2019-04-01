package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_ADDITION;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_SUBTRACTION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_ADDITION;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_SUBTRACTION;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MOD;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SUBJECT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCards.ADDITION;
import static seedu.address.testutil.TypicalCards.HELLO_WORLD;
import static seedu.address.testutil.TypicalCards.SUBTRACTION;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalDecks.getTypicalTopDeck;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CardsView;
import seedu.address.logic.commands.AddCardCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Card;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CardBuilder;

public class AddCardCommandParserTest {
    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private AddCardCommandParser parser;

    @Before
    public void initialize() {
        model.changeDeck(getTypicalDeck());
        assertTrue(model.isAtCardsView());
        parser = new AddCardCommandParser((CardsView) model.getViewState());
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Card expectedCard = new CardBuilder(SUBTRACTION).withTags(VALID_TAG_MOD).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + QUESTION_DESC_SUBTRACTION + ANSWER_DESC_SUBTRACTION
                + TAG_DESC_MOD, new AddCardCommand((CardsView) model.getViewState(), expectedCard));

        // multiple names - last name accepted
        assertParseSuccess(parser, QUESTION_DESC_HELLO + QUESTION_DESC_SUBTRACTION + ANSWER_DESC_SUBTRACTION
                + TAG_DESC_MOD, new AddCardCommand((CardsView) model.getViewState(), expectedCard));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, QUESTION_DESC_SUBTRACTION + ANSWER_DESC_HELLO + ANSWER_DESC_SUBTRACTION
                + TAG_DESC_MOD, new AddCardCommand((CardsView) model.getViewState(), expectedCard));

        // multiple tags - all accepted
        Card expectedCardMultipleTags = new CardBuilder(HELLO_WORLD)
                .withTags(VALID_TAG_MOD, VALID_TAG_SUBJECT).build();
        assertParseSuccess(parser, QUESTION_DESC_HELLO + ANSWER_DESC_HELLO + TAG_DESC_MOD + TAG_DESC_SUBJECT,
                           new AddCardCommand((CardsView) model.getViewState(), expectedCardMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Card expectedCard = new CardBuilder(ADDITION).withTags().build();
        assertParseSuccess(parser, QUESTION_DESC_ADDITION + ANSWER_DESC_ADDITION,
                           new AddCardCommand((CardsView) model.getViewState(), expectedCard));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE);

        // missing question prefix
        assertParseFailure(parser, VALID_QUESTION_HELLO + ANSWER_DESC_HELLO, expectedMessage);

        // missing answer prefix
        assertParseFailure(parser, QUESTION_DESC_HELLO + VALID_ANSWER_HELLO, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_QUESTION_HELLO + VALID_ANSWER_HELLO, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid tag
        assertParseFailure(parser, QUESTION_DESC_HELLO + ANSWER_DESC_HELLO + INVALID_TAG_DESC + VALID_TAG_MOD,
                           Tag.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                           PREAMBLE_NON_EMPTY + QUESTION_DESC_HELLO + ANSWER_DESC_HELLO + TAG_DESC_SUBJECT
                                   + TAG_DESC_MOD,
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE));
    }
}
