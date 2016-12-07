package com.acalendar.acal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.acalendar.acal.Friend.Friend;
import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.Notification.Invitation;
import com.acalendar.acal.Notification.InvitationAdapter;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublicEventsPoolFragment extends Fragment {
//    private GoogleMap mMap;
    InvitationAdapter invitationAdapter;


    public PublicEventsPoolFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_public_events_pool, container, false);

//        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        getActivity().setTitle("Public Events Pool");

        Spinner eventTypeFilterDropdown = (Spinner) view.findViewById(R.id.publicEventsPoolEventTypeFilterSpinner);
        String[] items = new String[]{"Party", "Concert"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        eventTypeFilterDropdown.setAdapter(adapter);
//
//        Spinner startTimeFilterDropdown = (Spinner)view.findViewById(R.id. publicEventsPoolCitySpinner);
//        String[] items2 = new String[]{"Seattle", "Bellevue", "Lake City"};
//        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items2);
//        startTimeFilterDropdown.setAdapter(adapter2);


        ArrayList<Invitation> invitationsList = new ArrayList<>();

        // query to get all public events
        Map<String, String> query = new HashMap<String, String>();
        Map<String, Object> response = ApiResource.submitRequest(query, null, ApiResource.GET_REQUEST, ApiResource.REQUEST_PUBLIC_EVENT_POOL);
        if (response.containsKey("result")) {
            List<Map<String, Object>> publicEvents = (List) response.get("result");
            invitationsList = parseInvitation(publicEvents);
        }


        invitationAdapter = new InvitationAdapter(getActivity(), R.layout.invitation_item, R.id.invitation, invitationsList);

        ListView listView = (ListView) view.findViewById(R.id.event_pool_list_view);

        listView.setAdapter(invitationAdapter);

        Button join = (Button) view.findViewById(R.id.publicEventsPoolSearchButton);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Invitation> invitationsList = invitationAdapter.getInvitationList();

                StringBuffer responseText = new StringBuffer();
                responseText.append("You joined: \n");

                Iterator<Invitation> iterator = invitationsList.iterator();
                while (iterator.hasNext()) {
                    Invitation inv = iterator.next();
                    if (inv.isSelected()) {
                        responseText.append(inv.getTitle() + "\n");
                        iterator.remove();
                    }
                }
                Toast.makeText(getContext(),
                        responseText, Toast.LENGTH_LONG).show();
                invitationAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    private void joinEvent(Button join) {


//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
    }

    private ArrayList<Invitation> parseInvitation(List<Map<String, Object>> mapList) {
        ArrayList<Invitation> invitationList = new ArrayList<>();
        for (Map<String, Object> eventMap : mapList) {
            if (eventMap.isEmpty()) {
                continue;
            }
            String title = "";
            if (eventMap.get("title") != null) {
                title = eventMap.get("title").toString();
            }
            String eventId = "";
            if (eventMap.get("eventId") != null) {
                eventId = eventMap.get("eventId").toString();
            }
            String startTime = "";
            if (eventMap.get("startTime") != null) {
                String start = eventMap.get("startTime").toString();
                double startDouble = Double.parseDouble(start);
                Long startLong = (long)startDouble;
                Date startDate = new Date(startLong);
                Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US);
                startTime = format.format(startDate);
            }
//            String ownerName = "";
//            if (eventMap.get("ownerName") != null) {
//                ownerName = eventMap.get("ownerName").toString();
////                ownerName = "Ruoyu Mo";
//            }
            String location = "";
            if (eventMap.get("location") != null) {
                Map<String, String> locationMap = (Map<String, String>)eventMap.get("location");
                location = locationMap.get("address");
            }
            Invitation invitation = new Invitation(title, "", startTime, location, eventId, false);
            invitationList.add(invitation);
        }
        return invitationList;
    }

}
