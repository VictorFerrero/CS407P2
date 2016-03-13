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
        import android.widget.Toast;

        import java.text.ParseException;
        import java.util.Calendar;

 class CustomAddEventButtonListener implements View.OnClickListener {

     private AddEventFragment.AddEventCallback callback;
     private Fragment theFragment;
     private View fragmentView;
     private CustomCalendar theMainCalendar;
     // our custom listener needs access to the callback function
     // as well as the fragment view so it can grab data from the forms
     // and we need the fragment object to pass back in callback so we can destroy the fragment
     public CustomAddEventButtonListener(AddEventFragment.AddEventCallback callback, Fragment fragment, View fragmentView, CustomCalendar c) {
         this.callback = callback;
         this.theFragment = fragment;
         this.fragmentView = fragmentView;
         this.theMainCalendar = c;
     }

     public void onClick(View v) {
         // grab data from the text boxes
         EditText eventNameEditTextBox = (EditText) this.fragmentView.findViewById(R.id.eventName);
         EditText eventScriptEditTextBox = (EditText) this.fragmentView.findViewById(R.id.eventDescription);
         DatePicker datePicker = (DatePicker) this.fragmentView.findViewById(R.id.addEventDate);
         String eventName = eventNameEditTextBox.getText().toString();
         String eventDescription = eventScriptEditTextBox.getText().toString();

         if(eventName.equals("") || eventDescription.equals("")) {
             return;
         }

         int month = datePicker.getMonth();
         month = month + 1; // date picker indexes months starting at 0, SimpleDateFormatter doesnt like this
         int day = datePicker.getDayOfMonth();
         int year = datePicker.getYear();
         String date = Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
        Event event = null;
         try {
             event = new Event(date, eventName, eventDescription);
         } catch(ParseException e) {
             e.printStackTrace();
             event = null;
         }
         if(event!= null) {
             this.theMainCalendar.addEvent(event);
             // wipe the edit text fields after successful event is added
             EditText et = (EditText) this.fragmentView.findViewById(R.id.eventName);
             et.setText(new char[' '], 0, 0);

             et = (EditText) this.fragmentView.findViewById(R.id.eventDescription);
             et.setText(new char[' '], 0, 0);
         }
     }
}

public class AddEventFragment
        extends Fragment {

    private AddEventCallback mCallback;
    private View fragmentView;
    private CustomCalendar theMainCalendar;

    public void setCalendar(CustomCalendar c){
        this.theMainCalendar = c;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentView view = inflater.inflate(R.layout.fragment_rssitem_detail,
        View view = inflater.inflate(R.layout.add_event_fragment, container, false);
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePicker dp = (DatePicker)view.findViewById(R.id.addEventDate);
        dp.init(year, month, day, null);
        this.fragmentView = view;


        Button addEventButton = (Button) view.findViewById(R.id.addEventFragmentButton);
        addEventButton.setOnClickListener(new CustomAddEventButtonListener(this.mCallback, this, this.fragmentView, this.theMainCalendar));

        return view;
    }

    // Container Activity must implement this interface
    public interface AddEventCallback {
       // public void addEventCallback(Fragment fragment, Event event);
        public void addEventCallback(Fragment fragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        if(context instanceof Activity) {
            Activity activity = (Activity) context;
            try {
                this.mCallback = (AddEventCallback) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement AddEventCallback interface");
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

