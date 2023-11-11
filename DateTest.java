package banking;

import static org.junit.Assert.*;

/**
 * Tests the Date class
 * @author Jeeva Ramasamy, Parth Patel
 */
public class DateTest {

    /**
     * Test case #1
     * Tests if date is in a non-leap year February
     */
    @org.junit.Test
    public void testFebDays_NonLeap() {
        Date date = new Date(2021, 2, 29);
        assertFalse(date.isValid());
    }

    /**
     * Test case #2
     * Tests if date is in a leap year February
     */
    @org.junit.Test
    public void testFebDays_Leap() {
        Date date = new Date(2020, 2, 29);
        assertTrue(date.isValid());
    }

    /**
     * Test case #3
     * Tests if date's month is not before January
     */
    @org.junit.Test
    public void testMonth_BelowRange() {
        Date date = new Date(2023, -1, 4);
        assertFalse(date.isValid());
    }

    /**
     * Test case #4
     * Tests if date's month is not past December
     */
    @org.junit.Test
    public void testMonth_AboveRange() {
        Date date = new Date(2023, 13, 5);
        boolean expectedOutput = false, actualOutput = date.isValid();
        assertFalse(date.isValid());
    }

    /**
     * Test case #5
     * Tests if the date's day is not before the 1st valid day (1)
     */
    @org.junit.Test
    public void testDays_BelowRange() {
        Date date = new Date(2023, 2, 0);
        boolean expectedOutput = false, actualOutput = date.isValid();
        assertFalse(date.isValid());
    }

    /**
     * Test case #6
     * Tests if the date's day is not after the
     * last valid day in January (31)
     */
    @org.junit.Test
    public void testDaysInJan_AboveRange() {
        // Same case for January, March, May, July, August, October, December
        Date date = new Date(2022, 1, 32);
        boolean expectedOutput = false, actualOutput = date.isValid();
        assertFalse(date.isValid());
    }

    /**
     * Test case #7
     * Tests if the date's day is not after the last valid day in April (30)
     */
    @org.junit.Test
    public void testDaysInApr_AboveRange() {
        // Same case for April, June, September, November
        Date date = new Date(2021, 4, 31);
        assertFalse(date.isValid());
    }

    /**
     * Test case #8
     * Tests if the date's day is within the range of
     * valid days in June (1-30)
     */
    @org.junit.Test
    public void testDaysInJune_WithinRange() {
        // Same case for April, June, September, November
        Date date = new Date(2020, 6, 19);
        assertTrue(date.isValid());
    }
}