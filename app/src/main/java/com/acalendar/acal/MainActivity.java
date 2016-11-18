package com.acalendar.acal;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.acalendar.acal.Events.AllEventsInSingleDayActivity;
import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.amazonaws.mobile.AWSMobileClient;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        AWSMobileClient.initializeMobileClientIfNecessary(this.getApplicationContext());

        AWSMobileClient.initializeMobileClientIfNecessary(this.getApplicationContext());


        Intent FrontPageActivity = new Intent(this, com.acalendar.acal.Login.FrontPageActivity.class);
        startActivityForResult(FrontPageActivity, 1);



        setContentView(R.layout.activity_main);

        System.out.println("In MainActivity.onCreate: LoginedAccount.getEventsManager " + LoginedAccount.getEventsManager());
        System.out.println("In MainActivity.onCreate: LoginedAccount.isLoggedIn " + LoginedAccount.isLogedIn());
        EventPoolFragment fragment = new EventPoolFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_event_pool) {
            toolbar.setTitle("Events");
            EventPoolFragment fragment = new EventPoolFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_friends) {
            toolbar.setTitle("CalPals");
            FriendsFragment fragment = new FriendsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        }
        else if (id == R.id.nav_edit_profile) {
            toolbar.setTitle("Edit Profile");
            ProfileFragment fragment = new ProfileFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_new_event) {
            toolbar.setTitle("New Events");
            NewEventFragment fragment = new NewEventFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_notification) {
            toolbar.setTitle("Notifications");
            NotifiFragment fragment = new NotifiFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_new_friend) {
            toolbar.setTitle("New CalPals");
            NewFriend fragment = new NewFriend();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_settings) {
            toolbar.setTitle("Settings");
            SettingFragment fragment = new SettingFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_public_events_pool) {
            toolbar.setTitle("Public Events Pool");
            PublicEventsPoolFragment fragment = new PublicEventsPoolFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                TextView profileView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.UserFullName);
                profileView.setText(LoginedAccount.getUserFullName());
                TextView emailView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.AccountInfo);
                emailView.setText(LoginedAccount.getEmail());
            }
        }

        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
        caldroidFragment.setArguments(args);
        android.support.v4.app.FragmentTransaction t = MainActivity.this.getSupportFragmentManager().beginTransaction();
        t.replace(R.id.event_pool_calendarView, caldroidFragment);
        t.commit();

        System.out.println("In MainActivity.onActivityResult: LoginedAccount.getEventsManager " + LoginedAccount.getEventsManager());
        System.out.println("In MainActivity.onActivityResult: LoginedAccount.isLoggedIn " + LoginedAccount.isLogedIn());

        // TODO:
        // for every date that contains event, change its background as done above
        List<Date> datesToMark = LoginedAccount.getEventsManager().getAllDates();
        System.out.println("DatesToMark SIZE " + datesToMark.size());
        for (Date date : datesToMark) {
            System.out.println("LALALALALALALA " + date);
            ColorDrawable blue = new ColorDrawable();
            blue.setColor(0xdd1565C0);
            caldroidFragment.setBackgroundDrawableForDate(blue, date);
        }

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                // TODO:
                // on select start a new activity that displays all events on that day

                Intent intentToDisplayEventsForADay = new Intent(MainActivity.this,
                        AllEventsInSingleDayActivity.class);
                startActivityForResult(intentToDisplayEventsForADay, 0);

            }

        };

        caldroidFragment.setCaldroidListener(listener);
    }
}