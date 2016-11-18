package com.acalendar.acal.Friend;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.acalendar.acal.R;

import java.util.ArrayList;

public class InviteFriendsActivity extends Activity {

    FriendAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_friend);

        populateListView();

        activatedInvite();
    }



    private void populateListView() {


        ArrayList<Friend> friendsList = new ArrayList<>();


        //  11/15/2016 Replace the items below to db itmes
        friendsList.add( new Friend("Lisa", false));
        friendsList.add( new Friend("Moo Moo", false));
        friendsList.add( new Friend("Gao", false));
        friendsList.add( new Friend("Qin", false));
        friendsList.add( new Friend("Yao", false));



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

    private void activatedInvite() {
        Button inviteButton = (Button) findViewById(R.id.findSelected);
        inviteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Friend> friendList = adapter.getFriendList();
                for(int i=0;i<friendList.size();i++){
                    Friend friend = friendList.get(i);
                    if(friend.isSelected()){
                        responseText.append("\n" + friend.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });
    }
}
