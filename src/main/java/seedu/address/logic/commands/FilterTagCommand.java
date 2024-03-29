package seedu.address.logic.commands;

import seedu.address.model.person.predicates.PersonHasTagPredicate;

/**
 * Filters and lists all persons in address book who are tagged by any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FilterTagCommand extends FilterCommand {

    public static final String TYPE = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters for all persons whose tags include any of "
            + "the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " tag student";

    /**
     * Returns a new FilterTagCommand object that takes in a PersonHasTagPredicate
     * to update the filtered list
     * @param predicate
     */
    public FilterTagCommand(PersonHasTagPredicate predicate) {
        super(predicate);
    }
}
