package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;

/**
 * Selects a card identified using it's displayed index.
 */
public abstract class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the card identified by the index number used in the displayed card list.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_SUCCESS = "Selected: %1$s";

    protected final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }
}
