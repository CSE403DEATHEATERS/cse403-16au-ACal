package com.acalendar.acal.Events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.R;
import com.google.android.gms.drive.realtime.internal.event.ObjectChangedDetails;
import com.google.android.gms.vision.text.Text;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Displaying the single event's all information, including all comments,
 * and enable delete button, edit button, and add comment button.
 */
public class EventInfoDisplayPageActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create layout, UI here
        setContentView(R.layout.event_info_display_page);
        final Bundle eventInfoBundle = getIntent().getExtras();
        // extract everything from Bundle and display
        UpdateTextViews(eventInfoBundle);


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
                    intentToEdit.putExtras(eventInfoBundle);
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
                }
            }
        );

        seeCommentButton.setOnClickListener(
            new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // TODO: go to eventCommentPage where user can view and add comments
                }
            }
        );
    }

    private void UpdateTextViews(Bundle eventInfoBundle) {
        TextView titleView = (TextView) findViewById(R.id.eventTitleDisplayText);
        Object eid = eventInfoBundle.get("eventId");
        Map<String, Object> eventMap = LoginedAccount.getEventsManager().getEventById(eid.toString());
        if (eventMap.get("title") != null)
            titleView.setText(eventMap.get("title").toString());
        TextView descripView = (TextView) findViewById(R.id.DescriptionDisplayText);
        if (eventMap.get("description") != null)
            descripView.setText(eventMap.get("description").toString());
        TextView  locationView = (TextView) findViewById(R.id.LocationDisplayText);
        if (eventMap.get("location") != null) {
            Map<String, String> add = new HashMap<>();
            Map<String,String> map = (Map<String, String>) eventMap.get("location");
            locationView.setText(map.get("address"));
        }
        TextView startView = (TextView) findViewById(R.id.startTimeDisplayText);
        if (eventMap.get("startTime") != null) {
            Date startTime = new Date(((Double)eventMap.get("startTime")).longValue());
            Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            startView.setText(format.format(startTime).toString());
        }
        TextView endView = (TextView) findViewById(R.id.endTimeDisplayText);
        if (eventMap.get("endTime") != null) {
            Date endTime = new Date(((Double)eventMap.get("endTime")).longValue());
            Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            endView.setText(format.format(endTime).toString());
        }
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
