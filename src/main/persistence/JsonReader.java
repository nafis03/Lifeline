package persistence;

import model.EventList;
import model.AssignmentList;
import model.Event;
import model.Assignment;
import model.Date;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads EventList and AssignmentList from JSON data stored in file

// NOTE: THIS CLASS HAS BEEN HEAVILY INSPIRED BY JsonSerializationDemo from
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// AND MANY OF THE METHODS IMPLEMENTED HERE ARE SIMILAR TO THE ONES IN THE DEMO
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads AssignmentList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AssignmentList readAL() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAL(jsonObject);
    }

    // EFFECTS: reads EventList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public EventList readEL() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseEL(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses AL from JSON object and returns it
    private AssignmentList parseAL(JSONObject jsonObject) {
        AssignmentList al = new AssignmentList();
        addAssignments(al, jsonObject);
        return al;
    }

    // EFFECTS: parses EL from JSON object and returns it
    private EventList parseEL(JSONObject jsonObject) {
        EventList el = new EventList();
        addEvents(el, jsonObject);
        return el;
    }

    // MODIFIES: al
    // EFFECTS: parses assignments from JSON object and adds them to assignmentList
    private void addAssignments(AssignmentList al, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfAssignments");
        for (Object json : jsonArray) {
            JSONObject nextAssignment = (JSONObject) json;
            addAssignment(al, nextAssignment);
        }
    }

    // MODIFIES: el
    // EFFECTS: parses events from JSON object and adds them to eventList
    private void addEvents(EventList el, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfEvents");
        for (Object json : jsonArray) {
            JSONObject nextEvent = (JSONObject) json;
            addEvent(el, nextEvent);
        }
    }

    // MODIFIES: al
    // EFFECTS: parses assignment from JSON object and adds it to assignment list
    private void addAssignment(AssignmentList al, JSONObject jsonObject) {
        try {
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("description");
            JSONObject dateJson = jsonObject.getJSONObject("deadline");
            int day = Integer.parseInt(dateJson.getString("day"));
            int month = Integer.parseInt(dateJson.getString("month"));
            int year = Integer.parseInt(dateJson.getString("year"));
            Date date = new Date(day, month, year);
            String type = jsonObject.getString("type");
            Assignment assignment = new Assignment(name, description, date, type);
            al.addAssignment(assignment);
        } catch (Exception e) {
            System.out.println("Invalid date");
        }
    }

    // MODIFIES: el
    // EFFECTS: parses event from JSON object and adds it to event list
    private void addEvent(EventList el, JSONObject jsonObject) {
        try {
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("description");
            JSONObject dateJson = jsonObject.getJSONObject("date");
            int day = Integer.parseInt(dateJson.getString("day"));
            int month = Integer.parseInt(dateJson.getString("month"));
            int year = Integer.parseInt(dateJson.getString("year"));
            Date date = new Date(day, month, year);
            String timeString = jsonObject.getString("time");
            int time = Integer.parseInt(timeString);
            Event event = new Event(name, description, date, time);
            el.addEvent(event);
        } catch (Exception e) {
            System.out.println("Invalid date");
        }
    }
}
