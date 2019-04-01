package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DECKS;

import seedu.address.logic.CardsView;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.DecksView;
import seedu.address.logic.ViewState;
import seedu.address.model.Model;

/**
 * Lists all decks to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_DECK_SUCCESS = "Listed all decks";
    public static final String MESSAGE_CARD_SUCCESS = "Listed all cards";

    private final ViewState viewState;

    public ListCommand(ViewState viewState) {
        this.viewState = viewState;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (model.isAtDecksView()) {
            DecksView decksView = (DecksView) viewState;
            decksView.updateFilteredList(PREDICATE_SHOW_ALL_DECKS);
            return new CommandResult(MESSAGE_DECK_SUCCESS);
        } else {
            CardsView cardsView = (CardsView) viewState;
            cardsView.updateFilteredList(PREDICATE_SHOW_ALL_CARDS);
            return new CommandResult(MESSAGE_CARD_SUCCESS);
        }

    }
}
