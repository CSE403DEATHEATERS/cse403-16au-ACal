package com.acalendar.acal.Events;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class EventMessagePageActivity extends Activity {

    private String eventId;
    private MessageAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts);

        Bundle bundle = getIntent().getExtras();
        eventId = bundle.getString("eventId");



        //      eventId = "7e541db9-0690-4638-9881-99e60ec2978f";
        // using eventId = "7e541db9-0690-4638-9881-99e60ec2978f" should display the following message
        /*
        Thu Dec 01 22:27:37 PST 2016
        user1
        hello

        Thu Dec 01 22:27:37 PST 2016
        user2
         hi
         .
         .
         .
         */



        mAdapter = new MessageAdapter(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<Date>());

        RecyclerView mMessages = (RecyclerView) findViewById(R.id.messagesList);


        LinearLayoutManager mManager = new LinearLayoutManager(this);
        mMessages.setHasFixedSize(false);
        mMessages.setLayoutManager(mManager);
        mMessages.setAdapter(mAdapter);


        sentMessage();
        populateMessage();


    }

    private void populateMessage() {
        Map<String, Object> messages = LoginedAccount.getEventsManager().refreshSingleEventMessages(eventId);

        mAdapter.clear();
        for (int i = 0; i < messages.size(); i++) {
            Map<String, Object> message = (Map<String, Object>) messages.get("" + i);
            String content = (String) message.get("content");
            Date createTime = new Date(((Double)message.get("createAt")).longValue());
            Log.v("time",createTime.toString() );
            String createBy = (String) message.get("userId");
            mAdapter.add(createBy, content, createTime );
        }
    }

    private void sentMessage() {

        Button mSendButton = (Button) findViewById(R.id.sendButton);
        final EditText mMessageEdit = (EditText) findViewById(R.id.messageEdit);
        final String mes = mMessageEdit.getText().toString();
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMessageEdit.getText().toString().equals("")){
                    Toast empty = Toast.makeText(EventMessagePageActivity.this, "Message cannot be empty", Toast.LENGTH_SHORT);
                    empty.show();
                    return;
                }
                if (LoginedAccount.getEventsManager().createEventMessage(eventId, LoginedAccount.getUserFullName(),mes )) {
                    populateMessage();
                } else {
                    Toast fail = Toast.makeText(EventMessagePageActivity.this, "Message not sent", Toast.LENGTH_SHORT);
                    fail.show();}
                mMessageEdit.setText("");
            }
        });
    }
}
