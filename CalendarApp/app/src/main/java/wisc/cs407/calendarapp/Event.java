package wisc.cs407.calendarapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Victor on 3/8/2016.
 */
public class Event {

    private static int counter = 0;

    private Date time;
    private String eventName;
    private String eventDescription;
    private int id;
    private String test;

    public Event(String time, String en, String eD) throws ParseException{
        this.test = time;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        this.time = format.parse(time);
        this.eventName = en;
        this.eventDescription = eD;
        // use ids for deletion
        this.id = Event.counter;
        Event.counter = Event.counter + 1;
    }

    // this constructor is for loading saved events. We use a saved id and
    // we do NOT increment the event id counter
    public Event(String time, String en, String eD, String strId) throws ParseException{
        this.test = time;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        this.time = format.parse(time);
        this.eventName = en;
        this.eventDescription = eD;
        // use ids for deletion
        this.id = Integer.parseInt(strId);
    }

    public int getId() {
        return this.id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {

        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String toString() {
        String s1 = "";
        String newline = System.getProperty("line.separator");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = dateFormatter.format(this.time);
        s1 = "Date: " + strDate + newline;
        s1 += "Event Name: " + this.eventName + newline;
        s1 += "Event Description: " + this.eventDescription + newline;
        s1 += "Event Id: " + this.id + newline + newline;
        return s1;
    }
}
