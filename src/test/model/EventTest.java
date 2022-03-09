package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for methods in Event Class
public class EventTest {

    Event testEvent;
    Date firstDay;
    Date secondDay;
    Date testDay;

    @BeforeEach
    public void setup() {
        try {
            firstDay = new Date(1, 1, 2022);
            secondDay = new Date(2, 3, 2023);
            testDay = new Date(21, 7, 2022);
            testEvent = new Event("Event 1", "Test Event", firstDay, 1);
        } catch (Exception e) {
            // do nothing
        }
    }

    @Test
    public void initializeEventTest() {
        Event evenTest = new Event("Event 2", "Testing initializing event", secondDay, 2);

        assertEquals("Event 2", evenTest.getName());
        assertEquals("Testing initializing event", evenTest.getDescription());
        assertEquals(secondDay, evenTest.getDate());
        assertEquals(2, evenTest.getTime());
        assertFalse(evenTest.getStatus());
    }

    @Test
    public void completeEventTestIncomplete() {
        testEvent.completeEvent();

        assertTrue(testEvent.getStatus());
    }

    @Test
    public void completeEventTestComplete() {
        testEvent.completeEvent();
        testEvent.completeEvent();

        assertTrue(testEvent.getStatus());
    }

    @Test
    public void printEventTest() {
        testEvent = new Event("e1", "test e1", testDay, 1000);
        String intendedResult = "e1 || " + "test e1 || " + "21/7/2022 || " + "1000 ||";
        String result = testEvent.printEvent();
        assertEquals(intendedResult, result);
    }

    // FOLLOWING TESTS ARE TO GET 100% IN CODE COVERAGE

    @Test
    public void setDateTestBasic() {
        testEvent.setDate(secondDay);
        assertEquals(secondDay, testEvent.getDate());
    }

    @Test
    public void setTimeTestBasic() {
        testEvent.setTime(1000);
        assertEquals(1000, testEvent.getTime());
    }

    @Test
    public void setNameTestBasic() {
        testEvent.setName("e1");
        assertEquals("e1", testEvent.getName());
    }

    @Test
    public void setDescriptionTestBasic() {
        testEvent.setDescription("test e1");
        assertEquals("test e1", testEvent.getDescription());
    }

    @Test
    public void setStatusTestBasic() {
        testEvent.completeEvent();
        assertEquals(true, testEvent.getStatus());
    }
}
