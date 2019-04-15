package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GenerateQuestionCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.TopDeckParser;
import seedu.address.logic.parser.ViewStateParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ListViewState;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTopDeck;
import seedu.address.storage.Storage;
import seedu.address.ui.MainPanel;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final TopDeckParser topDeckParser;
    private boolean topDeckModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        topDeckParser = new TopDeckParser();

        // Set topDeckModified to true whenever the models' address book is modified.
        model.getTopDeck().addListener(observable -> topDeckModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        topDeckModified = false;

        CommandResult commandResult;
        try {
            ViewStateParser viewStateParser = model.getViewState().getViewStateParser();
            Command command = topDeckParser.parseCommand(commandText, viewStateParser);
            commandResult = command.execute(model, history);
            if (command instanceof GenerateQuestionCommand) {
                topDeckModified = true;
            }
        } finally {
            history.add(commandText);
        }

        if (topDeckModified) {
            logger.info("TopDeck modified, saving to file.");
            try {
                storage.saveTopDeck(model.getTopDeck());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }
        return commandResult;
    }

    @Override
    public ReadOnlyTopDeck getTopDeck() {
        return model.getTopDeck();
    }

    @Override
    public ObservableList<ListItem> getFilteredList() {
        return ((ListViewState) model.getViewState()).getFilteredList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getTopDeckFilePath() {
        return model.getTopDeckFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public MainPanel getPanel() {
        return model.getViewState().getPanel();
    }
}
