package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import seedu.address.logic.commands.FilterDayCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Days;
import seedu.address.model.person.predicates.PersonAvailableOnDayPredicate;

/**
 * Parses input arguments and creates a new FilterTagCommand object
 */
public class FilterDayCommandParser implements Parser<FilterDayCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FilterTagCommand
     * and returns a FilterTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterDayCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            FilterDayCommand.MESSAGE_USAGE));
        }

        Set<Days> getDays = ParserUtil.parseDays(Arrays.asList(trimmedArgs.split("\\s+")));

        return new FilterDayCommand(new PersonAvailableOnDayPredicate(new ArrayList<>(getDays)));
    }
}
