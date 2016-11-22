package com.acalendar.acal.Notification;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.acalendar.acal.ApiResource;
import com.acalendar.acal.Friend.Friend;
import com.acalendar.acal.Friend.FriendAdapter;
import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


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

        acceptSelected();

        declineSelected();
    }



    private void populateListView() {


        ArrayList<Invitation> invitationsList = new ArrayList<>();
        LoginedAccount.getEventsManager().refreshAllAcceptedEvents();
        invitationsList.addAll(LoginedAccount.getNotificationManager().pendingEvents);

        invitationsList.add(new Invitation("Doctor Strange", "Tong Shen", "10/10/2016", "Northgate", "", false));

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

    private void acceptSelected() {
        Button inviteButton = (Button) findViewById(R.id.join_event_button);
        inviteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<Invitation> invitationList = adapter.getInvitationList();

                StringBuffer responseText = new StringBuffer();
                if (invitationList.size() == 1)
                    responseText.append("You joined event:\n");
                else if (invitationList.size() > 1) {
                    responseText.append("You joined events:\n");
                }

                Iterator<Invitation> iterator = invitationList.iterator();
                while (iterator.hasNext()) {
                    Invitation invitation = iterator.next();
                    if (invitation.isSelected()) {
                        Map<String, String> joinEventQuery = new HashMap<String, String>();
                        joinEventQuery.put("userId", LoginedAccount.getUserId());
                        joinEventQuery.put("eventId", invitation.getEventId());
                        Map<String, Object> apiResponse = ApiResource.submitRequest(joinEventQuery, null, ApiResource.GET_REQUEST, ApiResource.REQUEST_JOIN_EVENT);
                        if (apiResponse.get("result") != null && (boolean)apiResponse.get("result")) {
                            Log.v("Test", "joined event" + invitation.getTitle());
                            //TODO: remove friend from list
                            responseText.append(invitation.getTitle() + "\n");
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
        Button inviteButton = (Button) findViewById(R.id.decline_event_button);
        inviteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<Invitation> invitationList = adapter.getInvitationList();

                StringBuffer responseText = new StringBuffer();
                if (invitationList.size() == 1)
                    responseText.append("You declined invitation:\n");
                else if (invitationList.size() > 1) {
                    responseText.append("You declined invitations:\n");
                }

                // TODO: acturally call API for this and remove this line
                Iterator<Invitation> iterator = invitationList.iterator();
                while (iterator.hasNext()) {
                    Invitation invitation = iterator.next();
                    if (invitation.isSelected()) {
                        Map<String, String> declineEventQuery = new HashMap<String, String>();
                        declineEventQuery.put("userId", LoginedAccount.getUserId());
                        declineEventQuery.put("eventId", invitation.getEventId());
                        Map<String, Object> apiResponse = ApiResource.submitRequest(declineEventQuery, null, ApiResource.GET_REQUEST, ApiResource.REQUEST_DECLINE_EVENT);
                        if (apiResponse.get("result") != null && (boolean)apiResponse.get("result")) {
                            Log.v("Test", "declined event" + invitation.getTitle());
                            //TODO: remove friend from list
                            responseText.append(invitation.getTitle() + "\n");
                            iterator.remove();
                        }
                    }

                }


                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        });
    }

}
