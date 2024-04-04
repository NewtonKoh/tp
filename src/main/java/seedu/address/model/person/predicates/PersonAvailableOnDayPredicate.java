package seedu.address.model.person.predicates;

import java.util.Collection;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Day;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code daysAvailable} matches any of the days given.
 */
public class PersonAvailableOnDayPredicate implements Predicate<Person> {
    private final Collection<Day> daysAvailable;
    private final boolean matchAll;

    /**
     * Constructs a PersonAvailableOnDayPredicate with the given keywords and a boolean
     * flag to indicate if we should match all or any.
     */
    public PersonAvailableOnDayPredicate(Collection<Day> daysAvailable, boolean matchAll) {
        this.daysAvailable = daysAvailable;
        this.matchAll = matchAll;
    }

    public PersonAvailableOnDayPredicate(Collection<Day> daysAvailable) {
        this(daysAvailable, false);
    }

    @Override
    public boolean test(Person person) {
        Predicate<Day> predicate = day -> person.getDaysAvailable().contains(day);
        if (matchAll) {
            return daysAvailable.stream()
                    .allMatch(predicate);
        }
        return daysAvailable.stream()
                .anyMatch(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonAvailableOnDayPredicate)) {
            return false;
        }

        PersonAvailableOnDayPredicate otherPersonAvailableOnDayPredicate = (PersonAvailableOnDayPredicate) other;
        return daysAvailable.equals(otherPersonAvailableOnDayPredicate.daysAvailable)
                && matchAll == otherPersonAvailableOnDayPredicate.matchAll;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", daysAvailable)
                .add("matchAll", matchAll)
                .toString();
    }
}
