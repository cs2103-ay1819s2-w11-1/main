package seedu.address.logic.commands;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Executes the reverse of the last command in command history.
 *
 */
public class ReverseCommand extends Command {

    public static final String COMMAND_WORD = "reverse";
    public static final String MESSAGE_SUCCESS = "Reverse success!";
    public static final String MESSAGE_FAILURE = "No more commands to reverse!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_FAILURE);
    }

    public Command inverse(Model model){
        return this;
    }
}
