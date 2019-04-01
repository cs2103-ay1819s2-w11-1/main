package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DECKS;

import seedu.address.logic.CardsView;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.DecksView;
import seedu.address.logic.ViewState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;

/**
 * Reverts the {@code model}'s deck to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";
    private final ViewState viewState;

    public RedoCommand(ViewState viewState) {
        this.viewState = viewState;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoTopDeck()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoTopDeck();
        if (model.isAtDecksView()) {
            DecksView decksView = (DecksView) viewState;
            decksView.updateFilteredList(PREDICATE_SHOW_ALL_DECKS);
        } else if (model.isAtCardsView()) {
            CardsView currentView = (CardsView) viewState;
            Deck currentActiveDeck = currentView.getActiveDeck();
            Deck newActiveDeck = model.getDeck(currentActiveDeck);
            model.changeDeck(newActiveDeck);
        }
        return new UpdatePanelCommandResult(MESSAGE_SUCCESS);
    }
}
