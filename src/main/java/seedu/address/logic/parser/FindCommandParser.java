package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FilterNameCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterNameCommand object
 */
public class FindCommandParser implements Parser<FilterNameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterNameCommand
     * and returns a FilterNameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterNameCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterNameCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FilterNameCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
