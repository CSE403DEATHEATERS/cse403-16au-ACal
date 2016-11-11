package com.acalendar.acal.SideMenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.acalendar.acal.R;

public class PublicEventsPoolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_events_pool);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        setTitle("Public Events Pool");
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner eventTypeFilterDropdown = (Spinner)findViewById(R.id.publicEventsPoolEventTypeFilterSpinner);
        String[] items = new String[]{"Party", "Convert"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        eventTypeFilterDropdown.setAdapter(adapter);

        Spinner startTimeFilterDropdown = (Spinner)findViewById(R.id. publicEventsPoolLocationFilterSpinner);
        String[] items2 = new String[]{"Seattle", "Bellevue", "Lake City"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        startTimeFilterDropdown.setAdapter(adapter2);






    }



}
