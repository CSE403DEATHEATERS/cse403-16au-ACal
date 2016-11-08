package com.acalendar.acal.SideMenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acalendar.acal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicEventsPoolFragment extends Fragment {


    public PublicEventsPoolFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_public_events_pool, container, false);
    }

}
