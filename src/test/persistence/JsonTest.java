package persistence;

import model.Assignment;
import model.Date;
import model.Event;

import static org.junit.jupiter.api.Assertions.assertEquals;

// NOTE: THIS CLASS HAS BEEN HEAVILY INSPIRED BY JsonSerializationDemo from
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// AND MANY OF THE METHODS IMPLEMENTED HERE ARE SIMILAR TO THE ONES IN THE DEMO

public class JsonTest {
    protected void chkAssignment(String name, String description, Date date, String type, String status, Assignment a) {
        assertEquals(name, a.getName());
        assertEquals(description, a.getDescription());
        assertEquals(date.getDay(), a.getDeadline().getDay());
        assertEquals(date.getMonth(), a.getDeadline().getMonth());
        assertEquals(date.getYear(), a.getDeadline().getYear());
        assertEquals(type, a.getType());
        assertEquals(status, a.getStatus());
    }

    protected void chkEvent(String name, String description, Date date, int time, boolean status, Event e) {
        assertEquals(name, e.getName());
        assertEquals(description, e.getDescription());
        assertEquals(date.getDay(), e.getDate().getDay());
        assertEquals(date.getMonth(), e.getDate().getMonth());
        assertEquals(date.getYear(), e.getDate().getYear());
        assertEquals(time, e.getTime());
        assertEquals(status, e.getStatus());
    }
}

// to make more elegant -> hashcode