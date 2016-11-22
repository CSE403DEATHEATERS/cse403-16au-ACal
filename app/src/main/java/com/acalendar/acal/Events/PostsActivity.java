package com.acalendar.acal.Events;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.acalendar.acal.R;

import java.util.ArrayList;

public class PostsActivity extends Activity {

    private Button mSendButton;
    private EditText mMessageEdit;

    private RecyclerView mMessages;
    private RecyclerView.LayoutManager mManager;
    private MessageAdapter madapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts);

        // TODO: 11/21/2016 replace related user and message with db message
        ArrayList<String> user = new ArrayList<String>();
        user.add("momo");
        user.add("lisa");
        user.add("yaozi");
        user.add("yaozi");
        ArrayList<String> message= new ArrayList<String>();
        message.add("created this event");
        message.add("join event");
        message.add("join event");
        message.add("leave event");

        mSendButton = (Button) findViewById(R.id.sendButton);
        mMessageEdit = (EditText) findViewById(R.id.messageEdit);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 11/21/2016 request post a message, need to check certificate
                madapter.add(mMessageEdit.getText().toString());

                mMessageEdit.setText("");
            }
        });

        mMessages = (RecyclerView) findViewById(R.id.messagesList);
        madapter = new MessageAdapter( user, message);

        mManager = new LinearLayoutManager(this);

        mMessages.setHasFixedSize(false);
        mMessages.setLayoutManager(mManager);
        mMessages.setAdapter(madapter);
    }
}
