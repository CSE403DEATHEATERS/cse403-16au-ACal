package com.acalendar.acal.Events;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.acalendar.acal.ApiResource;
import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostsActivity extends Activity {

    private EditText mMessageEdit;
    private String eventId;
    private MessageAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts);

        Bundle bundle = getIntent().getExtras();
        eventId = bundle.getString("eventId");
        mAdapter = new MessageAdapter(new ArrayList<String>(), new ArrayList<String>());

        RecyclerView mMessages = (RecyclerView) findViewById(R.id.messagesList);


        LinearLayoutManager mManager = new LinearLayoutManager(this);
        mMessages.setHasFixedSize(false);
        mMessages.setLayoutManager(mManager);
        mMessages.setAdapter(mAdapter);

        populateMessage();

        sentMessage();


    }

    private void populateMessage() {
        Map<String, String> getRequest = new HashMap<>();
        getRequest.put("eventId", eventId);
        // TODO: 12/1/2016 call api
        Map<String, Object> messages = ApiResource.submitRequest(getRequest, null, ApiResource.GET_REQUEST, ApiResource.REQUEST_GET_MESSAGE_EVENT);
 //       Log.v("testApi", "response: " + messages);
        if (!messages.isEmpty()) {
            for (int i = 0; i < messages.size(); i++) {
                String value = (String) messages.get("" + i);
                String[] token = value.split("\\s+");
                Long createAt = Long.parseLong(token[0]);
                String createBy = token[1];
                String content = token[2];
                mAdapter.add(createBy, content);
            }
        }
    }

    private void sentMessage() {

        Button mSendButton = (Button) findViewById(R.id.sendButton);
        mMessageEdit = (EditText) findViewById(R.id.messageEdit);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> sentRequest = new HashMap<>();
                sentRequest.put("eventId", eventId);
                sentRequest.put("userId", LoginedAccount.getUserFullName());
                sentRequest.put("content", mMessageEdit.getText().toString());
                mMessageEdit.setText("");

                // TODO: 12/1/2016  call api
                Map<String, Object> res = ApiResource.submitRequest(sentRequest, null, ApiResource.POST_REQUEST, ApiResource.REQUEST_POST_MESSAGE_EVENT);

                if ((boolean)res.get("result")) {
                    mAdapter.clear();
                    populateMessage();
                }
            }
        });
    }
}
