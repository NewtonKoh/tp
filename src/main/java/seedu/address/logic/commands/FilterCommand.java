package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.predicates.PersonHasTagPredicate;

/**
 * Sorts and lists all persons in address book who are tagged by any of the argument keywords.
 * Keyword matching is case-sensitive.
 */
public class FilterCommand extends Filter {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters for all persons whose tags include any of "
            + "the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friend teacher student";

    /**
     * Returns a new FilterCommand object that takes in a PersonHasTagPredicate
     * to update the filtered list
     * @param predicate
     */
    public FilterCommand(PersonHasTagPredicate predicate) {
        this.predicate = predicate;
    }
}
