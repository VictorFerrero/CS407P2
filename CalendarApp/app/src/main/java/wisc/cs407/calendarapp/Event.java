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

    public Event(String time, String en, String eD) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.time = format.parse(time);
        this.eventName = en;
        this.eventDescription = eD;
        // use ids for deletion
        this.id = Event.counter;
        Event.counter = Event.counter + 1;
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
        String s1 = "NOT IMPLEMENTED";


        return s1;
    }
}
