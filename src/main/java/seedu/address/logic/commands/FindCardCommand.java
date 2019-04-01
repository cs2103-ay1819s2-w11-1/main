package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CardsView;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.deck.QuestionContainsKeywordsPredicate;

/**
 * Finds and lists all cards in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCardCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Finds all cards whose questions contain any of "
                    + "the specified keywords (case-insensitive) and displays them as a list with index "
                    + "numbers.\n" + "Parameters: KEYWORD [MORE_KEYWORDS]...\n" + "Example: " + COMMAND_WORD
                    + " alice bob charlie";

    private final CardsView cardsView;
    private final QuestionContainsKeywordsPredicate predicate;

    public FindCardCommand(CardsView cardsView, QuestionContainsKeywordsPredicate predicate) {
        this.cardsView = cardsView;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        cardsView.updateFilteredList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CARDS_LISTED_OVERVIEW, cardsView.getFilteredList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCardCommand // instanceof handles nulls
                && predicate.equals(((FindCardCommand) other).predicate)); // state check
    }
}
