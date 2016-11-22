package com.acalendar.acal.Events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.acalendar.acal.Friend.Friend;
import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


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
        final Intent intentRecieved = getIntent();
        String eid = intentRecieved.getStringExtra("eventId");
        this.event = LoginedAccount.getEventsManager().getEventById(eid);
        UpdateTextViews(this.event);


        // 2. buttons:
        Button editButton = (Button) findViewById(R.id.EditButton);
        Button deleteButton = (Button) findViewById(R.id.DeleteButton);
        Button seeCommentButton = (Button) findViewById(R.id.SeeCommentsButton);
        Button manageParticipantsButton = (Button) findViewById(R.id.manageParticipantButton);
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
                    boolean deleteStatus =
                            LoginedAccount.getEventsManager().deleteEvent(event.getEventId());
                    // TODO: if false, alert.
                    // return to the previous activity to removed event.
                    Intent intentGoBackToAllEvents = new Intent();
                    intentGoBackToAllEvents.putExtra("eventIdDeleted", event.getEventId());
                    setResult(RESULT_OK, intentGoBackToAllEvents);
                    finish();
                }
            }
        );

        manageParticipantsButton.setOnClickListener(
            new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intentToFriendCheckBoxPage = new Intent(
                            EventInfoDisplayPageActivity.this, EditEventParticipantsActivity.class);
                    intentToFriendCheckBoxPage.putParcelableArrayListExtra(
                            "originalParticipantsList",
                            (ArrayList<? extends Parcelable>) event.getListOfParticipantingFriends());
                    startActivityForResult(intentToFriendCheckBoxPage, 998);
                }
            }
        );

        seeCommentButton.setOnClickListener(
            new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // TODO: go to eventCommentPage where user can view and add comments
                    Intent posts = new Intent(EventInfoDisplayPageActivity.this, PostsActivity.class);
                    EventInfoDisplayPageActivity.this.startActivity(posts);
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
        String locationText = "";
        if (eventObject.getLocation() != null) {
            locationText = eventObject.getLocation().getAddress();
        }
        locationView.setText(locationText);

        TextView startView = (TextView) findViewById(R.id.startTimeDisplayText);
        Date startTime = eventObject.getStartTime();
        Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US);
        startView.setText(format.format(startTime));

        TextView endView = (TextView) findViewById(R.id.endTimeDisplayText);
        Date endTime = eventObject.getEndTime();
        endView.setText(format.format(endTime));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("Test", "onActivityResult is called; result is returned");
        if (requestCode == 998) {
            if (resultCode == RESULT_OK) {
                ArrayList<Friend> newAddList = data.getParcelableArrayListExtra("listOfNewlyAddedFriends");
                ArrayList<Friend> deleteList = data.getParcelableArrayListExtra("listOfDeletedFriends");
                boolean result =
                        LoginedAccount.getEventsManager().editParticipants(newAddList, deleteList);
                // TODO: display current List
//                currentlySelectedParticipants.addAll(newAddList);
//                currentlySelectedParticipants.removeAll(deleteList);
                Log.v("InfoDisplay", "a list of " + newAddList.size() + " is newly add to the event");
                Log.v("InfoDisplay", "a list of " + deleteList.size() + " is removed from the event");
            }
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
