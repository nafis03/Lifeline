package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// Represents a list of events to be handled by TaskManagerApp
public class EventList implements Writable {

    private LinkedList<Event> listOfEvents;

    // EFFECTS: Initializes a new empty list of upcoming events
    public EventList() {
        listOfEvents = new LinkedList();
    }

    // REQUIRES: event.getStatus is false
    // MODIFIES: this
    // EFFECT: adds an event to list of upcoming, inserting it in at the proper position so the list maintains an
    // order of earliest event to latest event
    public void addEvent(Event event) {
        int properPosition = 0;

        for (int position = 0; position < listOfEvents.size(); position++) {
            Event contender = listOfEvents.get(position);

            if (event.getDate().isEarlier(contender.getDate())) {
                properPosition = position;
                position = listOfEvents.size();
            } else if (event.getDate().isSameDay(contender.getDate()) && event.getTime() < contender.getTime()) {
                properPosition = position;
                position = listOfEvents.size();
            } else {
                properPosition = position + 1;
            }
        }

        listOfEvents.add(properPosition, event);
    }

    // REQUIRES: day is >= 1 and <= 31
    // EFFECT: returns a list of all the events taking place on the given day
    public EventList getEventListForDay(int day, int month, int year) {
        EventList result = new EventList();

        for (Event e : listOfEvents) {
            boolean checkDay = e.getDate().getDay() == day;
            boolean checkMonth = e.getDate().getMonth() == month;
            boolean checkYear = e.getDate().getYear() == year;

            if (checkDay && checkMonth && checkYear) {
                result.addEvent(e);
            }
        }

        return result;
    }

    // REQUIRES: day is >= 1 and <= 31
    // EFFECT: returns a list of all the events this week in the same month (all events from day to day + 6)
    public EventList getEventListForWeek(int day, int month, int year) {
        EventList result = new EventList();

        for (Event e : listOfEvents) {
            boolean checkDay = e.getDate().getDay() >= day && e.getDate().getDay() <= day + 6;
            boolean checkMonth = e.getDate().getMonth() == month;
            boolean checkYear = e.getDate().getYear() == year;

            if (checkDay && checkMonth && checkYear) {
                result.addEvent(e);
            }
        }

        return result;
    }

    // REQUIRES: month is >= 1 and <= 12
    // EFFECT: returns a list of all the events that will take place in given month
    public EventList getEventListForMonth(int month, int year) {
        EventList result = new EventList();

        for (Event e : listOfEvents) {
            boolean checkMonth = e.getDate().getMonth() == month;
            boolean checkYear = e.getDate().getYear() == year;

            if (checkMonth && checkYear) {
                result.addEvent(e);
            }
        }

        return result;
    }

    // EFFECT: returns event at given position in list
    public Event getEventAtPosition(int i) {
        return listOfEvents.get(i);
    }

    // EFFECT: returns true if list is empty (has no events)
    public boolean isEmpty() {
        if (listOfEvents.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECT: returns size of list of events
    public int getSize() {
        return listOfEvents.size();
    }

    // EFFECT: returns true if list contains given event
    public boolean containsEvent(Event e) {
        return listOfEvents.contains(e);
    }

    // EFFECTS: returns all the events in the list in (readable) string form
    public String viewEventList() {
        String result = "";
        int index;

        if (listOfEvents.isEmpty()) {
            result = "No upcoming events.";
        } else {
            for (int position = 1; position <= listOfEvents.size(); position++) {
                index = position - 1;
                result = result + "\nEvent " + position + ": " + listOfEvents.get(index).printEvent();
            }
        }
        return result;
    }

    // MODIFIES: this
    // EFFECTS: updates the event list also removing any assignments with event.getStatus == true
    public void updateEventList() {
        for (int index = 0; index < listOfEvents.size(); index++) {
            Event e = listOfEvents.get(index);

            if (e.getStatus() == true) {
                listOfEvents.remove(e);
            }
        }
    }

    // returns this list of events as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("listOfEvents", listOfEventsToJson());
        return json;
    }

    // EFFECTS: returns assignments in this list of assignments as a JSON array
    private JSONArray listOfEventsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Event e : listOfEvents) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns an unmodifiable list of events from this this of events
    public List<Event> getEvents() {
        return Collections.unmodifiableList(listOfEvents);
    }


}
