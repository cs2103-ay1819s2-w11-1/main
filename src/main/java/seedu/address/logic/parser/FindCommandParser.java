package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deck.QuestionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private final String IN_BETWEEN_QUOTES_REGEX = "\"([^\"]*)\"";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArrayList<String> questionKeywords = new ArrayList<>();
        Pattern p = Pattern.compile( IN_BETWEEN_QUOTES_REGEX );
        Matcher m = p.matcher( trimmedArgs );
        while( m.find()) {
            questionKeywords.add(m.group(1));
        }

        trimmedArgs = trimmedArgs.replaceAll(IN_BETWEEN_QUOTES_REGEX, "");
        String[] keyArgs = trimmedArgs.split("\\s+");

        for (String key: keyArgs) {
            if (!key.isEmpty()) {
                questionKeywords.add(key);
            }
        }

        return new FindCommand(new QuestionContainsKeywordsPredicate(questionKeywords));
    }

}
