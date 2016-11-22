package com.acalendar.acal.Events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.acalendar.acal.Friend.Friend;
import com.acalendar.acal.Friend.FriendAdapter;
import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.R;

import java.util.ArrayList;

public class EditEventParticipantsActivity extends Activity {

    private View view;

    // TODO: Replace this local friends list to the db friends list
    private Button inviteButton;
    private FriendAdapter friendAdapter;
    private ArrayList<Friend> friendsList;
    private ArrayList<Friend> originalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_invite_friends);
        inviteButton = (Button) findViewById(R.id.friends_add_button);
        friendsList = (ArrayList<Friend>) LoginedAccount.getFriendManager().getListOfFriend();
        view = this.findViewById(android.R.id.content);
        friendAdapter = new FriendAdapter(this, R.layout.friend_list_item ,R.id.friend_name, friendsList);
        ListView listView = (ListView) findViewById(R.id.friends_list_inviting);
        listView.setAdapter(friendAdapter);

        Intent i = getIntent();
        originalList = i.getParcelableArrayListExtra("originalParticipantsList");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Friend friend = (Friend) parent.getItemAtPosition(position);
            }
        });

        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: send the selected friends back to previous page.
                Intent intent = new Intent();
                ArrayList<Friend> friendList = friendAdapter.getFriendList();
                ArrayList<Friend> newAddList = new ArrayList<>();
                ArrayList<Friend> deleteList = new ArrayList<>();

                for(int i=0;i<friendList.size();i++){
                    Friend friend = friendList.get(i);
                    if(friend.isSelected()){
                        if (!originalList.contains(friend)) {
                            newAddList.add(friend);
                            originalList.add(friend);
                        }
                    } else {
                        if (originalList.contains(friend)) {
                            deleteList.add(friend);
                            originalList.remove(friend);
                        }
                    }
                }
                intent.putParcelableArrayListExtra("listOfNewlyAddedFriends", newAddList);
                intent.putParcelableArrayListExtra("listOfDeletedFriends", deleteList);

                Log.v("EditEventParticipants", "number of friends removed from event is "
                        + deleteList.size());
                Log.v("EditEventParticipants", "number of friends added to event is "
                        + newAddList.size());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
