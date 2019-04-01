package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_ADDITION;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_SUBTRACTION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_ADDITION;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MOD;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_ADDITION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_ADDITION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalDecks.getTypicalTopDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CARD;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CardsView;
import seedu.address.logic.commands.EditCardCommand;
import seedu.address.logic.commands.EditCardCommand.EditCardDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditCardDescriptorBuilder;

public class EditCardCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT = String
            .format(MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE);

    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private EditCardCommandParser parser;

    @Before
    public void initialize() {
        model.changeDeck(getTypicalDeck());
        assertTrue(model.isAtCardsView());
        parser = new EditCardCommandParser((CardsView) model.getViewState());
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_QUESTION_ADDITION, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + QUESTION_DESC_ADDITION, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + QUESTION_DESC_ADDITION, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_MOD + TAG_DESC_SUBJECT + TAG_EMPTY,
                           Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_MOD + TAG_EMPTY + TAG_DESC_SUBJECT,
                           Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_MOD + TAG_DESC_SUBJECT,
                           Tag.MESSAGE_CONSTRAINTS);

    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_CARD;
        String userInput =
                targetIndex.getOneBased() + QUESTION_DESC_HELLO + ANSWER_DESC_HELLO + TAG_DESC_SUBJECT
                        + TAG_DESC_MOD;

        EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_HELLO)
                                                                       .withAnswer(VALID_ANSWER_HELLO)
                                                                       .withTags(VALID_TAG_SUBJECT,
                                                                                 VALID_TAG_MOD).build();
        EditCardCommand expectedCommand = new EditCardCommand((CardsView) model.getViewState(), targetIndex,
                                                              descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_CARD;
        String userInput = targetIndex.getOneBased() + ANSWER_DESC_HELLO;

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withAnswer(VALID_ANSWER_HELLO).build();
        EditCardCommand expectedCommand = new EditCardCommand((CardsView) model.getViewState(), targetIndex,
                                                              descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_CARD;
        String userInput = Integer.toString(targetIndex.getOneBased());

        EditCardCommand expectedCommand = new EditCardCommand((CardsView) model.getViewState(), targetIndex);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // question
        Index targetIndex = INDEX_THIRD_CARD;
        String userInput = targetIndex.getOneBased() + QUESTION_DESC_ADDITION;
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withQuestion(VALID_QUESTION_ADDITION).build();
        EditCardCommand expectedCommand = new EditCardCommand((CardsView) model.getViewState(), targetIndex,
                                                              descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // answer
        userInput = targetIndex.getOneBased() + ANSWER_DESC_ADDITION;
        descriptor = new EditCardDescriptorBuilder().withAnswer(VALID_ANSWER_ADDITION).build();
        expectedCommand = new EditCardCommand((CardsView) model.getViewState(), targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_MOD;
        descriptor = new EditCardDescriptorBuilder().withTags(VALID_TAG_MOD).build();
        expectedCommand = new EditCardCommand((CardsView) model.getViewState(), targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_CARD;
        String userInput =
                targetIndex.getOneBased() + ANSWER_DESC_ADDITION + ANSWER_DESC_SUBTRACTION + ANSWER_DESC_HELLO
                        + TAG_DESC_MOD + TAG_DESC_SUBJECT;

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withAnswer(VALID_ANSWER_HELLO).withTags(VALID_TAG_MOD, VALID_TAG_SUBJECT).build();
        EditCardCommand expectedCommand = new EditCardCommand((CardsView) model.getViewState(), targetIndex,
                                                              descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_CARD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withTags().build();
        EditCardCommand expectedCommand = new EditCardCommand((CardsView) model.getViewState(), targetIndex,
                                                              descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
