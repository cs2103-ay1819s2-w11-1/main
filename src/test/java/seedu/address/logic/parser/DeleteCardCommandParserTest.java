package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalDecks.getTypicalTopDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CardsView;
import seedu.address.logic.commands.DeleteCardCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCardCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCardCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCardCommandParserTest {

    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private DeleteCardCommandParser parser;

    @Before
    public void initialize() {
        model.changeDeck(getTypicalDeck());
        assertTrue(model.isAtCardsView());
        parser = new DeleteCardCommandParser((CardsView) model.getViewState());
    }

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1",
                           new DeleteCardCommand((CardsView) model.getViewState(), INDEX_FIRST_CARD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCardCommand.MESSAGE_USAGE));
    }
}
