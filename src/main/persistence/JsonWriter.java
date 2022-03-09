package persistence;

import model.AssignmentList;
import model.EventList;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of assignment/event list to file

// NOTE: THIS CLASS HAS BEEN HEAVILY INSPIRED BY JsonSerializationDemo from
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// AND MANY OF THE METHODS IMPLEMENTED HERE ARE SIMILAR TO THE ONES IN THE DEMO
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of assignment list to file
    public void writeAssignmentList(AssignmentList al) {
        JSONObject json = al.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of event list to file
    public void writeEventList(EventList el) {
        JSONObject json = el.toJson();
        saveToFile(json.toString(TAB));
    }

}
