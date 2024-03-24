package seedu.address.model.person;

import java.util.Arrays;

public enum Days {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;

    public static boolean isValidDay(String day) {
        return Arrays.stream(Days.values()).anyMatch(x -> x.toString().equalsIgnoreCase(day));
    }

    public static Days getDay(String day) {
        return Arrays.stream(Days.values())
                .filter(x -> x.toString().equalsIgnoreCase(day)).reduce((x,y) -> x)
                .orElse(null);
    }

    public String getShortForm() {
        return this.name().substring(0,3);
    }
    private static String getAllDaysAsString() {
        StringBuilder s = new StringBuilder();
        for (Days d: Days.values()) {
            s.append(d).append(", ");
        }
        return s.toString().trim();
    }

    public static final String MESSAGE_CONSTRAINTS = "Please enter a recognizable day: " + getAllDaysAsString();
}
