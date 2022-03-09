package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for methods in TaskManagerApp class
public class AssignmentListTest {

    AssignmentList testAssignmentList;
    Assignment assignment1;
    Assignment assignment2;
    Assignment assignment3;
    Assignment assignment4;
    Assignment assignment5;
    Date date1;
    Date date2;
    Date date3;
    Date date4;

    Date date5;
    Date date6;
    Date date7;
    Date date8;


    @BeforeEach
    public void setup() {
        testAssignmentList = new AssignmentList();

        try {
            date1 = new Date(21, 7, 2022);
            date2 = new Date(27, 7, 2022);
            date3 = new Date(31, 7, 2022);
            date4 = new Date(1, 8, 2022);

            date5 = new Date(19, 6, 2023);
            date6 = new Date(18, 7, 2023);
            date7 = new Date(18, 6, 2022);
            date8 = new Date(18, 6, 2023);
        } catch (Exception e) {
            // do nothing
        }


        assignment1 = new Assignment("a1", "test a1", date1, "CPSC 210");
        assignment2 = new Assignment("a2", "test a2", date1, "PHYS 131");
        assignment3 = new Assignment("a3", "test a3", date2, "CPSC 210");
        assignment4 = new Assignment("a4", "test a4", date3, "PHIL 101");
        assignment5 = new Assignment("a5", "test a5", date4, "CPSC 210");
    }

    @Test
    public void initializeAssignmentList() {
        AssignmentList result = new AssignmentList();
        assertTrue(result.isEmpty());
    }

    @Test
    public void addAssignmentsTestOne() {
        assertTrue(testAssignmentList.isEmpty());

        testAssignmentList.addAssignment(assignment1);
        assertFalse(testAssignmentList.isEmpty());
        assertEquals(assignment1, testAssignmentList.getAssignmentAtPosition(0));
        assertEquals(1, testAssignmentList.getSize());
    }

    @Test
    public void addAssignmentsTestMany() {
        assertTrue(testAssignmentList.isEmpty());

        testAssignmentList.addAssignment(assignment4);
        assertFalse(testAssignmentList.isEmpty());
        assertEquals(assignment4, testAssignmentList.getAssignmentAtPosition(0));
        assertEquals(1, testAssignmentList.getSize());

        testAssignmentList.addAssignment(assignment1);
        assertEquals(assignment1, testAssignmentList.getAssignmentAtPosition(0));
        assertEquals(assignment4, testAssignmentList.getAssignmentAtPosition(1));
        assertEquals(2, testAssignmentList.getSize());

        testAssignmentList.addAssignment(assignment3);
        assertEquals(assignment1, testAssignmentList.getAssignmentAtPosition(0));
        assertEquals(assignment3, testAssignmentList.getAssignmentAtPosition(1));
        assertEquals(assignment4, testAssignmentList.getAssignmentAtPosition(2));
        assertEquals(3, testAssignmentList.getSize());
    }

    @Test
    public void getAssignmentListForDayTestNone() {
        testAssignmentList.addAssignment(assignment1);
        testAssignmentList.addAssignment(assignment2);
        testAssignmentList.addAssignment(assignment3);

        AssignmentList result = testAssignmentList.getAssignmentListForDay(22, 7, 2022);

        assertTrue(result.isEmpty());
        assertFalse(result.containsAssignment(assignment1));
        assertFalse(result.containsAssignment(assignment2));
        assertFalse(result.containsAssignment(assignment3));
    }

    @Test
    public void getAssignmentListForDayTestFailThreeFold() {
        assignment1.setDeadline(date5);
        assignment2.setDeadline(date6);
        assignment3.setDeadline(date7);
        assignment4.setDeadline(date8);
        testAssignmentList.addAssignment(assignment1);
        testAssignmentList.addAssignment(assignment2);
        testAssignmentList.addAssignment(assignment3);
        testAssignmentList.addAssignment(assignment4);

        AssignmentList result = testAssignmentList.getAssignmentListForDay(18, 6, 2023);

        assertFalse(result.isEmpty());
        assertFalse(result.containsAssignment(assignment1));
        assertFalse(result.containsAssignment(assignment2));
        assertFalse(result.containsAssignment(assignment3));
        assertTrue(result.containsAssignment(assignment4));
    }


