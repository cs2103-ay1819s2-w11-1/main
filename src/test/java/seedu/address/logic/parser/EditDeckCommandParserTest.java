package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_DECK_NOT_EDITED;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DECK_NAME_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DECK_NAME_A_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DECK_NAME_B_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DECK_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DECK_B;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalDecks.getTypicalTopDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.DecksView;
import seedu.address.logic.commands.EditDeckCommand;
import seedu.address.logic.commands.EditDeckCommand.EditDeckDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Name;
import seedu.address.testutil.EditDeckDescriptorBuilder;

public class EditDeckCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String
            .format(MESSAGE_INVALID_COMMAND_FORMAT, EditDeckCommand.MESSAGE_USAGE);

    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private EditDeckCommandParser parser = new EditDeckCommandParser((DecksView) model.getViewState());

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_DECK_A, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_DECK_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_DECK_NAME_A_ARGS, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_DECK_NAME_A_ARGS, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DECK_NAME_ARGS, Name.MESSAGE_NAME); // invalid name
    }

    @Test
    public void parse_repeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_DECK;
        String userInput = targetIndex.getOneBased() + VALID_DECK_NAME_A_ARGS + VALID_DECK_NAME_B_ARGS;
        EditDeckDescriptor descriptor = new EditDeckDescriptorBuilder().withName(VALID_NAME_DECK_B).build();
        EditDeckCommand expectedCommand = new EditDeckCommand((DecksView) model.getViewState(), targetIndex,
                                                              descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_DECK;
        String userInput = targetIndex.getOneBased() + INVALID_DECK_NAME_ARGS + VALID_DECK_NAME_B_ARGS;
        EditDeckDescriptor descriptor = new EditDeckDescriptorBuilder().withName(VALID_NAME_DECK_B).build();
        EditDeckCommand expectedCommand = new EditDeckCommand((DecksView) model.getViewState(), targetIndex,
                                                              descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}


