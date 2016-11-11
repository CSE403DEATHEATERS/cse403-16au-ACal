package com.acalendar.acal.Events;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.acalendar.acal.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


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
        if (false) {  // TODO: if previous page gives event info

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
        // set up location picker

        // TODO: save button listener -> save & goes back to previous page! alert if needed

        // TODO: share with friends -> goes to select Friends Page
    }

    @Override
    protected void onPause() {
        super.onPause();
        // TODO: save the data entered: all textEdits, Times.
    }
}
