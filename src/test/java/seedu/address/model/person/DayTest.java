package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DayTest {

    @Test
    public void isValidDay_validDay_true() {
        assertTrue(Day.isValidDay("monday"));
    }

    @Test
    public void isValidDay_invalidDay_false() {
        assertFalse(Day.isValidDay("day"));
    }

    @Test
    public void getDayTest_validDay_monday() {
        assertEquals(Day.getDay("monday"), Day.MONDAY);
    }

    @Test
    public void getDayTest_invalidDay_isNull() {
        assertNull(Day.getDay("noday"));
    }
}
