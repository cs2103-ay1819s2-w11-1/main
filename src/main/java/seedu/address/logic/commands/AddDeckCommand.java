package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DECKS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.DecksView;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;

/**
 * Adds a new deck to TopDeck.
 */
public class AddDeckCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Create a new deck." + "Parameters: " + PREFIX_NAME + "NAME\n" + "Example: "
                    + COMMAND_WORD + " " + PREFIX_NAME + "MyDeck";

    public static final String MESSAGE_SUCCESS = "New deck added: %1$s";
    public static final String MESSAGE_DUPLICATE_DECK = "A deck with this name already exists";

    private final DecksView decksView;
    private final Deck toAdd;

    /**
     * Creates an AddDeckCommand to add the specified {@code Deck}
     */
    public AddDeckCommand(DecksView decksView, Deck deck) {
        requireNonNull(deck);
        this.decksView = decksView;
        toAdd = deck;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasDeck(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DECK);
        }

        model.addDeck(toAdd);
        model.commitTopDeck();
        decksView.updateFilteredList(PREDICATE_SHOW_ALL_DECKS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDeckCommand // instanceof handles nulls
                && toAdd.equals(((AddDeckCommand) other).toAdd));
    }
}

