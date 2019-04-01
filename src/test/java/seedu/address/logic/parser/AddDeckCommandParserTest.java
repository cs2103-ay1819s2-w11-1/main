package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DECK_NAME_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DECK_NAME_A_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DECK_A;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalDecks.getTypicalTopDeck;

import org.junit.Test;

import seedu.address.logic.DecksView;
import seedu.address.logic.commands.AddDeckCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.DeckBuilder;

public class AddDeckCommandParserTest {
    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private AddDeckCommandParser parser = new AddDeckCommandParser((DecksView) model.getViewState());

    @Test
    public void parse_allFieldsPresent_success() {
        Deck expectedDeck = new DeckBuilder().withName(VALID_NAME_DECK_A).build();

        // clean
        assertParseSuccess(parser, VALID_DECK_NAME_A_ARGS,
                           new AddDeckCommand((DecksView) model.getViewState(), expectedDeck));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_DECK_NAME_A_ARGS,
                           new AddDeckCommand((DecksView) model.getViewState(), expectedDeck));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDeckCommand.MESSAGE_USAGE);

        // No argument
        assertParseFailure(parser, "", expectedMessage);

        // Blank name
        assertParseFailure(parser, PREFIX_NAME + "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDeckCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "n/" + INVALID_DECK_NAME_ARGS, expectedMessage);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_NAME_DECK_A,
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDeckCommand.MESSAGE_USAGE));
    }
}
