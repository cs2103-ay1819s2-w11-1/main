package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TopDeck;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyTopDeck_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitTopDeck();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS,
                             expectedModel);
    }

    @Test
    public void execute_nonEmptyTopDeck_success() {
        Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalTopDeck(), new UserPrefs());
        expectedModel.setTopDeck(new TopDeck());
        expectedModel.commitTopDeck();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS,
                             expectedModel);
    }

}
