package model;

import model.exceptions.InvalidDateException;
import model.exceptions.TooEarlyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class DateTest {

    Date testDate;
    Date testDate2;
    Date testDate3;
    Date testDate4;
    Date testDate5;
    Date testDate6;
    Date testDate7;
    Date testDate8;
    int beforeYear = 2022;
    int firstYear = 2023;
    int secondYear = 2024;

    @BeforeEach
    public void setup() {
        try {
            testDate = new Date(4, 4, firstYear);
            testDate2 = new Date(4, 4, secondYear);
            testDate3 = new Date(4, 4, beforeYear);
            testDate4 = new Date(4, 5, firstYear);
            testDate5 = new Date(4, 3, firstYear);
            testDate6 = new Date(5, 4, firstYear);
            testDate7 = new Date(3, 4, firstYear);
            testDate8 = new Date(4, 4, firstYear);
        } catch (Exception e) {
            // do nothing
        }
    }

    @Test
    public void initializeDateTestValid() {
        try {
            testDate = new Date(30, 9, firstYear);
        } catch (InvalidDateException e) {
            fail("Threw an invalid date exception when it was valid");
        } catch (TooEarlyException e2) {
            fail("Threw a too early exception when it was valid");
        }

        assertEquals(30, testDate.getDay());
        assertEquals(9, testDate.getMonth());
        assertEquals(firstYear, testDate.getYear());
    }

    @Test
    public void initializeDateTestInvalidDayE() {
        try {
            testDate = new Date(0, 9, firstYear);
            fail("Didn't throw invalid date exception");
        } catch (InvalidDateException e) {
            // all good
        } catch (TooEarlyException e2) {
            fail("Threw a too early exception when it was invalid day");
        }
    }

    @Test
    public void initializeDateTestInvalidDayL() {
        try {
            testDate = new Date(32, 9, firstYear);
            fail("Didn't throw invalid date exception");
        } catch (InvalidDateException e) {
            // all good
        } catch (TooEarlyException e2) {
            fail("Threw a too early exception when it was invalid day");
        }
    }

    @Test
    public void initializeDateTestInvalidMonthE() {
        try {
            testDate = new Date(20, 0, firstYear);
            fail("Didn't throw invalid date exception");
        } catch (InvalidDateException e) {
            // all good
        } catch (TooEarlyException e2) {
            fail("Threw a too early exception when it was invalid day");
        }
    }

    @Test
    public void initializeDateTestInvalidMonthL() {
        try {
            testDate = new Date(20, 13, firstYear);
            fail("Didn't throw invalid date exception");
        } catch (InvalidDateException e) {
            // all good
        } catch (TooEarlyException e2) {
            fail("Threw a too early exception when it was invalid day");
        }
    }

    @Test
    public void initializeDateTestTooEarlyYear() {
        try {
            testDate = new Date(20, 9, 2020);
            fail("Didn't throw too early exception");
        } catch (InvalidDateException e) {
            fail("Threw an invalid date exception when it was too early date");
        } catch (TooEarlyException e2) {
            // all good;
        }
    }

    @Test
    public void initializeDateTestTooEarlyMonth() {
        try {
            testDate = new Date(31, 7, 2021);
            fail("Didn't throw too early exception");
        } catch (InvalidDateException e) {
            fail("Threw an invalid date exception when it was too early date");
        } catch (TooEarlyException e2) {
            // all good;
        }
    }

    @Test
    public void isEarlierTestEarlierYear() {
        boolean result = testDate.isEarlier(testDate2);
        assertTrue(result);
    }

    @Test
    public void isEarlierTestLateYear() {
        boolean result = testDate.isEarlier(testDate3);
        assertFalse(result);
    }

    @Test
    public void isEarlierTestEarlierMonth() {
        boolean result = testDate.isEarlier(testDate4);
        assertTrue(result);
    }

    @Test
    public void isEarlierTestLaterMonth() {
        boolean result = testDate.isEarlier(testDate5);
        assertFalse(result);
    }

    @Test
    public void isEarlierTestEarlierDay() {
        boolean result = testDate.isEarlier(testDate6);
        assertTrue(result);
    }

    @Test
    public void isEarlierTestLaterDay() {
        boolean result = testDate.isEarlier(testDate7);
        assertFalse(result);
    }

    @Test
    public void isEarlierTestSameDate() {
        boolean result = testDate.isEarlier(testDate8);
        assertFalse(result);
    }

    @Test
    public void isSameDateTestTrue() {
        boolean result = testDate.isSameDay(testDate2);
        assertFalse(result);
    }

    @Test
    public void isSameDateTestFalse() {
        boolean result = testDate.isSameDay(testDate8);
        assertTrue(result);
    }

    // FOLLOWING TESTS ARE TO GET 100% in code coverage

    @Test
    public void setDayTestBasic() {
        testDate.setDay(2);

        assertEquals(2, testDate.getDay());
        assertEquals(4, testDate.getMonth());
        assertEquals(firstYear, testDate.getYear());
    }

    @Test
    public void setMonthTestBasic() {
        testDate.setMonth(1);

        assertEquals(4, testDate.getDay());
        assertEquals(1, testDate.getMonth());
        assertEquals(firstYear, testDate.getYear());
    }

    @Test
    public void setYearTestBasic() {
        testDate.setYear(secondYear);

        assertEquals(4, testDate.getDay());
        assertEquals(4, testDate.getMonth());
        assertEquals(secondYear, testDate.getYear());
    }
}
