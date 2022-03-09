package model;

import model.exceptions.InvalidDateException;
import model.exceptions.TooEarlyException;
import org.json.JSONObject;
import persistence.Writable;

// Represents a date with a day, month and year
public class Date implements Writable {
    private int day;
    private int month;
    private int year;

    // EFFECTS: initializes a date with given values for day, month and year
    public Date(int day, int month, int year) throws TooEarlyException, InvalidDateException {
        if (month <= 0 || month > 12 || day <= 0 || day > 31) {
            throw new InvalidDateException();
        } else if (year < 2021 || (year == 2021 && month < 8)) {
            throw new TooEarlyException();
        } else {
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }

    // REQUIRES: integer value >= 1 and <= 31
    // MODIFIES: this
    // EFFECTS: sets day to given
    public void setDay(int day) {
        this.day = day;
    }

    // REQUIRES: integer value >= 1 and <= 12
    // MODIFIES: this
    // EFFECTS: sets month to given
    public void setMonth(int month) {
        this.month = month;
    }

    // REQUIRES: integer value >= 2021
    // MODIFIES: this
    // EFFECTS: sets year to given
    public void setYear(int year) {
        this.year = year;
    }

    // EFFECTS: returns day of date
    public int getDay() {
        return this.day;
    }

    // EFFECTS: returns month of date
    public int getMonth() {
        return this.month;
    }

    // EFFECTS: returns year of date
    public int getYear() {
        return this.year;
    }

    // EFFECT: returns true if the the date is earlier than the given date
    public boolean isEarlier(Date date2) {
        if (this.year < date2.getYear()) {
            return true;
        } else if (this.year > date2.getYear()) {
            return false;
        } else if (this.month < date2.getMonth()) {
            return true;
        } else if (this.month > date2.getMonth()) {
            return false;
        } else if (this.day < date2.getDay()) {
            return true;
        } else if (this.day > date2.getDay()) {
            return false;
        } else {
            return false;
        }
    }

    // EFFECT: returns true if date is the same as given date (if both dates have same values for all fields)
    public boolean isSameDay(Date date2) {
        if (this.day == date2.getDay() && this.month == date2.getMonth() && this.year == date2.getYear()) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECT: returns this date as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("day", Integer.toString(day));
        json.put("month", Integer.toString(month));
        json.put("year", Integer.toString(year));
        return json;
    }

}
