package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.ListItem;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;

/**
 * Deletes a deck identified using it's displayed index from TopDeck.
 */
public class DeleteDeckCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Deletes the deck identified by the index number used in the displayed deck list.\n"
                    + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_DECK_SUCCESS = "Deleted Deck: %1$s";

    private Index targetIndex;

    public DeleteDeckCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<ListItem> currentDeckList = model.getFilteredList();

        if (targetIndex.getZeroBased() >= currentDeckList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        Deck deckToDelete = (Deck) currentDeckList.get(targetIndex.getZeroBased());
        model.deleteDeck(deckToDelete);
        model.commitTopDeck();
        return new CommandResult(String.format(MESSAGE_DELETE_DECK_SUCCESS, deckToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDeckCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteDeckCommand) other).targetIndex)); // state check
    }
}
