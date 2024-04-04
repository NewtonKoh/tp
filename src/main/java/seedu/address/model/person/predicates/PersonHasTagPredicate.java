package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tag}s matches any of the keywords given.
 */
public class PersonHasTagPredicate implements Predicate<Person> {

    private final List<Tag> keywords;
    private final boolean matchAll;

    /**
     * Constructs a PersonHasTagPredicate with the given keywords and a boolean
     * flag to indicate if we should match all or any.
     */
    public PersonHasTagPredicate(List<Tag> keywords, boolean matchAll) {
        this.keywords = keywords;
        this.matchAll = matchAll;
    }

    public PersonHasTagPredicate(List<Tag> keywords) {
        this(keywords, false);
    }

    @Override
    public boolean test(Person person) {
        Predicate<Tag> predicate = keyword -> person.getTags().contains(keyword);
        if (matchAll) {
            return keywords.stream()
                    .allMatch(predicate);
        }
        return keywords.stream()
                .anyMatch(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonHasTagPredicate)) {
            return false;
        }

        PersonHasTagPredicate otherPersonHasTagPredicate = (PersonHasTagPredicate) other;
        return keywords.equals(otherPersonHasTagPredicate.keywords)
                && matchAll == otherPersonHasTagPredicate.matchAll;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .add("matchAll", matchAll)
                .toString();
    }
}
