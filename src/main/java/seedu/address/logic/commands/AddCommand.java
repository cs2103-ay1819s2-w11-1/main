package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;

/**
 * Adds a card to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a card to the deck. "
            + "Parameters: "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUESTION + "Is this a question? "
            + PREFIX_ANSWER + "Yes it is "
            + PREFIX_TAG + "basic "
            + PREFIX_TAG + "test";

    public static final String MESSAGE_SUCCESS = "New card added: %1$s";
    public static final String MESSAGE_DUPLICATE_CARD = "This card already exists in the deck";


    private final Card toAdd;
    private Optional<Index> indexToInsert;


    /**
     * Creates an AddCommand to add the specified {@code Card}
     */
    public AddCommand(Card card) {
        requireNonNull(card);
        toAdd = card;
        indexToInsert = Optional.empty();
    }

    public AddCommand(Card card, Index targetIndex) {
        requireNonNull(card);
        toAdd = card;
        indexToInsert = Optional.of(targetIndex);
    }

    /**
     * Creates a DeleteCommand to delete the specified {@code Person}
     */
    public DeleteCommand inverse(Model model) {

        List<Card> lastShownList = model.getFilteredCardList();
        Index targetIndex = Index.fromZeroBased(lastShownList.size() - 1);
        return new DeleteCommand(targetIndex);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasCard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        }

        if (indexToInsert.isPresent()){
            model.addCard(toAdd, indexToInsert.get());
        } else {
            model.addCard(toAdd);
        }

        model.commitTopDeck();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
