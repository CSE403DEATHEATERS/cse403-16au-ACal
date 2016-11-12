package com.acalendar.acal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublicEventsPoolFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;


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
        String[] items = new String[]{"Party", "Convert"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        eventTypeFilterDropdown.setAdapter(adapter);

        Spinner startTimeFilterDropdown = (Spinner)view.findViewById(R.id. publicEventsPoolLocationFilterSpinner);
        String[] items2 = new String[]{"Seattle", "Bellevue", "Lake City"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items2);
        startTimeFilterDropdown.setAdapter(adapter2);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
