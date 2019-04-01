package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DECK_NOT_EDITED;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.DecksView;
import seedu.address.logic.commands.EditDeckCommand;
import seedu.address.logic.commands.EditDeckCommand.EditDeckDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditDeck object
 */
public class EditDeckCommandParser implements Parser<EditDeckCommand> {

    private final DecksView decksView;

    public EditDeckCommandParser(DecksView decksView) {
        this.decksView = decksView;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditDeckCommand
     * and returns an EditDeckCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditDeckCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDeckCommand.MESSAGE_USAGE), pe);
        }

        EditDeckDescriptor editDeckDescriptor = new EditDeckDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editDeckDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (!editDeckDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_DECK_NOT_EDITED);
        }

        return new EditDeckCommand(decksView, index, editDeckDescriptor);
    }
}
