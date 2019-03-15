package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 *
 */

public class ReverseCommandTest {

    private final Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
//    private final Model expectedModel = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute() {
        assertCommandFailure(new ReverseCommand(), model, commandHistory, ReverseCommand.MESSAGE_FAILURE);
    }


}
