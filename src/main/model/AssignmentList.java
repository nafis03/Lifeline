package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

// test change
// Represents a (to-do) list of assignments to be handled by TaskManagerApp
public class AssignmentList implements Writable {

    private LinkedList<Assignment> listOfAssignments;

    // EFFECTS: Initializes a new empty list of assignments the user needs to complete
    public AssignmentList() {
        listOfAssignments = new LinkedList();
    }

    // REQUIRES: assignment.getStatus is not "complete"
    // MODIFIES: this
    // EFFECT: adds assignment to list of assignments, inserting it at the proper position so the list maintains an
    // order of earliest due assignment to latest due assignment
    public void addAssignment(Assignment assignment) {
        int properPosition = listOfAssignments.size();

        for (int position = 0; position < listOfAssignments.size(); position++) {
            Assignment contender = listOfAssignments.get(position);

            if (assignment.getDeadline().isEarlier(contender.getDeadline())) {
                properPosition = position;
                position = listOfAssignments.size();
            }
        }

        listOfAssignments.add(properPosition, assignment);

    }

    // REQUIRES: day is >= 1 and <= 31; also assignment list is not empty
    // EFFECT: returns a list of all the assignments due on the given day
    public AssignmentList getAssignmentListForDay(int day, int month, int year) {
        AssignmentList result = new AssignmentList();

        for (Assignment a : listOfAssignments) {
            boolean checkDay = a.getDeadline().getDay() == day;
            boolean checkMonth = a.getDeadline().getMonth() == month;
            boolean checkYear = a.getDeadline().getYear() == year;

            if (checkDay && checkMonth && checkYear) {
                result.addAssignment(a);
            }
        }

        return result;
    }

    // REQUIRES: day is >= 1 and <= 31; also assignment list is not empty
    // EFFECT: returns a list of all the assignments due in the week of the day given, in the same month
    // (all assignments with deadlines of dates day + 6)
    public AssignmentList getAssignmentListForWeek(int day, int month, int year) {
        AssignmentList result = new AssignmentList();

        for (Assignment a : listOfAssignments) {
            boolean checkDay = a.getDeadline().getDay() >= day && a.getDeadline().getDay() <= day + 6;
            boolean checkMonth = a.getDeadline().getMonth() == month;
            boolean checkYear = a.getDeadline().getYear() == year;

            if (checkDay && checkMonth && checkYear) {
                result.addAssignment(a);
            }
        }
        return result;
    }

    // REQUIRES: month is >= 1 and <= 12; also assignment list is not empty
    // EFFECT: returns a list of all the assignments due in the given month
    public AssignmentList getAssignmentListForMonth(int month, int year) {
        AssignmentList result = new AssignmentList();

        for (Assignment a : listOfAssignments) {
            boolean checkMonth = a.getDeadline().getMonth() == month;
            boolean checkYear = a.getDeadline().getYear() == year;

            if (checkMonth && checkYear) {
                result.addAssignment(a);
            }
        }

        return result;
    }

    // EFFECT: returns assignment at given position
    public Assignment getAssignmentAtPosition(int i) {
        return listOfAssignments.get(i);
    }

    // EFFECT: returns position of given assignment
    public Integer getPositionOfAssignment(Assignment a) {
        int result = 0;

        for (int i = 0; i < this.getSize(); i++) {
            if (a == this.getAssignmentAtPosition(i)) {
                return i;
            }
        }

        return result;
    }

    // EFFECT: returns true if list is empty (has no assignments)
    public boolean isEmpty() {
        if (listOfAssignments.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECT: returns size of list
    public int getSize() {
        return listOfAssignments.size();
    }

    // EFFECT: returns true if list contains given assignment
    public boolean containsAssignment(Assignment a) {
        return listOfAssignments.contains(a);
    }

    // EFFECTS: returns all the assignments in the list in (readable) string form; if empty returns
    // "No assignments left!"
    public String viewAssignmentList() {
        String result = "";
        int index;

        if (listOfAssignments.isEmpty()) {
            result = "No assignments left!";
        } else {
            for (int position = 1; position <= listOfAssignments.size(); position++) {
                index = position - 1;
                result = result + "\nTask " + position + ": " + listOfAssignments.get(index).printAssignment();
            }
        }
        return result;
    }

    // MODIFIES: this
    // EFFECTS: updates the assignment list also removing any assignments with status of "complete"
    public void updateAssignmentList() {
        for (int index = 0; index < listOfAssignments.size(); index++) {
            Assignment a = listOfAssignments.get(index);

            if (a.getStatus().equals("complete")) {
                listOfAssignments.remove(a);
            }
        }
    }

    // EFFECT: returns the list of assignments as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("listOfAssignments", listOfAssignmentsToJson());
        return json;
    }

    // EFFECTS: returns assignments in this list of assignments as a JSON array
    private JSONArray listOfAssignmentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Assignment a : listOfAssignments) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns an unmodifiable list of assignments from this this of assignments
    public List<Assignment> getAssignments() {
        return listOfAssignments;
    }
}
