package com.acalendar.acal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventPoolFragment extends Fragment {

    TextView textView;
    int year_x, month_x, day_x;

    public EventPoolFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_event_pool, container, false);

        CalendarView calendarView = (CalendarView) view.findViewById(R.id.event_pool_calendarView);
        textView = (TextView) view.findViewById(R.id.calendar_text_view);
        Button detail = (Button) view.findViewById((R.id.calendar_button));
        Button add = (Button) view.findViewById((R.id.event_add));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                 textView.setText(new StringBuilder()
                         .append("\nThe event(s) on ")
                         .append(year).append("-")
                         .append(month).append("-")
                         .append(day).append(" :"));
                year_x = year;
                month_x = month;
                day_x = day;
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }
}