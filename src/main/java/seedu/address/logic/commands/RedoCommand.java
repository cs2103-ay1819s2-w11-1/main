package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s deck to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoTopDeck()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoTopDeck();
        model.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
    public Command inverse(Model model) {
        return this;
    }
}
