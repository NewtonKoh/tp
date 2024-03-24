package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DaysTest {

    @Test
    public void isValidDay_validDay_true() {
        assertTrue(Days.isValidDay("monday"));
    }

    @Test
    public void isValidDay_invalidDay_false() {
        assertFalse(Days.isValidDay("day"));
    }

    @Test
    public void getDayTest_validDay_monday() {
        assertEquals(Days.getDay("monday"), Days.MONDAY);
    }

    @Test
    public void getDayTest_invalidDay_isNull() {
        assertNull(Days.getDay("noday"));
    }
}
