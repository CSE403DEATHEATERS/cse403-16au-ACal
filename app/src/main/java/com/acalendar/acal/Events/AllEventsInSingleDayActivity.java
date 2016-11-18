package com.acalendar.acal.Events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.acalendar.acal.Email.EmailManager;
import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.R;

import java.util.List;

// TODO: may be combined with main activity -> fragment
public class AllEventsInSingleDayActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginedAccount.getEventsManager().refreshAllAcceptedEvents();
        setContentView(R.layout.events_in_single_day_page);
        LinearLayout eventsViewContainer =
                (LinearLayout) findViewById(R.id.eventsLinearLayoutScroll);

        // get, display, and set OnClick to all events from the date selected
        Intent intentRecievved = getIntent();
        String key = intentRecievved.getStringExtra("dateSelected");
        Log.v("Test", "the date we got is " + key);
        List<Event> list = LoginedAccount.getEventsManager().getEventsInDate(key);
        Log.v("Test", "all events in this date are " + list.toString());
        for (Event e : list) {
            final String eid = e.getEventId();
            String eventTitle = e.getEventTitle();
            Button eventDisplay = new Button(this);
            eventDisplay.setText("Event: " + eventTitle);
            eventDisplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentToViewSingleEvent = new Intent(AllEventsInSingleDayActivity.this,
                            EventInfoDisplayPageActivity.class);
                    intentToViewSingleEvent.putExtra("eventId", eid);
                    startActivity(intentToViewSingleEvent);
                }
            });
            // TODO: set appearence of this button.
            eventsViewContainer.addView(eventDisplay);
        }
        // TODO: add button -> goes to edit page
    }
}
