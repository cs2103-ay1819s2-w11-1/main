package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CardsView;
import seedu.address.logic.commands.FindCardCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.QuestionContainsKeywordsPredicate;

public class FindCardCommandParserTest {

    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private FindCardCommandParser parser;

    @Before
    public void initialize() {
        model.changeDeck(getTypicalDeck());
        assertTrue(model.isAtCardsView());
        parser = new FindCardCommandParser((CardsView) model.getViewState());
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCardCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCardCommand expectedFindCommand = new FindCardCommand((CardsView) model.getViewState(),
                                                                  new QuestionContainsKeywordsPredicate(
                                                                          Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

}
