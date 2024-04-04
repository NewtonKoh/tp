package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Day;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestUtil;

public class PersonAvailableOnDayPredicateTest {
    @Test
    public void equals() throws Exception {
        List<Day> firstPredicateKeywordList = TestUtil.stringsToDays(Collections.singletonList("monday"));
        List<Day> secondPredicateKeywordList = TestUtil.stringsToDays(Arrays.asList("monday", "tuesday"));

        PersonAvailableOnDayPredicate firstPredicate = new PersonAvailableOnDayPredicate(firstPredicateKeywordList);
        PersonAvailableOnDayPredicate secondPredicate = new PersonAvailableOnDayPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonAvailableOnDayPredicate firstPredicateCopy = new PersonAvailableOnDayPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_availabilityContainsKeywords_returnsTrue() throws Exception {
        // One keyword
        PersonAvailableOnDayPredicate predicate =
                new PersonAvailableOnDayPredicate(TestUtil.stringsToDays(Collections.singletonList("monday")));
        assertTrue(predicate.test(new PersonBuilder().withDaysAvailable("monday").build()));

        // Multiple keywords
        predicate = new PersonAvailableOnDayPredicate(TestUtil.stringsToDays(Arrays.asList("monday", "tuesday")));
        assertTrue(predicate.test(new PersonBuilder().withDaysAvailable("monday", "tuesday").build()));

        // Only one matching keyword
        predicate = new PersonAvailableOnDayPredicate(TestUtil.stringsToDays(Arrays.asList("monday", "tuesday")));
        assertTrue(predicate.test(new PersonBuilder().withDaysAvailable("tuesday", "wednesday").build()));

    }

    @Test
    public void test_availabilityDoesNotContainKeywords_returnsFalse() throws Exception {
        // Zero keywords
        PersonAvailableOnDayPredicate predicate = new PersonAvailableOnDayPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withDaysAvailable("monday").build()));

        // Non-matching keyword
        predicate = new PersonAvailableOnDayPredicate(TestUtil.stringsToDays(Arrays.asList("monday")));
        assertFalse(predicate.test(new PersonBuilder().withDaysAvailable("tuesday", "wednesday").build()));

    }

    @Test
    public void test_availabilityMatchAllKeywords() throws Exception {
        // One keyword
        PersonAvailableOnDayPredicate predicate =
                new PersonAvailableOnDayPredicate(TestUtil.stringsToDays(Collections.singletonList("monday")), true);
        assertTrue(predicate.test(new PersonBuilder().withDaysAvailable("monday").build()));

        // Multiple keywords
        predicate = new PersonAvailableOnDayPredicate(TestUtil.stringsToDays(Arrays.asList("monday", "tuesday")), true);
        assertTrue(predicate.test(new PersonBuilder().withDaysAvailable("monday", "tuesday").build()));

        // Only one matching keyword
        predicate = new PersonAvailableOnDayPredicate(TestUtil.stringsToDays(Arrays.asList("monday", "tuesday")), true);
        assertFalse(predicate.test(new PersonBuilder().withDaysAvailable("tuesday", "wednesday").build()));
    }

    @Test
    public void toStringMethod() throws Exception {
        List<Day> keywords = TestUtil.stringsToDays(List.of("monday", "tuesday"));
        boolean matchAll = true;
        PersonAvailableOnDayPredicate predicate = new PersonAvailableOnDayPredicate(keywords, matchAll);

        String expected = PersonAvailableOnDayPredicate.class.getCanonicalName()
                + "{keywords=" + keywords
                + ", matchAll=" + matchAll + "}";
        assertEquals(expected, predicate.toString());
    }
}
