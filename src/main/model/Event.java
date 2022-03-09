package model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.json.JSONObject;
import persistence.Writable;

// Represents an event having a name, description, date, time, and status
public class Event implements Writable {
    private String name;
    private String description;
    private Date date;
    private int time;
    private boolean status;

    // EFFECTS: initializes a new event with a name, description, date, time and status of incomplete
    // Note: time is represented in "military time". Ex 1 p.m. is 1300, 10 a.m. is 1000, etc.
    public Event(String name, String description, Date date, int time) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        status = false;
    }

    // MODIFIES: this
    // EFFECTS: sets the events name to the given string value
    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: sets the events description to the given string value
    public void setDescription(String description) {
        this.description = description;
    }

    // MODIFIES: this
    // EFFECTS: sets the events date to the given date value
    public void setDate(Date date) {
        this.date = date;
    }

    // REQUIRES: time > 0
    // MODIFIES: this
    // EFFECTS: sets the events length to the given int value
    public void setTime(int time) {
        this.time = time;
    }

    // MODIFIES: this
    // EFFECTS: sets the events status to complete (closes the event)
    public void completeEvent() {
        this.status = true;
    }

    // EFFECTS: returns events name
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns events description
    public String getDescription() {
        return this.description;
    }

    // EFFECTS: returns events date
    public Date getDate() {
        return this.date;
    }

    // EFFECTS: returns events length
    public int getTime() {
        return this.time;
    }

    // EFFECTS: returns assignment status, true if complete, false if incomplete
    public boolean getStatus() {
        return this.status;
    }

    //EFFECTS: prints out an event
    public String printEvent() {

        int day = this.date.getDay();
        int month = this.date.getMonth();
        int year = this.date.getYear();

        String dstring = Integer.toString(day);
        String mstring = Integer.toString(month);
        String ystring = Integer.toString(year);

        String dateString = dstring + "/" + mstring + "/" + ystring;

        String result = this.name + " || " + this.description + " || " + dateString + " || " + this.time + " ||";

        return result;
    }

    // EFFECT: returns this event as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        json.put("date", date.toJson());
        json.put("time", Integer.toString(time));
        json.put("status", Boolean.toString(status));
        return json;
    }
}
