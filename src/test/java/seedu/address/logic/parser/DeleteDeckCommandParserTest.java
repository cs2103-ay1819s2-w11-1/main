package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalDecks.getTypicalTopDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;

import org.junit.Test;

import seedu.address.logic.DecksView;
import seedu.address.logic.commands.DeleteDeckCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class DeleteDeckCommandParserTest {

    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private DeleteDeckCommandParser parser = new DeleteDeckCommandParser((DecksView) model.getViewState());

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1",
                           new DeleteDeckCommand((DecksView) model.getViewState(), INDEX_FIRST_DECK));
    }

    @Test
    public void parse_blankArgs_throwsParseException() {
        assertParseFailure(parser, "",
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDeckCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDeckCommand.MESSAGE_USAGE));
    }
}
