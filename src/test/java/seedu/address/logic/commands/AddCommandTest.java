package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTopDeck;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.TopDeck;
import seedu.address.model.deck.Card;
import seedu.address.testutil.CardBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_cardAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCardAdded modelStub = new ModelStubAcceptingCardAdded();
        Card validCard = new CardBuilder().build();

        CommandResult commandResult = new AddCommand(validCard).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validCard), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCard), modelStub.cardsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateCard_throwsCommandException() throws Exception {
        Card validCard = new CardBuilder().build();
        AddCommand addCommand = new AddCommand(validCard);
        ModelStub modelStub = new ModelStubWithCard(validCard);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_CARD);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Card addition = new CardBuilder().withQuestion("What is 1 + 1?").build();
        Card subtraction = new CardBuilder().withQuestion("What is 10 - 8?").build();
        AddCommand addAdditionCommand = new AddCommand(addition);
        AddCommand addSubtractionCommand = new AddCommand(subtraction);

        // same object -> returns true
        assertTrue(addAdditionCommand.equals(addAdditionCommand));

        // same values -> returns true
        AddCommand addAdditionCommandCopy = new AddCommand(addition);
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
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
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
        public void addCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCard(Card card, Index index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTopDeck(ReadOnlyTopDeck newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTopDeck getTopDeck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCard(Card target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCard(Card target, Card editedCard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Card> getFilteredCardList() {
            throw new AssertionError("This method should not be called.");
        }

        public void updateFilteredCardList(Predicate<Card> predicate) {
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
        public ReadOnlyProperty<Card> selectedCardProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Card getSelectedCard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedCard(Card card) {
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
        public boolean hasCard(Card card) {
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
        public boolean hasCard(Card card) {
            requireNonNull(card);
            return cardsAdded.stream().anyMatch(card::isSameCard);
        }

        @Override
        public void addCard(Card card) {
            requireNonNull(card);
            cardsAdded.add(card);
        }

        @Override
        public void commitTopDeck() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyTopDeck getTopDeck() {
            return new TopDeck();
        }
    }

}
