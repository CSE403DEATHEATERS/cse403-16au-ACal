package com.acalendar.acal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.acalendar.acal.Events.EventInfoEditPageActivity;
import com.acalendar.acal.Login.LoginedAccount;
import com.acalendar.acal.amazonaws.mobile.AWSMobileClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (LoginedAccount.getCurrentUser() != null) {
            LoginedAccount.logOut();
        }


        AWSMobileClient.initializeMobileClientIfNecessary(this.getApplicationContext());

        AWSMobileClient.initializeMobileClientIfNecessary(this.getApplicationContext());


        Intent FrontPageActivity = new Intent(this, com.acalendar.acal.Login.FrontPageActivity.class);
        startActivityForResult(FrontPageActivity, 1);

        setContentView(R.layout.activity_main);

//        Log.v("MainActivity.java", "onCreate(): LoginedAccount.getEventsManager " + LoginedAccount.getEventsManager());
//        Log.v("MainActivity.java", "onCreate(): LoginedAccount.isLoggedIn " + LoginedAccount.isLogedIn());
//        EventPoolFragment fragment = new EventPoolFragment();
//        android.support.v4.app.FragmentTransaction fragmentTransaction =
//                getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.commit();

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
            Intent NewEventActivity = new Intent(this, com.acalendar.acal.Notification.NewEventActivity.class);
            startActivityForResult(NewEventActivity, 2);
//        } else if (id == R.id.nav_notification) {
//            toolbar.setTitle("Notifications");
//            NotifiFragment fragment = new NotifiFragment();
//            android.support.v4.app.FragmentTransaction fragmentTransaction =
//                    getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, fragment);
//            fragmentTransaction.commit();
        } else if (id == R.id.nav_invite_friend) {
            toolbar.setTitle("New CalPals");
            Intent FriendRequestActivity = new Intent(this, com.acalendar.acal.Notification.FriendRequestActivity.class);
            startActivityForResult(FriendRequestActivity, 2);
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
        } else if (id == R.id.nav_sign_out) {
            LoginedAccount.logOut();
            Toast missmatch = Toast.makeText(MainActivity.this, "You Are Logged Out!", Toast.LENGTH_SHORT);
            missmatch.show();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
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

                Log.v("MainActivity.java", "onCreate(): LoginedAccount.getEventsManager " + LoginedAccount.getEventsManager());
                Log.v("MainActivity.java", "onCreate(): LoginedAccount.isLoggedIn " + LoginedAccount.isLogedIn());
            }
        }

        EventPoolFragment fragment = new EventPoolFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}