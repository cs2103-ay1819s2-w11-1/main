package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CardsView;
import seedu.address.logic.DecksView;
import seedu.address.logic.ListItem;
import seedu.address.logic.StudyView;
import seedu.address.logic.ViewState;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.exceptions.CardNotFoundException;
import seedu.address.model.deck.exceptions.IllegalOperationWhileReviewingCardException;

/**
 * Represents the in-memory model of top deck data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedTopDeck versionedTopDeck;
    private final UserPrefs userPrefs;
    private final SimpleObjectProperty<ListItem> selectedItem = new SimpleObjectProperty<>();
    private FilteredList<? extends ListItem> filteredItems;
    private ViewState viewState;

    /**
     * Initializes a ModelManager with the given topDeck and userPrefs.
     */
    public ModelManager(ReadOnlyTopDeck topDeck, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(topDeck, userPrefs);

        logger.fine("Initializing with address book: " + topDeck + " and user prefs " + userPrefs);

        versionedTopDeck = new VersionedTopDeck(topDeck);
        this.userPrefs = new UserPrefs(userPrefs);
        viewState = new DecksView(this, new FilteredList<>(versionedTopDeck.getDeckList()));
        filteredItems = ((DecksView) viewState).filteredDecks;
    }

    public ModelManager() {
        this(new TopDeck(), new UserPrefs());
    }

    public Command parse(String commandWord, String arguments) throws ParseException {
        return viewState.parse(commandWord, arguments);
    }

    /**
     * Updates the given ViewState to CardsView with the given deck.
     */
    public void changeDeck(Deck deck) {
        viewState = new CardsView(this, deck);

        filteredItems = ((CardsView) viewState).filteredCards;
        setSelectedItem(null);
    }

    public void studyDeck(Deck deck) {
        viewState = new StudyView(deck);
    }

    @Override
    public void goToDecksView() {
        viewState = new DecksView(this, new FilteredList<>(versionedTopDeck.getDeckList()));
        filteredItems = ((DecksView) viewState).filteredDecks;
        setSelectedItem(null);
    }

    @Override
    public boolean isAtDecksView() {
        return (viewState instanceof DecksView);
    }

    @Override
    public boolean isAtCardsView() {
        return (viewState instanceof CardsView);
    }

    @Override
    public ViewState getViewState() {
        return viewState;
    }


    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTopDeckFilePath() {
        return userPrefs.getTopDeckFilePath();
    }

    @Override
    public void setTopDeckFilePath(Path topDeckFilePath) {
        requireNonNull(topDeckFilePath);
        userPrefs.setTopDeckFilePath(topDeckFilePath);
    }

    //=========== TopDeck ================================================================================

    @Override
    public ReadOnlyTopDeck getTopDeck() {
        return versionedTopDeck;
    }

    @Override
    public void setTopDeck(ReadOnlyTopDeck topDeck) {
        versionedTopDeck.resetData(topDeck);
    }

    @Override
    public boolean hasCard(Card card, Deck deck) {
        requireAllNonNull(card, deck);

        return deck.hasCard(card);
    }

    @Override
    public void deleteCard(Card target, Deck deck) {
        requireAllNonNull(target, deck);

        Deck editedDeck = versionedTopDeck.deleteCard(target, deck);
        changeDeck(editedDeck);
    }

    @Override
    public void addCard(Card card, Deck deck) {
        requireAllNonNull(card, deck);

        Deck editedDeck = versionedTopDeck.addCard(card, deck);
        changeDeck(editedDeck);
    }

    @Override
    public void setCard(Card target, Card editedCard, Deck deck) {
        requireAllNonNull(target, editedCard, deck);

        Deck editedDeck = versionedTopDeck.setCard(target, editedCard, deck);
        changeDeck(editedDeck);
    }

    @Override
    public Deck getDeck(Deck target) {
        return versionedTopDeck.getDeck(target);
    }

    @Override
    public void addDeck(Deck deck) {
        logger.info("Added a new deck to TopDeck.");
        versionedTopDeck.addDeck(deck);
        commitTopDeck();
    }

    @Override
    public boolean hasDeck(Deck deck) {
        requireAllNonNull(deck);
        return versionedTopDeck.hasDeck(deck);
    }

    @Override
    public void deleteDeck(Deck deck) {
        logger.info("Deleted a deck.");

        versionedTopDeck.deleteDeck(deck);
    }

    @Override
    public void setDeck(Deck target, Deck editedDeck) {
        requireAllNonNull(target, editedDeck);

        DecksView decksView = (DecksView) viewState;

        versionedTopDeck.setDecks(decksView.filteredDecks);
    }

    @Override
    public void updateDeck(Deck target, Deck editedDeck) {
        requireAllNonNull(target, editedDeck);
        logger.info("Updated a deck's name in TopDeck.");
        versionedTopDeck.updateDeck(target, editedDeck);
    }

    //=========== Filtered Card List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Item} backed by the internal list of
     * {@code versionedTopDeck}
     */
    @Override
    public ObservableList<ListItem> getFilteredList() {
        return (ObservableList<ListItem>) filteredItems;
    }

    // TODO: This is no longer used by actual code. Update tests
    @Override
    public void updateFilteredList(Predicate<? extends ListItem> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate((Predicate<ListItem>) predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoTopDeck() {
        return versionedTopDeck.canUndo();
    }

    @Override
    public boolean canRedoTopDeck() {
        return versionedTopDeck.canRedo();
    }

    @Override
    public void undoTopDeck() {
        versionedTopDeck.undo();
    }

    @Override
    public void redoTopDeck() {
        versionedTopDeck.redo();
    }

    @Override
    public void commitTopDeck() {
        versionedTopDeck.commit();
    }

    //=========== Selected item ===========================================================================

    @Override
    public ReadOnlyProperty<ListItem> selectedItemProperty() {
        return selectedItem;
    }

    @Override
    public ListItem getSelectedItem() {
        return selectedItem.getValue();
    }

    @Override
    public void setSelectedItem(ListItem card) {
        if (card != null && !filteredItems.contains(card)) {
            throw new CardNotFoundException();
        }
        selectedItem.setValue(card);


        if (card instanceof Card && isAtCardsView()) {
            CardsView cardsView = (CardsView) viewState;
            cardsView.selectedCard.set((Card) card);
        } else if (card instanceof Deck && isAtDecksView()) {
            //TODO: Deck has to set its selection
        } else if (card != null) {
            throw new IllegalOperationWhileReviewingCardException();
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedTopDeck.equals(other.versionedTopDeck) && userPrefs.equals(other.userPrefs)
                && filteredItems.equals(other.filteredItems) && Objects
                .equals(selectedItem.get(), other.selectedItem.get());
    }

}
