package wisc.cs407.calendarapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Victor on 3/11/2016.
 */

class CustomDeleteButtonListener implements View.OnClickListener {

    private Fragment theFragment;
    private View fragmentView;
    private CustomCalendar theMainCalendar;
    // our custom listener needs access to the callback function
    // as well as the fragment view so it can grab data from the forms
    // and we need the fragment object to pass back in callback so we can destroy the fragment
    public CustomDeleteButtonListener(Fragment fragment, View fragmentView, CustomCalendar c) {
        this.theFragment = fragment;
        this.fragmentView = fragmentView;
        this.theMainCalendar = c;
    }

    public void onClick(View v) {
        EditText deleteIdEditText = (EditText) this.fragmentView.findViewById(R.id.idForDelete);
        String strId = deleteIdEditText.getText().toString();
        int id = -1;
        try {
            id = Integer.parseInt(strId);
        } catch(Exception e) {
            // if they did not enter an int, then we just return
          //  Toast.makeText(this.fragmentView.getContext(), Integer.toString(id), Toast.LENGTH_LONG).show();
            return;
        }
        // now we know we at least have an a valid integer, so delete it from calendar
        this.theMainCalendar.deleteEvent(id); // if id doesnt exist, nothing will change

        // now we will update the text view
        DatePicker datePicker = (DatePicker) this.fragmentView.findViewById(R.id.viewDeleteDatePicker);
        int month = datePicker.getMonth();
        month = month + 1; // date picker indexes months starting at 0, SimpleDateFormatter doesnt like this
        int day = datePicker.getDayOfMonth();
        int year = datePicker.getYear();
        String strDate = Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
        List<Event> eventsOnThisDate = this.theMainCalendar.getEventsForDate(strDate);
        String textViewOutput = "No events on this day";
        for(int i = 0; i < eventsOnThisDate.size(); i++) {
            if(i == 0) {
                textViewOutput = eventsOnThisDate.get(i).toString();
            }
            else {
                textViewOutput += eventsOnThisDate.get(i).toString();
            }
        }

        TextView tv = (TextView) this.fragmentView.findViewById(R.id.displayEventsTextview);
        char[] seq = textViewOutput.toCharArray();
        tv.setText(seq, 0, seq.length);
        return;
    }
}

class CustomDatePickerListener implements DatePicker.OnDateChangedListener {

    private View fragmentView;
    private CustomCalendar theMainCalendar;

    public CustomDatePickerListener(View fragmentView, CustomCalendar c) {
        this.fragmentView = fragmentView;
        this.theMainCalendar = c;
    }


    public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
      //  Toast.makeText(this.fragmentView.getContext(), "CLICLED ON DATE PICKER", Toast.LENGTH_LONG).show();
        monthOfYear = monthOfYear + 1;
        String strDate = Integer.toString(monthOfYear)+ "/" + Integer.toString(dayOfMonth)  + "/" + Integer.toString(year);
        strDate = strDate.trim();

        List<Event> eventsOnThisDate = this.theMainCalendar.getEventsForDate(strDate);
        String textViewOutput = "No events on this day";
        for(int i = 0; i < eventsOnThisDate.size(); i++) {
            if(i == 0) {
                textViewOutput = eventsOnThisDate.get(i).toString();
            }
            else {
                textViewOutput += eventsOnThisDate.get(i).toString();
            }
        }

        TextView tv = (TextView) this.fragmentView.findViewById(R.id.displayEventsTextview);
        char[] seq = textViewOutput.toCharArray();
        tv.setText(seq, 0, seq.length);

        return;
    }
}

public class ViewDeleteFragment extends Fragment {


    private ViewDeleteCallback mCallback;
    private View fragmentView;

    private CustomCalendar theMainCalendar;

    public void setCalendar(CustomCalendar c) {
        this.theMainCalendar = c;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentView view = inflater.inflate(R.layout.fragment_rssitem_detail,
        View view = inflater.inflate(R.layout.view_delete_fragment, container, false);
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // initialize the listener for the date picker
        CustomDatePickerListener dpListener = new CustomDatePickerListener(view, this.theMainCalendar);
        DatePicker dp = (DatePicker)view.findViewById(R.id.viewDeleteDatePicker);
        dp.init(year, month, day, dpListener);

        // initialize the listener for the delete button
        CustomDeleteButtonListener dbListener = new CustomDeleteButtonListener(this, view, this.theMainCalendar);
        Button deleteButton = (Button) view.findViewById(R.id.deleteEventButton);
        deleteButton.setOnClickListener(dbListener);

        this.fragmentView = view;
        this.initializeEventTextView(); // we are going to default to showing events for the current day

        return view;
    }

    private void initializeEventTextView() {
        DatePicker datePicker = (DatePicker) this.fragmentView.findViewById(R.id.viewDeleteDatePicker);
        int month = datePicker.getMonth();
        month = month + 1; // date picker indexes months starting at 0, SimpleDateFormatter doesnt like this
        int day = datePicker.getDayOfMonth();
        int year = datePicker.getYear();
        String strDate = Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);

        List<Event> eventsOnThisDate = this.theMainCalendar.getEventsForDate(strDate);
        String textViewOutput = "No events on this day";
        for(int i = 0; i < eventsOnThisDate.size(); i++) {
            if(i == 0) {
                textViewOutput = eventsOnThisDate.get(i).toString();
            }
            else {
                textViewOutput += eventsOnThisDate.get(i).toString();
            }
        }

        TextView tv = (TextView) this.fragmentView.findViewById(R.id.displayEventsTextview);
        char[] seq = textViewOutput.toCharArray();
        tv.setText(seq, 0, seq.length);
    }
    // Container Activity must implement this interface
    public interface ViewDeleteCallback {
        public void viewDeleteCallback(Fragment fragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        if(context instanceof Activity) {
            Activity activity = (Activity) context;
            try {
                this.mCallback = (ViewDeleteCallback) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement AddQuetionCallback interface");
            }
        } else {
            throw new ClassCastException("context is not activity");
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

/*
    public void onBackPressed() {
       // super.onBackPressed();
        // Toast.makeText(this.getContext(), "You cannot go back to previous question.", Toast.LENGTH_LONG).show();
    }
*/

    public void onStart() {
        super.onStart();
    }

    // public void onRestart(){
//        super.onRestart();
    //  }

    public void onResume(){
        super.onResume();
    }

    public void onPause(){
        super.onPause();
    }

    public void onStop(){
        super.onStop();
    }

    public void onDestroy(){
        super.onDestroy();
        this.mCallback = null;
    }






}