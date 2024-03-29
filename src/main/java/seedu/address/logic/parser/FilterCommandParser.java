package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.Filter;
import seedu.address.logic.commands.FilterDayCommand;
import seedu.address.logic.commands.FilterNameCommand;
import seedu.address.logic.commands.FilterTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Day;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PersonAvailableOnDayPredicate;
import seedu.address.model.person.predicates.PersonHasTagPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new Filter object
 */
public class FilterCommandParser implements Parser<Filter> {
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

        if (trimmedArgs.toLowerCase().startsWith(FilterDayCommand.TYPE)) {
            argsWithoutType = trimmedArgs.replaceFirst(FilterDayCommand.TYPE, "").trim();
            if (argsWithoutType.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                FilterDayCommand.MESSAGE_USAGE));
            }

            Set<Day> getDays = ParserUtil.parseDays(Arrays.asList(argsWithoutType.split("\\s+")));
            return new FilterDayCommand(new PersonAvailableOnDayPredicate(new ArrayList<>(getDays)));
        }

        if (trimmedArgs.toLowerCase().startsWith(FilterTagCommand.TYPE)) {
            argsWithoutType = trimmedArgs.replaceFirst(FilterTagCommand.TYPE, "").trim();
            if (argsWithoutType.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                FilterTagCommand.MESSAGE_USAGE));
            }

            Set<Tag> sortKeywords = ParserUtil.parseTags(Arrays.asList(argsWithoutType.split("\\s+")));
            return new FilterTagCommand(new PersonHasTagPredicate(new ArrayList<>(sortKeywords)));
        }

        if (trimmedArgs.toLowerCase().startsWith(FilterNameCommand.TYPE)) {
            argsWithoutType = trimmedArgs.replaceFirst(FilterNameCommand.TYPE, "").trim();
            if (argsWithoutType.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                FilterTagCommand.MESSAGE_USAGE));
            }

            return new FilterNameCommand(new NameContainsKeywordsPredicate(List.of(argsWithoutType)));
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Filter.MESSAGE_USAGE));
    }
}
