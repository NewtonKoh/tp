package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final boolean matchAll;

    /**
     * Constructs a NameContainsKeywordsPredicate with the given keywords and a boolean
     * flag to indicate if we should match all or any.
     */
    public NameContainsKeywordsPredicate(List<String> keywords, boolean matchAll) {
        this.keywords = keywords;
        this.matchAll = matchAll;
    }

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this(keywords, false);
    }

    @Override
    public boolean test(Person person) {
        Predicate<String> predicate = keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword);
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
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords)
                && matchAll == otherNameContainsKeywordsPredicate.matchAll;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .add("matchAll", matchAll)
                .toString();
    }
}
