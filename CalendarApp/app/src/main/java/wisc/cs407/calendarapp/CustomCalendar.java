package wisc.cs407.calendarapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Victor on 3/8/2016.
 */
public class CustomCalendar {

    private List<Event> events;

    public CustomCalendar() {
        this.events = new ArrayList<Event>();
    }

    public void addEvent(Event e) {
        this.events.add(e);
    }

    public void deleteEvent(int id) {
        Event e = null;
        for(int i = 0; i < this.events.size(); i++) {
            e = this.events.get(i);
            if(e.getId() == id) {
                this.events.remove(i);
            }
            e = null;
        }
    }

    public List<Event> getEventsForDate(Date d) {
        List<Event> e = new ArrayList<Event>();
        for(int i = 0; i < this.events.size(); i++) {
            Event eTmp = this.events.get(i);
            if(eTmp.getTime().equals((d))) {
                e.add(eTmp);
            }
        }
        return e;
    }
}
