package seedu.address.logic;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/*
 * Interface for storing states
 */
public interface ViewState {
    Command parse(String commandWord, String arguments) throws ParseException;
}
