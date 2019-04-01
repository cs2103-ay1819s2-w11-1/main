package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.ListItem;
import seedu.address.logic.ViewState;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Deck> PREDICATE_SHOW_ALL_DECKS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Card> PREDICATE_SHOW_ALL_CARDS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getTopDeckFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setTopDeckFilePath(Path addressBookFilePath);

    /**
     * Returns the TopDeck
     */
    ReadOnlyTopDeck getTopDeck();

    /**
     * Replaces TopDeck data with the data in {@code topDeck}.
     */
    void setTopDeck(ReadOnlyTopDeck topDeck);

    /**
     * Returns true if a card with the same identity as {@code card} exists in the deck.
     */
    boolean hasCard(Card card, Deck deck);

    /**
     * Deletes the given card.
     * The card must exist in the deck.
     */
    void deleteCard(Card target, Deck deck);

    /**
     * Adds the given card.
     * {@code card} must not already exist in the deck.
     */
    void addCard(Card card, Deck deck);

    /**
     * Replaces the given card {@code target} with {@code editedCard}.
     * {@code target} must exist in the deck.
     * The card identity of {@code editedCard} must not be the same as another existing card in the deck.
     */
    void setCard(Card target, Card editedCard, Deck deck);

    /**
     * Returns an unmodifiable view of the filtered list
     */
    ObservableList<ListItem> getFilteredList();

    /**
     * Updates the filter of the filtered card list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredList(Predicate<? extends ListItem> predicate);

    /**
     * Returns true if the model has previous TopDeck states to restore.
     */
    boolean canUndoTopDeck();

    /**
     * Returns true if the model has undone TopDeck states to restore.
     */
    boolean canRedoTopDeck();

    /**
     * Restores the model's TopDeck to its previous state.
     */
    void undoTopDeck();

    /**
     * Restores the model's TopDeck to its previously undone state.
     */
    void redoTopDeck();

    /**
     * Saves the current TopDeck state for undo/redo.
     */
    void commitTopDeck();

    /**
     * Selected item in the filtered list.
     * null if no item is selected.
     */
    ReadOnlyProperty<ListItem> selectedItemProperty();

    /**
     * Returns the selected Item in the filtered list.
     * null if no card is selected.
     */
    ListItem getSelectedItem();

    /**
     * Sets the selected item in the filtered list.
     */
    void setSelectedItem(ListItem item);

    /**
     * Adds a new deck in the filtered deck list.
     */
    void addDeck(Deck deck);

    /**
     * Returns the target deck.
     */
    Deck getDeck(Deck target);

    /**
     * Deletes the given deck.
     * The deck must exist in TopDeck.
     */
    void deleteDeck(Deck target);

    boolean hasDeck(Deck deck);

    void updateDeck(Deck target, Deck editedDeck);

    Command parse(String commandWord, String arguments) throws ParseException;

    void changeDeck(Deck deck);

    /**
     * Replaces the given deck {@code target} with {@code editedDeck}.
     * {@code target} must exist in the TopDeck.
     * The deck identity of {@code editedDeck} must not be the same as another existing deck in TopDeck.
     */
    void setDeck(Deck target, Deck editedDeck);

    /**
     * Changes view state to show a single card at a time
     */
    void studyDeck(Deck deck);

    void goToDecksView();

    boolean isAtDecksView();

    /**
     * Returns true is the current ViewState is at CardsView.
     */
    boolean isAtCardsView();

    /**
     * Returns the current ViewState.
     */
    ViewState getViewState();
}
