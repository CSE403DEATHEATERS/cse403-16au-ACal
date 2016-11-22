package com.acalendar.acal.Events;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.acalendar.acal.Friend.Friend;
import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    private Button manageParticipantButton;
    private Button saveButton;
    private Event eventObjectToEdit;  // a copy of the event to be edit

    private EditText titleView;
    private EditText locationView;
    private AutoCompleteTextView descriptionView;
    private CheckBox privateCheckBox;

    private ArrayList<Friend> currentlySelectedParticipants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_info_edit_page);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // initialize fields
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        timeFormat = new SimpleDateFormat("HH:mm", Locale.US);
        currentlySelectedParticipants = new ArrayList<>();

        // get the widgets
        datePickerViewButton = (Button) findViewById(R.id.datePickerButton);
        startTimeViewButton = (Button) findViewById(R.id.startTimeViewButton);
        endTimeViewButton = (Button) findViewById(R.id.endTimeViewButton);
        saveButton = (Button) findViewById(R.id.saveEditButton);
        manageParticipantButton = (Button) findViewById(R.id.manageParticipantButton);
        titleView = (EditText) findViewById(R.id.EventTitleEditText);
        locationView = (EditText) findViewById(R.id.locationEditText);
        descriptionView = (AutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView);
        privateCheckBox = (CheckBox) findViewById(R.id.privateCheckbox);

        // get intent
        Intent intentRecieved = getIntent();
        boolean addNew = intentRecieved.getExtras().getBoolean("AddNew");
        Log.v("EventEditActivity", "is adding new event? :" + addNew);
        if (!addNew) {
            // editing old events -> display old values
            eventObjectToEdit = intentRecieved.getParcelableExtra("eventObjectToEdit");
            Log.v("EventEditActivity", "eventObjectToEdit is" + eventObjectToEdit);
            displayOldValues(this.eventObjectToEdit);
            // disable sharing button since we decided to remove the share function from edit
            manageParticipantButton.setVisibility(View.GONE);
        } else {
            // adding new event, set default text, eventToBeEdit is null
            datePickerViewButton.setText(dateFormat.format(startCalendar.getTime()));
            startTimeViewButton.setText(timeFormat.format(startCalendar.getTime()));
            endTimeViewButton.setText(timeFormat.format(startCalendar.getTime()));
        }
        // set up date picker dialog
        dpDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, monthOfYear);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                datePickerViewButton.setText(dateFormat.format(startCalendar.getTime())); // only prints out the date
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
                startTimeViewButton.setText(timeFormat.format(startCalendar.getTime()));
            }
        }, startCalendar.get(Calendar.HOUR_OF_DAY), startCalendar.get(Calendar.MINUTE), false);

        startTimeViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stpDialog.show();
            }
        });
        endCalendar = (Calendar) startCalendar.clone();

        // set up end time picker dialogs
        etpDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                endCalendar.set(Calendar.MINUTE, minute);
                // endTimeSelected = endCalendar.getTime();
                endTimeViewButton.setText(timeFormat.format(endCalendar.getTime()));
            }
        }, endCalendar.get(Calendar.HOUR_OF_DAY), endCalendar.get(Calendar.MINUTE), false);
        endTimeViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etpDialog.show();
            }
        });

        manageParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventInfoEditPageActivity.this,
                        EditEventParticipantsActivity.class);
                i.putParcelableArrayListExtra("originalParticipantsList",
                        currentlySelectedParticipants);
                // if user can click this, then it means he's creating new event.
                startActivityForResult(i, 995); // results returned are handled in OnActicityResult
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.setClickable(false);
                // TODO: alert any invalid inputs
                String eventTitle = titleView.getText().toString();
                String location = locationView.getText().toString();
                String description = descriptionView.getText().toString();
                boolean isPublic = !(privateCheckBox.isChecked());
                Event newEvent = new Event(eventTitle, startCalendar.getTime(), endCalendar.getTime(),
                        location, description, isPublic);
                newEvent.setListOfParticipants(currentlySelectedParticipants);
                if (eventObjectToEdit == null) {
                    // create new
                    // TODO: also get all participants that were selected.
                    LoginedAccount.getEventsManager().addEvent(newEvent);
                } else {
                    // edit
                    LoginedAccount.getEventsManager().editEvent(eventObjectToEdit, newEvent);
                }
                saveButton.setClickable(false);
                finish();
            }
        });
    }

    private void displayOldValues(Event event) {
        titleView.setText(event.getEventTitle());
        datePickerViewButton.setText(dateFormat.format(event.getStartTime()));
        startTimeViewButton.setText(timeFormat.format(event.getStartTime()));
        endTimeViewButton.setText(timeFormat.format(event.getEndTime()));
        locationView.setText(event.getLocation().getAddress());
        descriptionView.setText(event.getDescription());
        privateCheckBox.setChecked(!event.isPublic());
    }

    @Override
    protected void onPause() {
        super.onPause();
        // TODO: save the data entered: all textEdits, Times.
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: this should be called when user is returned from manage participants page.
        Log.v("Test", "onActivityResult is called; result is returned");
        if (requestCode == 995) {
            if (resultCode == RESULT_OK) {
                // TODO: after getting the lst of friends selected back, maybe for display use.
                ArrayList<Friend> newAddList = data.getParcelableArrayListExtra("listOfNewlyAddedFriends");
                ArrayList<Friend> deleteList = data.getParcelableArrayListExtra("listOfDeletedFriends");
                currentlySelectedParticipants.addAll(newAddList);
                currentlySelectedParticipants.removeAll(deleteList);

                Log.v("InfoEdit", "after modification, the event now has "
                        + currentlySelectedParticipants.size() + " participants");
            }
        }
    }
}
