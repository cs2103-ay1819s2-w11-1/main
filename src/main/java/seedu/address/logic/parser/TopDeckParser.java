package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Parses user input.
 */
public class TopDeckParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern
            .compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, Model model) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches() && !userInput.equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        String commandWord = "";
        String arguments = "";
        if (!userInput.equals("")) {
            commandWord = matcher.group("commandWord");
            arguments = matcher.group("arguments");
        }
        try {
            return model.parse(commandWord, arguments);
        } catch (ParseException e) {
            switch (commandWord) {
                case ListCommand.COMMAND_WORD:
                    return new ListCommand(model.getViewState());
                case HistoryCommand.COMMAND_WORD:
                    return new HistoryCommand();
                case UndoCommand.COMMAND_WORD:
                    return new UndoCommand(model.getViewState());
                case RedoCommand.COMMAND_WORD:
                    return new RedoCommand(model.getViewState());
                case ExitCommand.COMMAND_WORD:
                    return new ExitCommand();
                case HelpCommand.COMMAND_WORD:
                    return new HelpCommand();
                default:
                    throw e;
            }
        }

        /**
         switch (commandWord) {

         case AddCardCommand.COMMAND_WORD:
         return new AddCardCommandParser().parse(arguments);

         case EditCardCommand.COMMAND_WORD:
         return new EditCommandParser().parse(arguments);

         case SelectCommand.COMMAND_WORD:
         return new SelectCommandParser().parse(arguments);

         case DeleteCardCommand.COMMAND_WORD:
         return new DeleteCommandParser().parse(arguments);

         case ClearCommand.COMMAND_WORD:
         return new ClearCommand();

         case FindCardCommand.COMMAND_WORD:
         return new FindCardCommandParser().parse(arguments);

         case ListCommand.COMMAND_WORD:
         return new ListCommand();

         case HistoryCommand.COMMAND_WORD:
         return new HistoryCommand();

         case ExitCommand.COMMAND_WORD:
         return new ExitCommand();

         case HelpCommand.COMMAND_WORD:
         return new HelpCommand();

         case UndoCommand.COMMAND_WORD:
         return new UndoCommand();

         case RedoCommand.COMMAND_WORD:
         return new RedoCommand();

         case AddDeckCommand.COMMAND_WORD:
         return new AddDeckCommandParser().parse(arguments);

         default:
         throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
         }
         **/
    }

}
