package com.acalendar.acal.Events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AllEventsInSingleDayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_in_single_day_page);

        LinearLayout eventsViewContainer =
                (LinearLayout) findViewById(R.id.eventsLinearLayoutScroll);

        // get, display, and set OnClick to all events from the date selected
        Intent intentRecieved = getIntent();
        long dateLongSelected = intentRecieved.getLongExtra("dateLongSelected", -1);
        Date dateObjectSelected = new Date(dateLongSelected);
        String key = EventsManager.dateToString(dateObjectSelected);
        Log.v("EventsInSingleDay", "the date we got is " + dateObjectSelected.toString()+
                " converted to key is " + key);
        List<Event> list = LoginedAccount.getEventsManager().getEventsInDate(key);
        Log.v("EventsInSingleDay", "all events in this date are " + list.toString());

        TextView title = (TextView) findViewById(R.id.eventsInSingleDayLabel);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        title.setText("Events on " + dateFormat.format(dateObjectSelected));
        for (Event e : list) {

            final String eid = e.getEventId();
            if (eid == null) {
                continue;
            }

            String eventTitle = e.getEventTitle();
            Button eventDisplay = (Button)getLayoutInflater().inflate(R.layout.event_button, null);
            eventDisplay.setId(eid.hashCode());
            eventDisplay.setText(eventTitle);

            eventDisplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentToViewSingleEvent = new Intent(AllEventsInSingleDayActivity.this,
                            EventInfoDisplayPageActivity.class);
                    intentToViewSingleEvent.putExtra("eventId", eid);
                    startActivityForResult(intentToViewSingleEvent, 79);
                }
            });
            eventsViewContainer.addView(eventDisplay);
            Log.v("DEBUG", "********************buttonID: " + eventDisplay.getId());
        }

        // TODO: can add an add button -> goes to edit page.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setContentView(R.layout.events_in_single_day_page);
        // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 79) {
            // came back from single eventInfoDisplay page, which may potentially delete an event
            if (resultCode == RESULT_OK) {
                int eventIdDeletedHashcode = (data.getStringExtra("eventIdDeleted")).hashCode();
                Log.v("AllEventsInSingleDay", "came back from display page, and event id " +
                        data.getStringExtra("eventIdDeleted") + " was deleted");
                Log.v("AllEventsSingleDay", "the button to be deleted is " + eventIdDeletedHashcode);
                Button eventView = (Button)findViewById(eventIdDeletedHashcode);
                try {
                    eventView.setVisibility(View.GONE);
                } catch (Exception e) {
                    // ideally this should not happen
                }
            }

            else if (resultCode == EventInfoDisplayPageActivity.EDITED) {
                int eventIdEditedHashcode = data.getStringExtra("eventIdEdited").hashCode();
                Button eventView = (Button) findViewById
                        (eventIdEditedHashcode);
                String newEventTitle = data.getStringExtra("newEventTitle");
                eventView.setText(newEventTitle);
                Log.v("AllEventsInSingleDay", "refreshed event " + newEventTitle);
            }
        }

    }
}