    @Test
    public void getAssignmentListForDayTestOne() {
        testAssignmentList.addAssignment(assignment1);
        testAssignmentList.addAssignment(assignment3);
        testAssignmentList.addAssignment(assignment4);

        AssignmentList result = testAssignmentList.getAssignmentListForDay(21, 7, 2022);

        assertFalse(result.isEmpty());
        assertTrue(result.containsAssignment(assignment1));
        assertFalse(result.containsAssignment(assignment3));
        assertFalse(result.containsAssignment(assignment4));
    }


    @Test
    public void getAssignmentListForDayTestMultiple() {
        testAssignmentList.addAssignment(assignment1);
        testAssignmentList.addAssignment(assignment2);
        testAssignmentList.addAssignment(assignment3);

        AssignmentList result = testAssignmentList.getAssignmentListForDay(21, 7, 2022);

        assertFalse(result.isEmpty());
        assertTrue(result.containsAssignment(assignment1));
        assertTrue(result.containsAssignment(assignment2));
        assertFalse(result.containsAssignment(assignment3));
    }


    @Test
    public void getAssignmentListForWeekTestNone() {
        testAssignmentList.addAssignment(assignment1);
        testAssignmentList.addAssignment(assignment2);
        testAssignmentList.addAssignment(assignment3);

        AssignmentList result = testAssignmentList.getAssignmentListForWeek(10, 7, 2022);

        assertTrue(result.isEmpty());
        assertFalse(result.containsAssignment(assignment1));
        assertFalse(result.containsAssignment(assignment2));
        assertFalse(result.containsAssignment(assignment3));
    }

    @Test
    public void getAssignmentListForWeekTestFailThreeFold() {
        date5.setDay(26);
        assignment1.setDeadline(date5);
        assignment2.setDeadline(date6);
        assignment3.setDeadline(date7);
        assignment4.setDeadline(date8);
        testAssignmentList.addAssignment(assignment1);
        testAssignmentList.addAssignment(assignment2);
        testAssignmentList.addAssignment(assignment3);
        testAssignmentList.addAssignment(assignment4);

        AssignmentList result = testAssignmentList.getAssignmentListForWeek(18, 6, 2023);

        assertFalse(result.isEmpty());
        assertFalse(result.containsAssignment(assignment1));
        assertFalse(result.containsAssignment(assignment2));
        assertFalse(result.containsAssignment(assignment3));
        assertTrue(result.containsAssignment(assignment4));
    }

    @Test
    public void getAssignmentListForWeekTestOne() {
        testAssignmentList.addAssignment(assignment1);
        testAssignmentList.addAssignment(assignment4);
        testAssignmentList.addAssignment(assignment5);

        AssignmentList result = testAssignmentList.getAssignmentListForWeek(21, 7, 2022);

        assertFalse(result.isEmpty());
        assertTrue(result.containsAssignment(assignment1));
        assertFalse(result.containsAssignment(assignment4));
        assertFalse(result.containsAssignment(assignment5));
    }

    @Test
    public void getAssignmentListForWeekTestMultiple() {
        testAssignmentList.addAssignment(assignment1);
        testAssignmentList.addAssignment(assignment2);
        testAssignmentList.addAssignment(assignment3);
        testAssignmentList.addAssignment(assignment4);
        testAssignmentList.addAssignment(assignment5);

        AssignmentList result = testAssignmentList.getAssignmentListForWeek(21, 7, 2022);

        assertFalse(result.isEmpty());
        assertTrue(result.containsAssignment(assignment1));
        assertTrue(result.containsAssignment(assignment2));
        assertTrue(result.containsAssignment(assignment3));
        assertFalse(result.containsAssignment(assignment4));
        assertFalse(result.containsAssignment(assignment5));
    }

    @Test
    public void getAssignmentListForMonthTestNone() {
        testAssignmentList.addAssignment(assignment1);
        testAssignmentList.addAssignment(assignment2);
        testAssignmentList.addAssignment(assignment3);

        AssignmentList result = testAssignmentList.getAssignmentListForMonth(6, 2022);

        assertTrue(result.isEmpty());
        assertFalse(result.containsAssignment(assignment1));
        assertFalse(result.containsAssignment(assignment2));
        assertFalse(result.containsAssignment(assignment3));
    }

    @Test
    public void getAssignmentListForMonthTestFailThreeFold() {
        assignment2.setDeadline(date6);
        assignment3.setDeadline(date7);
        assignment4.setDeadline(date8);
        testAssignmentList.addAssignment(assignment2);
        testAssignmentList.addAssignment(assignment3);
        testAssignmentList.addAssignment(assignment4);

        AssignmentList result = testAssignmentList.getAssignmentListForMonth(6, 2023);

        assertFalse(result.isEmpty());
        assertFalse(result.containsAssignment(assignment2));
        assertFalse(result.containsAssignment(assignment3));
        assertTrue(result.containsAssignment(assignment4));
    }

