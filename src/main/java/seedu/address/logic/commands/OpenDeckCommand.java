package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.DecksView;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;

/**
 * Selects a deck identified using its displayed index.
 */
public class OpenDeckCommand extends Command {

    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Opens the deck identified by the index number.\n"
                    + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_OPEN_DECK_SUCCESS = "Opened deck";

    private Index targetIndex;
    private DecksView decksView;

    public OpenDeckCommand(Index targetIndex, DecksView decksView) {
        this.targetIndex = targetIndex;
        this.decksView = decksView;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);

        List<Deck> filteredDeckList = decksView.filteredDecks;

        if (targetIndex.getZeroBased() >= filteredDeckList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        model.changeDeck(filteredDeckList.get(targetIndex.getZeroBased()));

        return new UpdatePanelCommandResult(
                String.format(MESSAGE_OPEN_DECK_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OpenDeckCommand // instanceof handles nulls
                && targetIndex.equals(((OpenDeckCommand) other).targetIndex)); // state check
    }
}
