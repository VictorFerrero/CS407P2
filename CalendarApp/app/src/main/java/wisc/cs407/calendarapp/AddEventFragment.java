package wisc.cs407.calendarapp;

        import android.app.Activity;
        import android.content.Context;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.DatePicker;
        import android.widget.EditText;

        import java.text.ParseException;
        import java.util.Calendar;

public class AddEventFragment
        extends Fragment {

    private AddQuestionCallback mCallback;
    private View fragmentView;
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
        return view;
    }

    // Container Activity must implement this interface
    public interface AddQuestionCallback {
        public void addQuestionCallback(Fragment fragment, Event event);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        if(context instanceof Activity) {
            Activity activity = (Activity) context;
            try {
                this.mCallback = (AddQuestionCallback) activity;
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


    // onClick handler for the adding an event to the calendar
    public void onAddEventClick(View v) throws ParseException{

        EditText eventNameEditTextBox = (EditText) this.fragmentView.findViewById(R.id.eventName);
        EditText eventScriptEditTextBox = (EditText) this.fragmentView.findViewById(R.id.eventDescription);
        DatePicker datePicker = (DatePicker) this.fragmentView.findViewById(R.id.addEventDate);

        String eventName = eventNameEditTextBox.getText().toString();
        String eventDescription = eventScriptEditTextBox.getText().toString();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int year = datePicker.getYear();
        String date = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);

        Event e = new Event(date, eventName, eventDescription);
        this.mCallback.addQuestionCallback(this, e);
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