    @Test
    public void getAssignmentListForMonthTestOne() {
        testAssignmentList.addAssignment(assignment1);
        testAssignmentList.addAssignment(assignment2);
        testAssignmentList.addAssignment(assignment5);

        AssignmentList result = testAssignmentList.getAssignmentListForMonth(8, 2022);

        assertFalse(result.isEmpty());
        assertFalse(result.containsAssignment(assignment1));
        assertFalse(result.containsAssignment(assignment2));
        assertTrue(result.containsAssignment(assignment5));
    }

    @Test
    public void getAssignmentListForMonthTestMultiple() {
        testAssignmentList.addAssignment(assignment1);
        testAssignmentList.addAssignment(assignment2);
        testAssignmentList.addAssignment(assignment3);
        testAssignmentList.addAssignment(assignment4);
        testAssignmentList.addAssignment(assignment5);

        AssignmentList result = testAssignmentList.getAssignmentListForMonth(7, 2022);

        assertFalse(result.isEmpty());
        assertTrue(result.containsAssignment(assignment1));
        assertTrue(result.containsAssignment(assignment2));
        assertTrue(result.containsAssignment(assignment3));
        assertTrue(result.containsAssignment(assignment4));
        assertFalse(result.containsAssignment(assignment5));
    }

    @Test
    public void viewAssignmentListTestEmpty() {
        assertTrue(testAssignmentList.isEmpty());
        String result = testAssignmentList.viewAssignmentList();
        assertEquals("No assignments left!", result);
    }

    @Test
    public void viewAssignmentListTestOne() {
        testAssignmentList.addAssignment(assignment1);
        String result = testAssignmentList.viewAssignmentList();
        String intendedResult = "\nTask 1: ";
        intendedResult = intendedResult + "a1 || " + "test a1 || " + "21/7/2022 || " + "incomplete || " + "CPSC 210 ||";
        assertEquals(intendedResult, result);
    }

    @Test
    public void viewAssignmentListTestMany() {
        testAssignmentList.addAssignment(assignment1);
        testAssignmentList.addAssignment(assignment2);
        testAssignmentList.addAssignment(assignment3);
        String result = testAssignmentList.viewAssignmentList();
        String it;
        it = "\nTask 1: " + "a1 || " + "test a1 || " + "21/7/2022 || " + "incomplete || " + "CPSC 210 ||";
        it = it + "\nTask 2: " + "a2 || " + "test a2 || " + "21/7/2022 || " + "incomplete || " + "PHYS 131 ||";
        it = it + "\nTask 3: " + "a3 || " + "test a3 || " + "27/7/2022 || " + "incomplete || " + "CPSC 210 ||";
        assertEquals(it, result);
    }

    @Test
    public void updateAssignmentListTestNone() {
        testAssignmentList.addAssignment(assignment1);
        testAssignmentList.addAssignment(assignment2);
        testAssignmentList.addAssignment(assignment3);

        testAssignmentList.updateAssignmentList();

        assertTrue(testAssignmentList.containsAssignment(assignment1));
        assertTrue(testAssignmentList.containsAssignment(assignment2));
        assertTrue(testAssignmentList.containsAssignment(assignment3));
        assertEquals(3, testAssignmentList.getSize());
    }

    @Test
    public void updateAssignmentListTestMultiple() {

        testAssignmentList.addAssignment(assignment1);
        testAssignmentList.addAssignment(assignment2);
        testAssignmentList.addAssignment(assignment3);
        testAssignmentList.addAssignment(assignment4);
        testAssignmentList.addAssignment(assignment5);

        assignment1.setStatus("complete");
        assignment3.setStatus("complete");
        assignment4.setStatus("overdue");
        assignment5.setStatus("complete");

        testAssignmentList.updateAssignmentList();

        assertFalse(testAssignmentList.containsAssignment(assignment1));
        assertTrue(testAssignmentList.containsAssignment(assignment2));
        assertFalse(testAssignmentList.containsAssignment(assignment3));
        assertTrue(testAssignmentList.containsAssignment(assignment4));
        assertFalse(testAssignmentList.containsAssignment(assignment5));
        assertEquals(2, testAssignmentList.getSize());
    }

}
