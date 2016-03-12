package wisc.cs407.calendarapp;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements AddEventFragment.AddEventCallback, ViewDeleteFragment.ViewDeleteCallback {

    private CustomCalendar calendar;
    private Fragment mostCurrentFragment;
    public static Activity mainActivity;


    public void viewDeleteCallback(Fragment fragment) {
        FragmentManager manager = ((Fragment) fragment).getFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove((Fragment) fragment);
        trans.commit();

        this.showAllButtons();
        this.mostCurrentFragment = null;
    }
    public void addEventCallback(Fragment fragment) {

        FragmentManager manager = ((Fragment) fragment).getFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove((Fragment) fragment);
        trans.commit();

     //   Toast.makeText(getBaseContext(), "", Toast.LENGTH_LONG).show();
        this.showAllButtons();
        this.mostCurrentFragment = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        this.mainActivity = this;

        String strCalendar = this.readFromFile(this.getApplicationContext(), "savedCalendar.txt");
        boolean needToMakeCalendar = true;
        if(!strCalendar.equals("")) {
          //  Toast.makeText(getBaseContext(), "strCalendar", Toast.LENGTH_LONG).show();
            Toast.makeText(getBaseContext(), strCalendar, Toast.LENGTH_LONG).show();

            try {
                this.calendar = this.createCalendarFromString(strCalendar);
                needToMakeCalendar = false;
            } catch(ParseException e) {
                this.calendar = null;
                needToMakeCalendar = true;
            }
        }
        else {
            Toast.makeText(getBaseContext(), "SDIgjsdgl", Toast.LENGTH_LONG).show();
        }
        //  Toast.makeText(getBaseContext(), strCalendar, Toast.LENGTH_LONG).show();
        if(needToMakeCalendar) {
            this.calendar = new CustomCalendar();
        }
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

      ////  DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
       // dp.init(year, month, day, null);

    }

    private void hideAllButtons(){
        Button button = (Button) this.findViewById(R.id.addEventButton);
        button.setVisibility(View.INVISIBLE);

        button = (Button) this.findViewById(R.id.viewOrDeleteButton);
        button.setVisibility(View.INVISIBLE);
    }

    private void showAllButtons() {
        Button button = (Button) this.findViewById(R.id.addEventButton);
        button.setVisibility(View.VISIBLE);

        button = (Button) this.findViewById(R.id.viewOrDeleteButton);
        button.setVisibility(View.VISIBLE);
    }

    public void addNewEvent(View view) {
        // hide the add event button when we open this fragment
        this.hideAllButtons();
        AddEventFragment aeFrag = new AddEventFragment();
        aeFrag.setCalendar(this.calendar);
        this.mostCurrentFragment = aeFrag;
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, aeFrag).commit();
    }

    public void viewOrDeleteEvents(View view) {
        this.hideAllButtons();
        ViewDeleteFragment vdFrag = new ViewDeleteFragment();
        vdFrag.setCalendar(this.calendar);
        this.mostCurrentFragment = vdFrag;
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, vdFrag).commit();
    }
    // block functionality of the back button
    public void onBackPressed() {
        if(this.mostCurrentFragment instanceof ViewDeleteFragment) {
            this.viewDeleteCallback(this.mostCurrentFragment);
        }
        else if(this.mostCurrentFragment instanceof AddEventFragment){
            this.addEventCallback(this.mostCurrentFragment);
        }
        // we only want to destroy the fragment, not the activity
        //super.onBackPressed();
    }



    public void onStart() {
        super.onStart();
    }

    // public void onRestart(){
//        super.onRestart();
    //  }

    public void onResume(){
        super.onResume();/*
        // here we will restore the calendar just to be safe
        String strCalendar = this.readFromFile(this.getApplicationContext(), "savedCalendar.txt");
        if(strCalendar != "") {
            try {
                this.calendar = this.createCalendarFromString(strCalendar);
            } catch(ParseException e){
                this.calendar = null;
            }
        }
        */
      //  Toast.makeText(getBaseContext(), strCalendar, Toast.LENGTH_LONG).show();
    }

    public void onPause(){
        this.writeCalendarToFile();
        super.onPause();
    }


    public void onStop(){
        super.onStop();
    }

    public void onDestroy(){
        super.onDestroy();
      //  getApplicationContext().deleteFile("savedCalendar.txt");
    }

    private CustomCalendar createCalendarFromString(String strCal) throws ParseException{
        CustomCalendar cal = new CustomCalendar();
        String[] strEvents = strCal.split("|");
        for(int i = 0; i < strEvents.length; i++) {
            String[] event = strEvents[i].split(",");

            if(event.length > 0) {
            //    Toast.makeText(getBaseContext(), event[0], Toast.LENGTH_LONG).show();
               /* String strDate = event[0];
                String eventName = event[1];
                String eventDescription = event[2];
                String strId = event[3];
                Event e = new Event(strDate, eventName, eventDescription, strId);
                cal.addEvent(e);
                */
            }
        }
        return cal;
    }
    private String readFromFile(Context context, String fileName) {
        if (context == null) {
            return null;
        }
        String ret = "";
        String otherReturn = "";
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int size = inputStream.available();
                char[] buffer = new char[size];
                inputStreamReader.read(buffer);
                inputStream.close();
                ret = new String(buffer);

                char[] tmp = ret.toCharArray();
                for(int i = 0; i < tmp.length; i++) {
                    otherReturn += tmp[i];
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
         //   ret = e.getMessage();
            ret = "";
        }
        return otherReturn;
    }

    private void writeCalendarToFile() {
        Context context = this.getApplicationContext();
        //Toast.makeText(getBaseContext(), "WRITE", Toast.LENGTH_LONG).show();
        // this is where we will save the calendar to a file
        // first delete the old text file
        context.deleteFile("savedCalendar.txt");
        // now lets write to a file
        File path = context.getFilesDir();
        File file = new File(path, "savedCalendar.txt");
        try {
            FileOutputStream stream = new FileOutputStream(file);
            String output = this.calendar.toString();
            stream.write(output.getBytes());
            stream.close();
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.exit(5);
        }
    }
}
