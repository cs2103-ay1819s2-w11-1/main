package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyTopDeck;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns TopDeck.
     *
     * @see seedu.address.model.Model#getTopDeck()
     */
    ReadOnlyTopDeck getTopDeck();

    /**
     * Sets the selected item.
     */
    void setSelectedItem(ListItem item);

    /**
     * Returns an unmodifiable view of the current filtered list. The element type depends on the view state.
     */
    ObservableList<ListItem> getFilteredList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' TopDeck file path.
     */
    Path getTopDeckFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected item in the filtered card list.
     * null if no item is selected.
     */
    ReadOnlyProperty<ListItem> selectedItemProperty();

    /**
     * Current text in the StudyView
     */
    ReadOnlyProperty<String> textShownProperty();

    /**
     * Current StudyState in the StudyView
     */
    ReadOnlyProperty<StudyView.StudyState> studyStateProperty();

    /**
     * Current userAnswer
     */
    ReadOnlyProperty<String> userAnswerProperty();
}
