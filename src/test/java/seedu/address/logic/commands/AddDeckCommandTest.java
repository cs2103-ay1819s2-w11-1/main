package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_DECK;
import static seedu.address.testutil.TypicalDecks.getTypicalTopDeck;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.DecksView;
import seedu.address.logic.ListItem;
import seedu.address.logic.ViewState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTopDeck;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.TopDeck;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.DeckBuilder;


public class AddDeckCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDeck_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddDeckCommand((DecksView) model.getViewState(), null);
    }

    @Test
    public void execute_deckAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDeckAdded modelStub = new ModelStubAcceptingDeckAdded();
        Deck validDeck = new DeckBuilder().build();

        CommandResult commandResult = new AddDeckCommand((DecksView) model.getViewState(), validDeck)
                .execute(modelStub, commandHistory);

        assertEquals(String.format(AddDeckCommand.MESSAGE_SUCCESS, validDeck), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validDeck), modelStub.decksAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateDeck_throwsCommandException() throws Exception {
        Deck validDeck = new DeckBuilder().build();
        AddDeckCommand addDeckCommand = new AddDeckCommand((DecksView) model.getViewState(), validDeck);
        ModelStub modelStub = new ModelStubWithDeck(validDeck);

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_DUPLICATE_DECK);
        addDeckCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Deck firstDeck = new DeckBuilder().withName("Test Deck1").build();
        Deck secondDeck = new DeckBuilder().withName("Test Deck2").build();
        AddDeckCommand addFirstDeckCommand = new AddDeckCommand((DecksView) model.getViewState(), firstDeck);
        AddDeckCommand addSecondDeckCommand = new AddDeckCommand((DecksView) model.getViewState(),
                                                                 secondDeck);

        // same object -> returns true
        assertTrue(addFirstDeckCommand.equals(addFirstDeckCommand));

        // same values -> returns true
        AddDeckCommand addFirstDeckCommandCopy = new AddDeckCommand((DecksView) model.getViewState(),
                                                                    firstDeck);
        assertTrue(addFirstDeckCommand.equals(addFirstDeckCommandCopy));

        // different types -> returns false
        assertFalse(addFirstDeckCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstDeckCommand.equals(null));

        // different person -> returns false
        assertFalse(addFirstDeckCommand.equals(addSecondDeckCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTopDeckFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTopDeckFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTopDeck getTopDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTopDeck(ReadOnlyTopDeck newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCard(Card card, Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCard(Card card, Deck deck) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void deleteCard(Card target, Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCard(Card target, Card editedCard, Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Deck getDeck(Deck target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<ListItem> getFilteredList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredList(Predicate<? extends ListItem> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoTopDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoTopDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoTopDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoTopDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitTopDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<ListItem> selectedItemProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ListItem getSelectedItem() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedItem(ListItem item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDeck(Deck deck) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateDeck(Deck target, Deck editedDeck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDeck(Deck target, Deck editedDeck) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Command parse(String commandWord, String arguments) throws ParseException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void changeDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void goToDecksView() {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public void studyDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isAtDecksView() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ViewState getViewState() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isAtCardsView() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single deck.
     */
    private class ModelStubWithDeck extends ModelStub {
        private final Deck deck;

        ModelStubWithDeck(Deck deck) {
            requireNonNull(deck);
            this.deck = deck;
        }

        @Override
        public boolean hasDeck(Deck deck) {
            requireNonNull(deck);
            return this.deck.isSameDeck(deck);
        }
    }

    /**
     * A Model stub that always accept the deck being added.
     */
    private class ModelStubAcceptingDeckAdded extends ModelStub {
        final ArrayList<Deck> decksAdded = new ArrayList<>();

        @Override
        public boolean hasDeck(Deck deck) {
            requireNonNull(deck);
            return decksAdded.stream().anyMatch(deck::isSameDeck);
        }

        @Override
        public void addDeck(Deck deck) {
            requireNonNull(deck);
            decksAdded.add(deck);
        }

        @Override
        public void commitTopDeck() {
            // called by {@code AddDeckCommand#execute()}
        }

        @Override
        public ReadOnlyTopDeck getTopDeck() {
            return new TopDeck();
        }
    }

}

