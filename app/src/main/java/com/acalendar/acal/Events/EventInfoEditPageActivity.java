package com.acalendar.acal.Events;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.acalendar.acal.R;

import java.util.Date;
import java.util.Locale;


public class EventInfoEditPageActivity  extends Activity {

    private Calendar javaCalendar;
    private EditText dateEditText;
    private Date datePicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_info_edit_page);
        // set up date picker dialog
        javaCalendar = Calendar.getInstance();
        dateEditText = (EditText) findViewById(R.id.dateEditText);

        final DatePickerDialog.OnDateSetListener datePickerListener =
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                javaCalendar.set(Calendar.YEAR, year);
                javaCalendar.set(Calendar.MONTH, monthOfYear);
                javaCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                datePicked = javaCalendar.getTime();
                System.out.print(datePicked);
                showDate();
            }
        };
        dateEditText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(EventInfoEditPageActivity.this,
                                datePickerListener,
                                javaCalendar.get(Calendar.YEAR),
                                javaCalendar.get(Calendar.MONTH),
                                javaCalendar.get(Calendar.DAY_OF_MONTH)
                                ).show();
                    }
                }
        );

        // TODO: if any get and display bundle data from previous activity

        // TODO: save button listener -> save & goes back to where it comes from!

        // TODO: share with friends -> goes to select Friends Page
    }

    private void showDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        dateEditText.setText(sdf.format(javaCalendar.getTime()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        // TODO: save the data entered: all textEdits, Times.
    }
}
