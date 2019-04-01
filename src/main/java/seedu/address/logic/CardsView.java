package seedu.address.logic;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.function.Predicate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.commands.AddCardCommand;
import seedu.address.logic.commands.BackCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCardCommand;
import seedu.address.logic.commands.EditCardCommand;
import seedu.address.logic.commands.FindCardCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.StudyDeckCommand;
import seedu.address.logic.parser.AddCardCommandParser;
import seedu.address.logic.parser.DeleteCardCommandParser;
import seedu.address.logic.parser.EditCardCommandParser;
import seedu.address.logic.parser.FindCardCommandParser;
import seedu.address.logic.parser.SelectCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

/**
 * Stores the state of the Card's view.
 */
public class CardsView implements ListViewState {

    public final FilteredList<Card> filteredCards;
    public final SimpleObjectProperty<Card> selectedCard = new SimpleObjectProperty<>();
    private final Model model;
    private final Deck activeDeck;

    public CardsView(Model model, Deck deck) {
        this.model = model;
        this.activeDeck = deck;
        filteredCards = new FilteredList<>(deck.getCards().asUnmodifiableObservableList());
        filteredCards.addListener(this::ensureSelectedItemIsValid);
    }

    @Override
    public Command parse(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
            case AddCardCommand.COMMAND_WORD:
                return new AddCardCommandParser(this).parse(arguments);
            case DeleteCardCommand.COMMAND_WORD:
                return new DeleteCardCommandParser(this).parse(arguments);
            case EditCardCommand.COMMAND_WORD:
                return new EditCardCommandParser(this).parse(arguments);
            case FindCardCommand.COMMAND_WORD:
                return new FindCardCommandParser(this).parse(arguments);
            case SelectCommand.COMMAND_WORD:
                return new SelectCommandParser(this).parse(arguments);
            case BackCommand.COMMAND_WORD:
                return new BackCommand();
            case StudyDeckCommand.COMMAND_WORD:
                return new StudyDeckCommand(activeDeck);
            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    /**
     * Ensures {@code selectedItem} is a valid card in {@code filteredItems}.
     */
    private void ensureSelectedItemIsValid(ListChangeListener.Change<? extends Card> change) {
        while (change.next()) {
            if (selectedCard.getValue() == null) {
                // null is always a valid selected card, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedItemReplaced =
                    change.wasReplaced() && change.getAddedSize() == change.getRemovedSize() && change
                            .getRemoved().contains(selectedCard.getValue());
            if (wasSelectedItemReplaced) {
                // Update selectedCard to its new value.
                int index = change.getRemoved().indexOf(selectedCard.getValue());
                selectedCard.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedItemRemoved = change.getRemoved().stream().anyMatch(
                removedItem -> selectedCard.getValue().equals(removedItem));
            if (wasSelectedItemRemoved) {
                // Select the card that came before it in the list,
                // or clear the selection if there is no such card.
                selectedCard
                        .setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    /**
     * Updates the filtered list in CardsView.
     */
    public void updateFilteredList(Predicate<Card> predicate) {
        requireNonNull(predicate);
        filteredCards.setPredicate(predicate);
    }

    public ObservableList<Card> getFilteredList() {
        return filteredCards;
    }
}
