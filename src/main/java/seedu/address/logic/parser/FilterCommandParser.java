package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import seedu.address.logic.commands.Filter;
import seedu.address.logic.commands.FilterDayCommand;
import seedu.address.logic.commands.FilterTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Day;
import seedu.address.model.person.predicates.PersonAvailableOnDayPredicate;
import seedu.address.model.person.predicates.PersonHasTagPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FilterTagCommand object
 */
public class FilterCommandParser implements Parser<Filter> {
    private static final String dayFilterType = "day";
    private static final String tagFilterType = "tag";

    /**
     * Parses the given {@code String} of arguments in the context of the FilterTagCommand
     * and returns a FilterTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Filter parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String argsWithoutType = "";
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, Filter.MESSAGE_USAGE));
        }
        argsWithoutType = trimmedArgs.substring(3).trim();
        if (trimmedArgs.toLowerCase().startsWith(dayFilterType)) {
            if (argsWithoutType.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                FilterDayCommand.MESSAGE_USAGE));
            }

            Set<Day> getDays = ParserUtil.parseDays(Arrays.asList(argsWithoutType.split("\\s+")));
            return new FilterDayCommand(new PersonAvailableOnDayPredicate(new ArrayList<>(getDays)));
        }
        if (trimmedArgs.toLowerCase().startsWith(tagFilterType)) {
            if (argsWithoutType.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                FilterTagCommand.MESSAGE_USAGE));
            }

            Set<Tag> sortKeywords = ParserUtil.parseTags(Arrays.asList(argsWithoutType.split("\\s+")));
            return new FilterTagCommand(new PersonHasTagPredicate(new ArrayList<>(sortKeywords)));
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Filter.MESSAGE_USAGE));
    }
}
