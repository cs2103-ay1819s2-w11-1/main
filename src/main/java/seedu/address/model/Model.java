package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.deck.Card;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Card> PREDICATE_SHOW_ALL_CARDS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

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
     * Replaces TopDeck data with the data in {@code topDeck}.
     */
    void setTopDeck(ReadOnlyTopDeck topDeck);

    /** Returns the AddressBook */
    ReadOnlyTopDeck getTopDeck();

    /**
     * Returns true if a card with the same identity as {@code card} exists in the deck.
     */
    boolean hasCard(Card card);

    /**
     * Deletes the given card.
     * The card must exist in the deck.
     */
    void deleteCard(Card target);

    /**
     * Adds the given card.
     * {@code card} must not already exist in the deck.
     */
    void addCard(Card card);

    /**
     * Adds the given person at a certain index.
     * {@code person} must not already exist in the address book.
     */
    void addCard(Card card, Index index);

    /**
     * Replaces the given card {@code target} with {@code editedCard}.
     * {@code target} must exist in the deck.
     * The card identity of {@code editedCard} must not be the same as another existing card in the deck.
     */
    void setCard(Card target, Card editedCard);

    /** Returns an unmodifiable view of the filtered card list */
    ObservableList<Card> getFilteredCardList();

    /**
     * Updates the filter of the filtered card list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCardList(Predicate<Card> predicate);

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
     * Selected card in the filtered card list.
     * null if no card is selected.
     */
    ReadOnlyProperty<Card> selectedCardProperty();

    /**
     * Returns the selected card in the filtered card list.
     * null if no card is selected.
     */
    Card getSelectedCard();

    /**
     * Sets the selected card in the filtered card list.
     */
    void setSelectedCard(Card card);
}
