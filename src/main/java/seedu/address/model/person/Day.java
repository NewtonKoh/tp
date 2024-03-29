package seedu.address.model.person;

import java.util.Arrays;

/**
 * Days enumeration models the 7 days of the week within FriendFolio.
 * Since days of the week are distinct, a set of available days is
 * easily represented by a HashSet containing some subset of all possible
 * Days.
 */
public enum Days {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;

    /**
     * Checks whether a String input matches the name of one of the days defined in the
     * Days enum. Matching is not case-sensitive. This method should always be used before
     * the getDay function to avoid null-handling requirements. isValidDay is decoupled from
     * getDay only in tests.
     * @param day
     * @return boolean representing if a match is found.
     */
    public static final String MESSAGE_CONSTRAINTS = "Please enter a valid day of the week from the following: "
            + getAllDaysAsString();

    public static boolean isValidDay(String day) {
        return Arrays.stream(Days.values()).anyMatch(x -> x.toString().equalsIgnoreCase(day));
    }

    /**
     * Maps a String input to one of the days defined in the Days enum. Matching is not
     * case-sensitive. While the output is null if no match, isValidDay method should
     * be used to check if the string is valid first. getDay is decoupled from isValidDay
     * only in tests.
     * @param day
     * @return Day whose name matches the String argument passed, or null if no match is found
     */
    public static Days getDay(String day) {
        return Arrays.stream(Days.values())
                .filter(x -> x.toString().equalsIgnoreCase(day)).reduce((x, y) -> x)
                .orElse(null);
    }

    /**
     * Helper function to return string containing all days in Days enum.
     * @return String containing all days' names in Days enum, split by comma.
     */
    private static String getAllDaysAsString() {
        StringBuilder s = new StringBuilder();
        for (Days d: Days.values()) {
            s.append(d).append(", ");
        }
        return s.toString().substring(0, s.length() - 2);
    }

    public String getShortForm() {
        return this.name().substring(0, 3);
    }
}
