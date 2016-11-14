package com.acalendar.acal.Events;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A page where user can edit/create a single event. This page is reached when clicked add in
 * main page or clicked edit in even info display page.
 */
public class EventInfoEditPageActivity  extends Activity {

    private Calendar startCalendar;
    private Button startTimeViewButton;
    private TimePickerDialog stpDialog;

    private Calendar endCalendar;
    private Button endTimeViewButton;
    private TimePickerDialog etpDialog;

    private Button datePickerViewButton;
    private DatePickerDialog dpDialog;

    private SimpleDateFormat sdf;

    private Button manageParticipantButton;
    private Button saveButton;
    private String eventid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_info_edit_page);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        // get the widgets
        datePickerViewButton = (Button) findViewById(R.id.datePickerButton);
        startTimeViewButton = (Button) findViewById(R.id.startTimeViewButton);
        endTimeViewButton = (Button) findViewById(R.id.endTimeViewButton);
        saveButton = (Button) findViewById(R.id.saveEditButton);
        manageParticipantButton = (Button) findViewById(R.id.manageParticipantButton);

        if (false) {  // TODO: if previous page gives event info
            // TODO: set eventid
        } else {
            datePickerViewButton.setText(sdf.format(startCalendar.getTime()));
            startTimeViewButton.setText(startCalendar.get(Calendar.HOUR_OF_DAY)
                    + " : " + startCalendar.get(Calendar.MINUTE));
            endTimeViewButton.setText(startCalendar.get(Calendar.HOUR_OF_DAY)
                    + " : " + startCalendar.get(Calendar.MINUTE));
        }
        // set up date picker dialog
        dpDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, monthOfYear);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                datePickerViewButton.setText(sdf.format(startCalendar.getTime())); // only prints out the date
            }
        }, startCalendar.get(Calendar.YEAR),
                startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpDialog.show();
            }
        });

        // set up start time picker dialogs
        stpDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                startCalendar.set(Calendar.MINUTE, minute);
                startTimeViewButton.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
            }
        }, startCalendar.get(Calendar.HOUR_OF_DAY), startCalendar.get(Calendar.MINUTE), false);
        startTimeViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stpDialog.show();
            }
        });
        endCalendar = (Calendar) startCalendar.clone();
        // set up start time picker dialogs
        etpDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                endCalendar.set(Calendar.MINUTE, minute);
                endTimeViewButton.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
            }
        }, endCalendar.get(Calendar.HOUR_OF_DAY), endCalendar.get(Calendar.MINUTE), false);
        endTimeViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etpDialog.show();
            }
        });

        // location picker is just a editText for now; may be changed later

        // share with friends -> goes to select participatting Friends Page
        manageParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventInfoEditPageActivity.this,
                        EditEventParticipantsActivity.class);
                startActivityForResult(i, 0); // results returned are handled in OnActicityResult
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: alert any invalid inputs
                // TODO: collect all information in this page. Backup remotely
                if (eventid == null) {
                    // TODO: call backend create event.
                    // get all info of user input
                    EditText titleView = (EditText) findViewById(R.id.EventTitleEditText);
                    EditText locationView = (EditText) findViewById(R.id.locationEditText);
                    AutoCompleteTextView descriptionView = (AutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView);
                    String eventTitle = titleView.getText().toString();
                    String location = locationView.getText().toString();
                    String description = descriptionView.getText().toString();
                    Button dateView = (Button) findViewById(R.id.datePickerButton);
                    String date = dateView.getText().toString();
                    String[] dateArr = date.split("/");
                    int day = Integer.parseInt(dateArr[1]);
                    int month = Integer.parseInt(dateArr[0]);
                    int year = Integer.parseInt(dateArr[2]);
                    Button startView = (Button) findViewById(R.id.startTimeViewButton);
                    Button endView = (Button) findViewById(R.id.endTimeViewButton);
                    String[] startArr = startView.getText().toString().split(":");
                    String[] endArr = endView.getText().toString().split(":");
                    Date startTime = new Date(year, month, day, Integer.parseInt(startArr[0]), Integer.parseInt(startArr[1]));
                    Date endTime = new Date(year, month, day, Integer.parseInt(endArr[0]), Integer.parseInt(endArr[1]));
                    boolean isPublic = !((CheckBox) findViewById(R.id.privateCheckbox)).isChecked();

                    Event event = new Event(eventTitle, startTime, endTime, location, description, isPublic);

                    LoginedAccount.getEventsManager().addEvent(event);
                } else {

                    // TODO: update the event which has eventid in DB
                }
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        Log.v("Test", "onActivityResult is called; result is returned");
        if (resultCode == RESULT_CANCELED) {
            // TODO: nothing for now.
        } else if (requestCode == 0 && resultCode == RESULT_OK) {
            // TODO: get from the intent the data.
            ArrayList<String> lst = data.getStringArrayListExtra("listOfSelectedUid");
            Log.v("Test", "list that was return is" + lst.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // TODO: save the data entered: all textEdits, Times.
    }
}
