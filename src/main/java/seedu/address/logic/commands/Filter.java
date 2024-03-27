package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * A common superclass for all filter commands that have the same logic, but filter using
 * different predicates.
 */
public abstract class Filter extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the contact list according to one of the "
            + "two possible fields: days and tags and displays them as a list with index numbers.\n"
            + "Parameters: filter [type] [keywords]...\n"
            + "Example 1: " + COMMAND_WORD + "tag student"
            + "Example 2: " + COMMAND_WORD + "day monday";

    private Predicate<Person> predicate;

    /**
     * Helps subclasses of filter to set appropriate predicates to filter for different
     * fields.
     * @param predicate to be assigned to filter object
     */
    public void setPredicate(Predicate<Person> predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Filter)) {
            return false;
        }

        Filter otherFilter = (Filter) other;
        return predicate.equals(otherFilter.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
