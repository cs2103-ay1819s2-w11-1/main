package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalDecks.getTypicalTopDeck;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CardsView;
import seedu.address.logic.CommandHistory;
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
import seedu.address.testutil.CardBuilder;

public class AddCardCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void initialize() {
        model.changeDeck(getTypicalDeck());
        assertTrue(model.isAtCardsView());
    }

    @Test
    public void constructor_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCardCommand((CardsView) model.getViewState(), null);
    }

    @Test
    public void execute_cardAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCardAdded modelStub = new ModelStubAcceptingCardAdded();
        Card validCard = new CardBuilder().build();

        CommandResult commandResult = new AddCardCommand((CardsView) model.getViewState(), validCard)
                .execute(modelStub, commandHistory);

        assertEquals(String.format(AddCardCommand.MESSAGE_SUCCESS, validCard),
                     commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCard), modelStub.cardsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateCard_throwsCommandException() throws Exception {
        Card validCard = new CardBuilder().build();
        AddCardCommand addCardCommand = new AddCardCommand((CardsView) model.getViewState(), validCard);
        ModelStub modelStub = new ModelStubWithCard(validCard);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCardCommand.MESSAGE_DUPLICATE_CARD);
        addCardCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Card addition = new CardBuilder().withQuestion("What is 1 + 1?").build();
        Card subtraction = new CardBuilder().withQuestion("What is 10 - 8?").build();
        AddCardCommand addAdditionCommand = new AddCardCommand((CardsView) model.getViewState(), addition);
        AddCardCommand addSubtractionCommand = new AddCardCommand((CardsView) model.getViewState(),
                                                                  subtraction);

        // same object -> returns true
        assertTrue(addAdditionCommand.equals(addAdditionCommand));

        // same values -> returns true
        AddCardCommand addAdditionCommandCopy = new AddCardCommand((CardsView) model.getViewState(),
                                                                   addition);
        assertTrue(addAdditionCommand.equals(addAdditionCommandCopy));

        // different types -> returns false
        assertFalse(addAdditionCommand.equals(1));

        // null -> returns false
        assertFalse(addAdditionCommand.equals(null));

        // different card -> returns false
        assertFalse(addAdditionCommand.equals(addSubtractionCommand));
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
        public Deck getDeck(Deck target) {
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
     * A Model stub that contains a single card.
     */
    private class ModelStubWithCard extends ModelStub {
        private final Card card;

        ModelStubWithCard(Card card) {
            requireNonNull(card);
            this.card = card;
        }

        @Override
        public boolean hasCard(Card card, Deck deck) {
            requireNonNull(card);
            return this.card.isSameCard(card);
        }
    }

    /**
     * A Model stub that always accept the card being added.
     */
    private class ModelStubAcceptingCardAdded extends ModelStub {
        final ArrayList<Card> cardsAdded = new ArrayList<>();

        @Override
        public boolean hasCard(Card card, Deck deck) {
            requireNonNull(card);
            return cardsAdded.stream().anyMatch(card::isSameCard);
        }

        @Override
        public void addCard(Card card, Deck deck) {
            requireNonNull(card);
            cardsAdded.add(card);
        }

        @Override
        public void commitTopDeck() {
            // called by {@code AddCardCommand#execute()}
        }

        @Override
        public ReadOnlyTopDeck getTopDeck() {
            return new TopDeck();
        }
    }

}
