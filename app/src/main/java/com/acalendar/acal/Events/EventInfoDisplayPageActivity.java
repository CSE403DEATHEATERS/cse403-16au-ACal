package com.acalendar.acal.Events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.acalendar.acal.R;


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
        // 1. extract everything from Bundle and display


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
                    String eid = eventInfoBundle.getString("eid");
                    // TODO: call back end to delete event id from local and aws DATABASE
                    // or should this be calling another service??
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

        // TODO: display timeline and all comments(message)

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // TODO: commit any changes. which exactly?
    }
}
