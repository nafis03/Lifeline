package model;


import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represents an assignment having a name, description, deadline, status and type
public class Assignment implements Writable {
    private String name;
    private String description;
    private Date deadline;
    private String status;
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(deadline, that.deadline) && Objects.equals(status, that.status) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, deadline, status, type);
    }

    // EFFECTS: initializes a new assignment with a name, description, deadline, type and status of "incomplete"
    public Assignment(String name, String description, Date deadline, String type) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.type = type;
        status = "incomplete";
    }

    // MODIFIES: this
    // EFFECTS: sets the assignments name to the given string value
    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: sets the assignments description to the given string value
    public void setDescription(String description) {
        this.description = description;
    }

    // MODIFIES: this
    // EFFECTS: sets the assignments deadline to the given date
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    // MODIFIES: this
    // EFFECTS: sets the assignments status to complete, incomplete, or overdue
    public void setStatus(String status) {
        this.status = status;
    }

    // MODIFIES: this
    // EFFECTS: sets the assignments type (school-class) to the given string value
    public void setType(String type) {
        this.type = type;
    }

    // EFFECTS: returns assignment name
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns assignment description
    public String getDescription() {
        return this.description;
    }

    // EFFECTS: returns assignment deadline
    public Date getDeadline() {
        return this.deadline;
    }

    // EFFECTS: returns assignment status
    public String getStatus() {
        return this.status;
    }

    // EFFECTS: returns assignment type
    public String getType() {
        return this.type;
    }

    //EFFECTS: prints out an assignment
    public String printAssignment() {

        int day = this.deadline.getDay();
        int month = this.deadline.getMonth();
        int year = this.deadline.getYear();

        String dstring = Integer.toString(day);
        String mstring = Integer.toString(month);
        String ystring = Integer.toString(year);

        String dateString = dstring + "/" + mstring + "/" + ystring;

        String result = this.name + " || " + this.description + " || " + dateString + " || " + this.status;
        result = result + " || " + this.type + " ||";

        return result;
    }

    // EFFECT: returns this assignment as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        json.put("deadline", deadline.toJson());
        json.put("type", type);
        json.put("status", status);
        return json;
    }
}
