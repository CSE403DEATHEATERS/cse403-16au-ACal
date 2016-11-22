package com.acalendar.acal;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.acalendar.acal.Events.AllEventsInSingleDayActivity;
import com.acalendar.acal.Events.EventInfoEditPageActivity;
import com.acalendar.acal.Login.LoginedAccount;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventPoolFragment extends Fragment {

    private CaldroidFragment caldroidFragment;

    public EventPoolFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
        caldroidFragment.setArguments(args);
        android.support.v4.app.FragmentTransaction t = getFragmentManager().beginTransaction();
        t.replace(R.id.event_pool_calendarView, caldroidFragment);
        t.commit();

        if (LoginedAccount.isLogedIn()) {
            getEvent();
        }

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


    public void getEvent() {
        // TODO:
        // for every date that contains event, change its background as done above
        List<Date> datesToMark = LoginedAccount.getEventsManager().getAllDates();
        System.out.println("DatesToMark SIZE " + datesToMark.size());
        for (Date date : datesToMark) {
            Log.v("Mark date on calendar", "Date to mark is " + date);

            ColorDrawable blue = new ColorDrawable();
            blue.setColor(0xdd1565C0);
            caldroidFragment.setBackgroundDrawableForDate(blue, date);
        }

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                // on select start a new activity that displays all events on that day
                Intent intentToViewAll = new Intent(getActivity(),
                        AllEventsInSingleDayActivity.class);
                intentToViewAll.putExtra("dateLongSelected", date.getTime());
                startActivity(intentToViewAll);
            }

        };

        caldroidFragment.setCaldroidListener(listener);
    }
}