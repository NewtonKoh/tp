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

    public PersonAvailableOnDayPredicate(Collection<Day> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    @Override
    public boolean test(Person person) {
        return daysAvailable.stream()
                .anyMatch(days -> person.getDaysAvailable().contains(days));
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
        return daysAvailable.equals(otherPersonAvailableOnDayPredicate.daysAvailable);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", daysAvailable).toString();
    }
}
