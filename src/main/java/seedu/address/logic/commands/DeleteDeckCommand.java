package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DECKS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.DecksView;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;

/**
 * Deletes a deck identified using it's displayed index from TopDeck.
 */
public class DeleteDeckCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the deck identified by the index number used in the displayed deck list.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_DECK_SUCCESS = "Deleted Deck: %1$s";
    private final DecksView decksView;
    private Index targetIndex;

    public DeleteDeckCommand(DecksView decksView, Index targetIndex) {
        this.decksView = decksView;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Deck> currentDeckList = decksView.getFilteredList();

        if (targetIndex.getZeroBased() >= currentDeckList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        Deck deckToDelete = currentDeckList.get(targetIndex.getZeroBased());
        model.deleteDeck(deckToDelete);
        model.commitTopDeck();
        decksView.updateFilteredList(PREDICATE_SHOW_ALL_DECKS);
        return new CommandResult(String.format(MESSAGE_DELETE_DECK_SUCCESS, deckToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDeckCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteDeckCommand) other).targetIndex)); // state check
    }
}
