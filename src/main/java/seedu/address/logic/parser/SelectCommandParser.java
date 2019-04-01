package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CardsView;
import seedu.address.logic.DecksView;
import seedu.address.logic.ViewState;
import seedu.address.logic.commands.SelectCardCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SelectDeckCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    private ViewState viewState;

    public SelectCommandParser(ViewState viewState) {
        this.viewState = viewState;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            if (viewState instanceof CardsView) {
                return new SelectCardCommand((CardsView) viewState, index);
            } else {
                return new SelectDeckCommand((DecksView) viewState, index);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE), pe);
        }
    }
}
