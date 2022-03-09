package persistence;

import model.*;
import model.Assignment;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// NOTE: THIS CLASS HAS BEEN HEAVILY INSPIRED BY JsonSerializationDemo from
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// AND MANY OF THE METHODS IMPLEMENTED HERE ARE SIMILAR TO THE ONES IN THE DEMO
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFileAL() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AssignmentList al = reader.readAL();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNonExistentFileEL() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            EventList el = reader.readEL();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAssignmentList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAssignmentList.json");
        try {
            AssignmentList al = reader.readAL();
            assertEquals(0, al.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyEventList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyEventList.json");
        try {
            EventList el = reader.readEL();
            assertEquals(0, el.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderAssignmentList() {
        JsonReader reader = new JsonReader("./data/testReaderNotEmptyAssignmentList.json");
        try {
            AssignmentList al = reader.readAL();
            List<Assignment> listOfAssignments = al.getAssignments();
            assertEquals(2, listOfAssignments.size());

            Date pdt2 = new Date(2, 8, 2021);
            String pd2 = "project phase 3 deliverable";
            String pn = "Project Phase 3";

            chkAssignment(pn, pd2, pdt2, "CPSC 210", "incomplete", listOfAssignments.get(0));
            Date pdt = new Date(4, 8, 2021);
            String pd = "lab 8 assignment";
            chkAssignment("Lab 8", pd, pdt, "CPSC 210", "incomplete", listOfAssignments.get(1));
        } catch (Exception e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEventList() {
        JsonReader reader = new JsonReader("./data/testReaderNotEmptyEventList.json");
        try {
            EventList el = reader.readEL();
            List<Event> listOfEvents = el.getEvents();
            assertEquals(2, listOfEvents.size());

            Date pdt2 = new Date(31, 7, 2022);
            String pd2 = "trip to cultus lake";
            String pn = "Cultus Lake";
            chkEvent(pn, pd2, pdt2, 700, false, listOfEvents.get(0));
            Date pdt = new Date(3, 8, 2022);
            String pd = "trip to winnipeg";
            chkEvent("Winnipeg Trip", pd, pdt, 630, false, listOfEvents.get(1));
        } catch (Exception e) {
            fail("Couldn't read from file");
        }
    }
}
