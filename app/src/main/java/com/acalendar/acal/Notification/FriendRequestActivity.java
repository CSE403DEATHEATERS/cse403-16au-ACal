package com.acalendar.acal.Notification;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.acalendar.acal.ApiResource;
import com.acalendar.acal.Friend.Friend;
import com.acalendar.acal.Friend.FriendAdapter;
import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.R;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class FriendRequestActivity extends Activity {

    FriendAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_request_activity);

        populateListView();

        acceptSelected();

        declineSelected();
    }



    private void populateListView() {


        ArrayList<Friend> friendsList = new ArrayList<>();


        //TODO:  11/15/2016 Replace the items below to db itmes
        LoginedAccount.getNotificationManager().refreshPendingFriends();
        friendsList.addAll(LoginedAccount.getNotificationManager().pendingFriends);


        adapter = new FriendAdapter(this, R.layout.friend_list_item ,R.id.friend_name, friendsList);
        ListView listView = (ListView) findViewById(R.id.friends_list_inviting);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Friend friend = (Friend) parent.getItemAtPosition(position);
            }
        });

    }

    private void acceptSelected() {
        Button inviteButton = (Button) findViewById(R.id.add_friend_button);
        inviteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<Friend> friendList = adapter.getFriendList();

                StringBuffer responseText = new StringBuffer();
                if (friendList.size() == 1)
                    responseText.append("You accepted friend request from:\n");
                else if (friendList.size() > 1) {
                    responseText.append("You accepted friend requests from:\n");
                }

//
//                for(int i=0;i<friendList.size();i++){
//                    Friend friend = friendList.get(i);
//                    if(friend.isSelected()) {
//                        responseText.append(friend.getName() + "\n");
//                        Map<String, String> addFriendQuery = new HashMap<String, String>();
//                        addFriendQuery.put("userId_1", LoginedAccount.getUserId());
//                        addFriendQuery.put("username", friend.getUsername());
//                        Map<String, Object> apiResponse = ApiResource.submitRequest(addFriendQuery, null, ApiResource.GET_REQUEST, ApiResource.REQUEST_ACEEPT_FRIEND);
//                        if (apiResponse.get("result") != null && apiResponse.get("result").equals("true")) {
//                            Log.v("Test", "accepted friend" + friend.getName());
//                            //TODO: remove friend from list
//                        }
//                    }
//                }

                Iterator<Friend> iterator = friendList.iterator();
                while (iterator.hasNext()) {
                    Friend friend = iterator.next();
                    if (friend.isSelected()) {
                        Map<String, String> addFriendQuery = new HashMap<String, String>();
                        addFriendQuery.put("userId_1", LoginedAccount.getUserId());
                        addFriendQuery.put("userId_2", friend.getUserId());
                        Map<String, Object> apiResponse = ApiResource.submitRequest(addFriendQuery, null, ApiResource.GET_REQUEST, ApiResource.REQUEST_ACEEPT_FRIEND);
                        if (apiResponse.get("result") != null && (boolean)apiResponse.get("result")) {
                            Log.v("Test", "accepted friend" + friend.getName());
                            //TODO: remove friend from list
                            responseText.append(friend.getName() + "\n");
                            iterator.remove();
                        }
                    }
                }
                LoginedAccount.getNotificationManager().refreshPendingFriends();

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();

            }
        });
    }

    private void declineSelected() {
        Button inviteButton = (Button) findViewById(R.id.decline_friend_button);
        inviteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<Friend> friendList = adapter.getFriendList();

                StringBuffer responseText = new StringBuffer();
                if (friendList.size() == 1)
                    responseText.append("You declined friend request from:\n");
                else if (friendList.size() > 1) {
                    responseText.append("You declined friend requests from:\n");
                }

                // TODO: acturally call API for this and remove this line
                ArrayList<Integer> toBeRmoved;
                Iterator<Friend> iterator = friendList.iterator();
                while (iterator.hasNext()) {
                    Friend friend = iterator.next();
                    if (friend.isSelected()) {
//                        Map<String, String> addFriendQuery = new HashMap<String, String>();
//                        addFriendQuery.put("userId_1", LoginedAccount.getUserId());
//                        addFriendQuery.put("userId_2", friend.getUserId());
//                        Map<String, Object> apiResponse = ApiResource.submitRequest(addFriendQuery, null, ApiResource.GET_REQUEST, ApiResource.REQUEST_REJECT_FRIEND);
//                        Log.v("Test", "decline response " + apiResponse);
//                        if (apiResponse.get("result") != null && (boolean)apiResponse.get("result")) {
//                            Log.v("Test", "accepted friend" + friend.getName());
//                            //TODO: remove friend from list
//                            responseText.append(friend.getName() + "\n");
//                            iterator.remove();
//                        }
                        responseText.append(friend.getName() + "\n");
                        iterator.remove();
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        });
    }

}
