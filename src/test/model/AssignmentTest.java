package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for methods in Assignment class
class AssignmentTest {

    Assignment testAssignment;
    Date firstDay;
    Date testDay;
    Assignment testAssignment2;

    @BeforeEach
    public void setup() {
        try {
            firstDay = new Date(1, 1, 2022);
            testDay = new Date(21, 7, 2022);
        } catch (Exception e) {
            // do nothing
        }
        testAssignment2 = new Assignment("Assignment 2", "test", firstDay, "CPSC 210");
    }

    @Test
    public void initializeAssignmentTest() {
        testAssignment = new Assignment("Assignment 1", "Test Assignment", firstDay, "CPSC 210");

        assertEquals("Assignment 1", testAssignment.getName());
        assertEquals("Test Assignment", testAssignment.getDescription());
        assertEquals(firstDay, testAssignment.getDeadline());
        assertEquals("CPSC 210", testAssignment.getType());
        assertEquals("incomplete", testAssignment.getStatus());
    }

    @Test
    public void printAssignmentTest() {
        testAssignment = new Assignment("a1", "test a1", testDay, "CPSC 210");
        String intendedResult = "a1 || " + "test a1 || " + "21/7/2022 || " + "incomplete || " + "CPSC 210 ||";
        String result = testAssignment.printAssignment();
        assertEquals(intendedResult, result);
    }

    // FOLLOWING TESTS ARE TO GET 100% in code coverage

    @Test
    public void setNameTestBasic() {
        testAssignment2.setName("a1");
        assertEquals("a1", testAssignment2.getName());
    }

    @Test
    public void setDescriptionTestBasic() {
        testAssignment2.setDescription("test a1");
        assertEquals("test a1", testAssignment2.getDescription());
    }

    @Test
    public void setDeadlineTestBasic() {
        testAssignment2.setDeadline(testDay);
        assertEquals(testDay, testAssignment2.getDeadline());
    }

    @Test
    public void setTypeTestBasic() {
        testAssignment2.setType("CPSC 121");
        assertEquals("CPSC 121", testAssignment2.getType());
    }

    @Test
    public void setStatusTestToOverdue() {
        testAssignment2.setStatus("overdue");
        assertEquals("overdue", testAssignment2.getStatus());
    }

    @Test
    public void setStatusTestToComplete() {
        testAssignment2.setStatus("complete");
        assertEquals("complete", testAssignment2.getStatus());
    }

}