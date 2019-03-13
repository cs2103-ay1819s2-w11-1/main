package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.address.logic.commands.Command;

/**
 * Stores the history of command (together with its inverse) executed by the user.
 */
public class CommandStack {
    private final ObservableList<Pair<Command, Command>> userCommandHistory = FXCollections.observableArrayList();
    private final ObservableList<Pair<Command, Command>> unmodifiableUserCommandHistory =
            FXCollections.unmodifiableObservableList(userCommandHistory);


    public CommandStack() {}

    public CommandStack(CommandStack commandHistory) {
        userCommandHistory.addAll(commandHistory.userCommandHistory);
    }


    /**
     * Appends {@code userInput} to the list of user input entered.
     */
    public void add(Command command, Command inverseCommand) {
        requireNonNull(command);
        Pair<Command, Command> commandPair = new Pair<>(command, inverseCommand);
        userCommandHistory.add(commandPair);
    }

    /**
     * Returns an unmodifiable view of {@code userCommandHistory}.
     */
    public ObservableList<Pair<Command, Command>> getCommandHistory() {
        return unmodifiableUserCommandHistory;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CommandStack)) {
            return false;
        }

        // state check
        CommandStack other = (CommandStack) obj;
        return userCommandHistory.equals(other.userCommandHistory);
    }

    @Override
    public int hashCode() {
        return userCommandHistory.hashCode();
    }
}
