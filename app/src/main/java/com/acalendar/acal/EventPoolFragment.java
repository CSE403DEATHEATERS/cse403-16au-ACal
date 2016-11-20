package com.acalendar.acal;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.acalendar.acal.Events.EventInfoEditPageActivity;

import java.text.SimpleDateFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventPoolFragment extends Fragment {

    public EventPoolFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_event_pool, container, false);

        Button add = (Button) view.findViewById((R.id.event_add));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to edit page
                Intent intentToAddNew = new Intent(getActivity(),
                        EventInfoEditPageActivity.class);
                intentToAddNew.putExtra("AddNew", true);
                startActivityForResult(intentToAddNew, 0);
            }
        });

        return view;
    }
}