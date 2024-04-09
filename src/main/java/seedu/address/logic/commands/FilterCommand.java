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
public abstract class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the contact list according to one of the "
            + "three possible types: name, days available or tags and displays them as a list with index numbers.\n"
            + "Parameters: filter TYPE [KEYWORDS]... [--all]\n"
            + "Example 1: " + COMMAND_WORD + " tag student"
            + "Example 2: " + COMMAND_WORD + " day monday tuesday --all";

    private final Predicate<Person> predicate;

    /**
     * Helps subclasses of filter to set appropriate predicates to filter for different
     * fields.
     *
     * @param predicate to be assigned to filter object
     */

    public FilterCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()))
                .withPersonToShow(Model.INVALID_PERSON_INDEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
