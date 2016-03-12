package wisc.cs407.calendarapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public List<Event> getEventsForDate(String strDate) {
        strDate = strDate.trim();
        List<Event> e = new ArrayList<Event>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date d = null;
        try {
            d = dateFormat.parse(strDate);
        } catch(ParseException ex){
            ex.printStackTrace();
            d = null;
        }
        for(int i = 0; i < this.events.size(); i++) {
            Event eTmp = this.events.get(i);
            Date tmpDate = eTmp.getTime();
            if(tmpDate.equals(d)) {
                e.add(eTmp);
            }
        }
        return e;
    }

    public String toString() {
        String s1 = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String newline = ",";
        for(int i = 0; i < this.events.size(); i++) {
            Event event = this.events.get(i);
            s1 += dateFormat.format(event.getTime()) + newline;
            s1 += event.getEventName() + newline;
            s1 += event.getEventDescription() + newline;
            s1 += event.getId() + "|";
        }
        return s1;
    }
}
