package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFileAL() {
        try {
            AssignmentList al = new AssignmentList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterInvalidFileEL() {
        try {
            EventList el = new EventList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAssignmentList() {
        try {
            AssignmentList al = new AssignmentList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAssignmentList.json");
            writer.open();
            writer.writeAssignmentList(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAssignmentList.json");
            al = reader.readAL();
            assertEquals(0, al.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyEventList() {
        try {
            EventList el = new EventList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyEventList.json");
            writer.open();
            writer.writeEventList(el);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyEventList.json");
            el = reader.readEL();
            assertEquals(0, el.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterAssignmentList() {
        try {
            AssignmentList al = new AssignmentList();
            String n1 = "Project Phase 3";
            String des1 = "project phase 3 deliverable";
            Date d1 = new Date(2, 8, 2022);
            Assignment a1 = new Assignment(n1, des1, d1, "CPSC 210");
            al.addAssignment(a1);

            String n2 = "Lab 8";
            String des2 = "lab 8 assignment";
            Date d2 = new Date(4, 8, 2022);
            Assignment a2 = new Assignment(n2, des2, d2, "CPSC 210");
            al.addAssignment(a2);

            JsonWriter writer = new JsonWriter("./data/testWriterNotEmptyAssignmentList.json");
            writer.open();
            writer.writeAssignmentList(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNotEmptyAssignmentList.json");
            al = reader.readAL();
            List<Assignment> listOfAssignments = al.getAssignments();
            assertEquals(2, listOfAssignments.size());
            chkAssignment(n1, des1, d1, "CPSC 210", "incomplete", listOfAssignments.get(0));
            chkAssignment(n2, des2, d2, "CPSC 210", "incomplete", listOfAssignments.get(1));

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEventList() {
        try {
            EventList el = new EventList();
            String n1 = "Cultus Trip";
            String des1 = "trip to cultus lake";
            Date d1 = new Date(31, 7, 2022);
            Event e1 = new Event(n1, des1, d1, 700);
            el.addEvent(e1);

            String n2 = "Winnipeg Trip";
            String des2 = "trip to winnipeg";
            Date d2 = new Date(3, 8, 2022);
            Event e2 = new Event(n2, des2, d2, 630);
            el.addEvent(e2);

            JsonWriter writer = new JsonWriter("./data/testWriterNotEmptyEventList.json");
            writer.open();
            writer.writeEventList(el);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNotEmptyEventList.json");
            el = reader.readEL();
            List<Event> listOfEvents = el.getEvents();
            assertEquals(2, listOfEvents.size());
            chkEvent(n1, des1, d1, 700, false, listOfEvents.get(0));
            chkEvent(n2, des2, d2, 630, false, listOfEvents.get(1));

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }
}


