package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Selects a deck identified using its displayed index.
 */
public class BackCommand extends Command {

    public static final String COMMAND_WORD = "back";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Close the deck and return to the main page.\n" + "Example: " + COMMAND_WORD
                    + " 1";

    private static final String MESSAGE_CLOSE_DECK_SUCCESS = "Closed deck";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.goToDecksView();

        return new UpdatePanelCommandResult(String.format(MESSAGE_CLOSE_DECK_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof BackCommand;
    }
}
