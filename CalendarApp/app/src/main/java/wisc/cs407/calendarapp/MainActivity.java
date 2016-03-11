package wisc.cs407.calendarapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AddEventFragment.AddQuestionCallback {

    private CustomCalendar calendar;


    public void addQuestionCallback(Fragment fragment, Event event) {
        this.calendar.addEvent(event);


        FragmentManager manager = ((Fragment) fragment).getFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove((Fragment) fragment);
        trans.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        this.calendar = new CustomCalendar();
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

      ////  DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
       // dp.init(year, month, day, null);

    }

    public void addNewEvent(View view) {
        AddEventFragment aeFrag = new AddEventFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, aeFrag).commit();
    }
}
