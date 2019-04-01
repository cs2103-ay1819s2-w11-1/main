package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;

import org.junit.Test;

import seedu.address.logic.DecksView;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SelectDeckCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Test scope: similar to {@code DeleteCardCommandParserTest}.
 *
 * @see DeleteCardCommandParserTest
 */
public class SelectCommandParserTest {

    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private SelectCommandParser parser = new SelectCommandParser(model.getViewState());

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1",
                           new SelectDeckCommand((DecksView) model.getViewState(), INDEX_FIRST_CARD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}
