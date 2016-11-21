package com.acalendar.acal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.acalendar.acal.Notify.EventsNotifiAdapter;
import com.acalendar.acal.Notify.FriendsNotifiAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotifiFragment extends Fragment {

    private View view;

    public NotifiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_notifi, container, false);

        notiEventsView();
        notiFriendsView();
        return view;
    }

    private void notiEventsView() {

        ArrayList<String> notifyEventsList = new ArrayList<>();
        notifyEventsList.add("Momo invite you to event1");
        notifyEventsList.add("Momo invite you to event2");
        notifyEventsList.add("Lisa invite you to event1");

        EventsNotifiAdapter adapter = new EventsNotifiAdapter(getActivity(), R.layout.notifi_events_item ,R.id.noti_events_item, notifyEventsList);


        ListView listView = (ListView) view.findViewById(R.id.noti_events_listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });

    }

    private void notiFriendsView() {
        ArrayList<String> notifyEventsList = new ArrayList<>();
        notifyEventsList.add("Momo wants to be you friend");
        notifyEventsList.add("Lisa wants to be you friend");

        FriendsNotifiAdapter adapter = new FriendsNotifiAdapter(getActivity(), R.layout.notifi_friends_item ,R.id.noti_friend_item, notifyEventsList);


        ListView listView = (ListView) view.findViewById(R.id.noti_friends_listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
    }

}
