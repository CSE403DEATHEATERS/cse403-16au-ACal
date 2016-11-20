package com.acalendar.acal.Events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Displaying the single event's all information, including all comments,
 * and enable delete button, edit button, and add comment button.
 * A eventId to display should be passed in to this class.
 */
public class EventInfoDisplayPageActivity extends Activity {

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create layout, UI here
        setContentView(R.layout.event_info_display_page);
        // extract everything from Bundle and display
        Intent intentRecieved = getIntent();
        String eid = intentRecieved.getStringExtra("eventId");
        this.event = LoginedAccount.getEventsManager().getEventById(eid);
        UpdateTextViews(this.event);


        // 2. buttons:
        Button editButton = (Button) findViewById(R.id.EditButton);
        Button deleteButton = (Button) findViewById(R.id.DeleteButton);
        Button seeCommentButton = (Button) findViewById(R.id.SeeCommentsButton);

        editButton.setOnClickListener(
            new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // go to next edit page!!
                    Intent intentToEdit = new Intent(EventInfoDisplayPageActivity.this,
                                            EventInfoEditPageActivity.class);
                    intentToEdit.putExtra("eventObjectToEdit", event);
                    startActivity(intentToEdit);
                }
            }
        );

        deleteButton.setOnClickListener(
            new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // TODO: call back end to delete event id from local and aws DATABASE
                    // TODO: notify status of deletion: success/failed.
                    LoginedAccount.getEventsManager().deleteEvent(event.getEventId());
                }
            }
        );

        seeCommentButton.setOnClickListener(
            new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // TODO: go to eventCommentPage where user can view and add comments, stretch feature
                }
            }
        );
        // TODO: display all participants in some way.
    }

    private void UpdateTextViews(Event eventObject) {
        TextView titleView = (TextView) findViewById(R.id.eventTitleDisplayText);
        titleView.setText(event.getEventTitle());

        TextView descripView = (TextView) findViewById(R.id.DescriptionDisplayText);
        descripView.setText(eventObject.getDescription());

        TextView  locationView = (TextView) findViewById(R.id.LocationDisplayText);
        locationView.setText(eventObject.getLocation().getAddress());

        TextView startView = (TextView) findViewById(R.id.startTimeDisplayText);
        Date startTime = eventObject.getStartTime();
        Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        startView.setText(format.format(startTime));

        TextView endView = (TextView) findViewById(R.id.endTimeDisplayText);
        Date endTime = eventObject.getEndTime();
        endView.setText(format.format(endTime));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
