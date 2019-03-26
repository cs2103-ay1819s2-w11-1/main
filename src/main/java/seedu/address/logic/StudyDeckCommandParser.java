package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.OpenDeckCommand;
import seedu.address.logic.commands.StudyDeckCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser creates a new StudyDeckCommand Object.
 */
public class StudyDeckCommandParser {

    private DecksView viewState;

    StudyDeckCommandParser(DecksView viewState) {
        this.viewState = viewState;
    }

    public StudyDeckCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new StudyDeckCommand(index, viewState);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenDeckCommand.MESSAGE_USAGE), pe);
        }
    }
}
