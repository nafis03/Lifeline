package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for EventList Class
public class EventListTest {

    EventList testEventList;
    Event event1;
    Event event2;
    Event event3;
    Event event4;
    Event event5;
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
        testEventList = new EventList();

        try {
            date1 = new Date(21, 7, 2022);
            date2 = new Date(27, 7, 2022);
            date3 = new Date(31, 7, 2022);
            date4 = new Date(1, 8, 2022);

            date5 = new Date(19, 6, 2023);
            date6 = new Date(18, 7, 2023);
            date7 = new Date(18, 6, 2022);
            date8 = new Date(18, 6, 2023);

            event1 = new Event("e1", "test e1", date1, 1000);
            event2 = new Event("e2", "test e2", date1, 1200);
            event3 = new Event("e3", "test e3", date2, 1000);
            event4 = new Event("e4", "test e4", date3, 1300);
            event5 = new Event("e5", "test e5", date4, 1700);
        } catch (Exception e) {
            // do nothing
        }
    }

    @Test
    public void initializeEventList() {
        EventList result = new EventList();
        assertTrue(result.isEmpty());
    }

    @Test
    public void addEventTestSingle() {
        assertTrue(testEventList.isEmpty());

        testEventList.addEvent(event1);
        assertFalse(testEventList.isEmpty());
        assertTrue(testEventList.containsEvent(event1));
        assertEquals(1, testEventList.getSize());
    }

    @Test
    public void addEventTestMultiple() {
        testEventList.addEvent(event2);
        assertFalse(testEventList.isEmpty());
        assertEquals(event2, testEventList.getEventAtPosition(0));
        assertEquals(1, testEventList.getSize());

        testEventList.addEvent(event4);
        assertEquals(event2, testEventList.getEventAtPosition(0));
        assertEquals(event4, testEventList.getEventAtPosition(1));
        assertEquals(2, testEventList.getSize());

        testEventList.addEvent(event3);
        assertEquals(event2, testEventList.getEventAtPosition(0));
        assertEquals(event3, testEventList.getEventAtPosition(1));
        assertEquals(event4, testEventList.getEventAtPosition(2));
        assertEquals(3, testEventList.getSize());

        testEventList.addEvent(event1);
        assertEquals(event1, testEventList.getEventAtPosition(0));
        assertEquals(event2, testEventList.getEventAtPosition(1));
        assertEquals(event3, testEventList.getEventAtPosition(2));
        assertEquals(event4, testEventList.getEventAtPosition(3));
        assertEquals(4, testEventList.getSize());
    }

    @Test
    public void getEventListForDayTestNone() {
        testEventList.addEvent(event1);
        testEventList.addEvent(event2);
        testEventList.addEvent(event3);

        EventList result = testEventList.getEventListForDay(22, 7, 2022);

        assertTrue(result.isEmpty());
        assertFalse(result.containsEvent(event1));
        assertFalse(result.containsEvent(event2));
        assertFalse(result.containsEvent(event3));
    }

    @Test
    public void getAssignmentListForDayTestFailThreeFold() {
        event1.setDate(date5);
        event2.setDate(date6);
        event3.setDate(date7);
        event4.setDate(date8);
        testEventList.addEvent(event1);
        testEventList.addEvent(event2);
        testEventList.addEvent(event3);
        testEventList.addEvent(event4);

        EventList result = testEventList.getEventListForDay(18, 6, 2023);

        assertFalse(result.isEmpty());
        assertFalse(result.containsEvent(event1));
        assertFalse(result.containsEvent(event2));
        assertFalse(result.containsEvent(event3));
        assertTrue(result.containsEvent(event4));
    }

    @Test
    public void getEventListForDayTestOne() {
        testEventList.addEvent(event1);
        testEventList.addEvent(event3);
        testEventList.addEvent(event4);

        EventList result = testEventList.getEventListForDay(21, 7, 2022);

        assertFalse(result.isEmpty());
        assertTrue(result.containsEvent(event1));
        assertFalse(result.containsEvent(event4));
        assertFalse(result.containsEvent(event3));
    }

    @Test
    public void getEventListForDayTestMultiple() {
        testEventList.addEvent(event1);
        testEventList.addEvent(event2);
        testEventList.addEvent(event3);

        EventList result = testEventList.getEventListForDay(21, 7, 2022);

        assertFalse(result.isEmpty());
        assertTrue(result.containsEvent(event1));
        assertTrue(result.containsEvent(event2));
        assertFalse(result.containsEvent(event3));
    }

    @Test
    public void getEventListForWeekTestNone() {
        testEventList.addEvent(event1);
        testEventList.addEvent(event2);
        testEventList.addEvent(event3);

        EventList result = testEventList.getEventListForWeek(10, 7, 2022);

        assertTrue(result.isEmpty());
        assertFalse(result.containsEvent(event1));
        assertFalse(result.containsEvent(event2));
        assertFalse(result.containsEvent(event3));
    }

    @Test
    public void getAssignmentListForWeekTestFailThreeFold() {
        date5.setDay(26);
        event1.setDate(date5);
        event2.setDate(date6);
        event3.setDate(date7);
        event4.setDate(date8);
        testEventList.addEvent(event1);
        testEventList.addEvent(event2);
        testEventList.addEvent(event3);
        testEventList.addEvent(event4);

        EventList result = testEventList.getEventListForDay(18, 6, 2023);

        assertFalse(result.isEmpty());
        assertFalse(result.containsEvent(event1));
        assertFalse(result.containsEvent(event2));
        assertFalse(result.containsEvent(event3));
        assertTrue(result.containsEvent(event4));
    }

    @Test
    public void getEventListForWeekTestOne() {
        testEventList.addEvent(event1);
        testEventList.addEvent(event3);
        testEventList.addEvent(event4);

        EventList result = testEventList.getEventListForWeek(20, 7, 2022);

        assertFalse(result.isEmpty());
        assertTrue(result.containsEvent(event1));
        assertFalse(result.containsEvent(event4));
        assertFalse(result.containsEvent(event3));
    }

    @Test
    public void getEventListForWeekTestMultiple() {
        testEventList.addEvent(event1);
        testEventList.addEvent(event2);
        testEventList.addEvent(event3);
        testEventList.addEvent(event4);
        testEventList.addEvent(event5);

        EventList result = testEventList.getEventListForWeek(21, 7, 2022);

        assertFalse(result.isEmpty());
        assertTrue(result.containsEvent(event1));
        assertTrue(result.containsEvent(event2));
        assertTrue(result.containsEvent(event3));
        assertFalse(result.containsEvent(event4));
        assertFalse(result.containsEvent(event5));
    }

    @Test
    public void getEventListForMonthTestNone() {
        testEventList.addEvent(event1);
        testEventList.addEvent(event2);
        testEventList.addEvent(event3);

        EventList result = testEventList.getEventListForMonth(8, 2022);

        assertTrue(result.isEmpty());
        assertFalse(result.containsEvent(event1));
        assertFalse(result.containsEvent(event2));
        assertFalse(result.containsEvent(event3));
    }

    @Test
    public void getAssignmentListForMonthTestFailThreeFold() {
        event2.setDate(date6);
        event3.setDate(date7);
        event4.setDate(date8);
        testEventList.addEvent(event2);
        testEventList.addEvent(event3);
        testEventList.addEvent(event4);

        EventList result = testEventList.getEventListForMonth(6, 2023);

        assertFalse(result.isEmpty());
        assertFalse(result.containsEvent(event2));
        assertFalse(result.containsEvent(event3));
        assertTrue(result.containsEvent(event4));
    }

    @Test
    public void getEventListForMonthTestOne() {
        testEventList.addEvent(event1);
        testEventList.addEvent(event2);
        testEventList.addEvent(event3);
        testEventList.addEvent(event5);

        EventList result = testEventList.getEventListForMonth(8, 2022);

        assertFalse(result.isEmpty());
        assertFalse(result.containsEvent(event1));
        assertFalse(result.containsEvent(event2));
        assertFalse(result.containsEvent(event3));
        assertTrue(result.containsEvent((event5)));
    }

    @Test
    public void getEventListForMonthTestMany() {
        testEventList.addEvent(event1);
        testEventList.addEvent(event2);
        testEventList.addEvent(event3);
        testEventList.addEvent(event4);
        testEventList.addEvent(event5);

        EventList result = testEventList.getEventListForMonth(7, 2022);

        assertFalse(result.isEmpty());
        assertTrue(result.containsEvent(event1));
        assertTrue(result.containsEvent(event2));
        assertTrue(result.containsEvent(event3));
        assertTrue(result.containsEvent(event4));
        assertFalse(result.containsEvent((event5)));
    }

    @Test
    public void viewEventListTestNone() {
        assertTrue(testEventList.isEmpty());
        String result = testEventList.viewEventList();
        assertEquals("No upcoming events.", result);
    }

    @Test
    public void viewEventListTestOne() {
        testEventList.addEvent(event1);
        String result = testEventList.viewEventList();
        String intendedResult = "\nEvent 1: " + "e1 || " + "test e1 || " + "21/7/2022 || " + "1000 ||";
        assertEquals(intendedResult, result);
    }

    @Test
    public void viewEventListTestMultiple() {
        testEventList.addEvent(event1);
        testEventList.addEvent(event2);
        testEventList.addEvent(event3);

        String result = testEventList.viewEventList();

        String intendedResult = "\nEvent 1: " + "e1 || " + "test e1 || " + "21/7/2022 || " + "1000 ||";
        intendedResult = intendedResult + "\nEvent 2: " + "e2 || " + "test e2 || " + "21/7/2022 || " + "1200 ||";
        intendedResult = intendedResult + "\nEvent 3: " + "e3 || " + "test e3 || " + "27/7/2022 || " + "1000 ||";
        assertEquals(intendedResult, result);
    }

    @Test
    public void updateEventListNone() {
        testEventList.addEvent(event1);
        testEventList.addEvent(event2);
        testEventList.addEvent(event3);

        testEventList.updateEventList();

        assertFalse(testEventList.isEmpty());
        assertTrue(testEventList.containsEvent(event1));
        assertTrue(testEventList.containsEvent(event2));
        assertTrue(testEventList.containsEvent(event3));
        assertEquals(3, testEventList.getSize());
    }

    @Test
    public void updateEventListMultiple() {

        testEventList.addEvent(event1);
        testEventList.addEvent(event2);
        testEventList.addEvent(event3);

        event1.completeEvent();
        event3.completeEvent();

        testEventList.updateEventList();

        assertFalse(testEventList.isEmpty());
        assertFalse(testEventList.containsEvent(event1));
        assertTrue(testEventList.containsEvent(event2));
        assertFalse(testEventList.containsEvent(event3));
        assertEquals(1, testEventList.getSize());
    }


}
