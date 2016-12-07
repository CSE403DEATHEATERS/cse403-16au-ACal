package com.acalendar.acal.Events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acalendar.acal.Friend.Friend;
import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Displaying the single event's all information, including all comments,
 * and enable delete button, edit button, and add comment button.
 * A eventId to display should be passed in to this class.
 */
public class EventInfoDisplayPageActivity extends Activity {

    private Event event;
    public static final int EDITED = 1003;
    private boolean edited = false;
    private List<Friend> currentlySelectedParticipants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create layout, UI here
        setContentView(R.layout.event_info_display_page);
        // extract everything from Bundle and display
        final Intent intentRecieved = getIntent();
        final String eid = intentRecieved.getStringExtra("eventId");
        this.event = LoginedAccount.getEventsManager().getEventById(eid);
        this.currentlySelectedParticipants = this.event.getListOfParticipantingFriends();

        Log.v("EventInfoDisplay", "how many friends are in this event?? " +
            this.currentlySelectedParticipants.size());

        UpdateTextViews(this.event);
        updateParticipantsListView(this.currentlySelectedParticipants);


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
                    startActivityForResult(intentToEdit, 991);
                }
            }
        );

        deleteButton.setOnClickListener(
            new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String idToBeDeleted = event.getEventId();
                    Log.v("InfoDisplay", "delete was pressed, following event will be deleted "
                            + idToBeDeleted);
                    boolean deleteStatus =
                            LoginedAccount.getEventsManager().deleteEvent(idToBeDeleted);
                    // TODO: if false, alert.
                    // return to the previous activity to removed event.

                    Intent intentGoBackToAllEvents = new Intent();
                    intentGoBackToAllEvents.putExtra("eventIdDeleted", idToBeDeleted);
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
                            (ArrayList<Friend>)event.getListOfParticipantingFriends());
                    startActivityForResult(intentToFriendCheckBoxPage, 998);
                }
            }
        );

        seeCommentButton.setOnClickListener(
            new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent posts = new Intent(EventInfoDisplayPageActivity.this, EventMessagePageActivity.class);
                    posts.putExtra("eventId", eid);
                    EventInfoDisplayPageActivity.this.startActivity(posts);
                }
            }
        );
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

    private void updateParticipantsListView(List<Friend> listF) {
        LinearLayout allParticipantsLayout = (LinearLayout)findViewById(R.id.allParticipantsLayout);
        TextView textViewForNoParticipants = new TextView(this);
        textViewForNoParticipants.setText("There is currently 0 participants in this event");
        if (listF != null) {
            allParticipantsLayout.removeAllViews();
            if (!listF.isEmpty()) {
                // now display
                for (Friend f : listF) {
                    TextView newText = new TextView(this);
                    newText.setText(f.getName());
                    allParticipantsLayout.addView(newText);
                }
            } else {
                allParticipantsLayout.addView(textViewForNoParticipants);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 998) {
            Log.v("EventInfoDisplay", "came back from manage participants");
            if (resultCode == RESULT_OK) {
                ArrayList<Friend> newAddList = data.getParcelableArrayListExtra("listOfNewlyAddedFriends");
                ArrayList<Friend> deleteList = data.getParcelableArrayListExtra("listOfDeletedFriends");
                boolean result =
                        LoginedAccount.getEventsManager().editParticipants(
                                this.event.getEventId(), newAddList, deleteList);
                if (result) {  // alert some message if failed
                    currentlySelectedParticipants.addAll(newAddList);
                    currentlySelectedParticipants.removeAll(deleteList);
                    Log.v("InfoDisplay", "Current participants: "
                            + this.currentlySelectedParticipants.size());
                    // display/update current List
                    updateParticipantsListView(this.currentlySelectedParticipants);
                }
            }

        } else if (requestCode == 991) {
            Log.v("EventInfoDisplay", "came back from edit info, should now refresh all info");
            if (resultCode == RESULT_OK) {
                Event eventAfterEdit = data.getParcelableExtra("eventAfterEdit");
                this.event = eventAfterEdit;  // now this thing should have everything including the id.
                Log.v("eventInfoDisplay", "eventAfterEdit has eventId" + this.event.getEventId());
                // got the edited version, need to refresh now.
                UpdateTextViews(this.event);
                Log.v("EventInfoDisplay", "displaying the new event");
                this.edited = true;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (this.edited && this.event != null) {
            Log.v("EventInfoDisplay", "going back to AllEventsSingleDay, " +
                    "while the viewed event being edited, " +
                    this.event.getEventTitle());
            Intent intent = new Intent();
            // send the eventId back, and also the new event title.
            intent.putExtra("eventIdEdited", this.event.getEventId());
            intent.putExtra("newEventTitle", this.event.getEventTitle());
            setResult(EDITED, intent);
        }
        super.onBackPressed();
    }
}
