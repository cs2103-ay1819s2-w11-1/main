package seedu.address.logic;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Stores the state of TopDeck.
 */
public interface ViewState {
    Command parse(String commandWord, String arguments) throws ParseException;
}
