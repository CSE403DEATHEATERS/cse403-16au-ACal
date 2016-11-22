package com.acalendar.acal.Notification;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.acalendar.acal.Friend.Friend;
import com.acalendar.acal.Friend.FriendAdapter;
import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewEventActivity extends Activity {
    InvitationAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_event);

        populateListView();
    }



    private void populateListView() {


        ArrayList<Invitation> invitationsList = new ArrayList<>();
        invitationsList.addAll(LoginedAccount.getNotificationManager().pendingEvents);

        invitationsList.add(new Invitation("Doctor Strange", "Tong Shen", "10/10/2016", "Northgate", false));

        adapter = new InvitationAdapter(this, R.layout.invitation_item, R.id.invitation, invitationsList);
        ListView listView = (ListView) findViewById(R.id.invitation_list_view);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Invitation invitation = (Invitation) parent.getItemAtPosition(position);
            }
        });

    }


}
