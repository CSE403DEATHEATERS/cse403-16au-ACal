package com.acalendar.acal.Events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.acalendar.acal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditEventParticipantsActivity extends Activity {

    public static final String USERID = "userId";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String LASTNAME = "lastname";
    public static final String FIRSTNAME = "firstname";

    private View view;
    private ArrayAdapter adapter;

    // Replace this local friends list to the db friends list
    private Button inviteButton;
    private ArrayList<Map<String, String>> friendsMapList;
    private ArrayList<String> friendsUidSelectedList;
    private Map<String, String> usernameToUidMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_invite_friends);
        inviteButton = (Button) findViewById(R.id.friends_add_button);
        friendsUidSelectedList = new ArrayList<>();
        usernameToUidMap = new HashMap<>();

        // get all friends related data
        getFriendsData();

        // build friendNames list to be displayed
        List<String> friendsNamesToBeDisplayed = new ArrayList<>();
        for (Map<String, String> userinfo : friendsMapList) {
            String username = userinfo.get(USERNAME);
            friendsNamesToBeDisplayed.add(username);
        }

        view = this.findViewById(android.R.id.content);
        adapter = new ArrayAdapter<>(EditEventParticipantsActivity.this,
                R.layout.friend_list_item, R.id.friend_name, friendsNamesToBeDisplayed);
        populateListView();

        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: send the selected friends back to previous page.
                Intent intent = new Intent();
                intent.putStringArrayListExtra("listOfSelectedUid", friendsUidSelectedList);
                Log.v("Test", "selected uid" +
                        intent.getStringArrayListExtra("listOfSelectedUid").toString());
                setResult(Activity.RESULT_OK, intent);
                // finishActivity(0);
                finish();
            }
        });
    }

    private void getFriendsData() {
        // TODO: hard code now since the back end is not ready. Call backend when ready
        // TODO: get all friends, in String format.
        // TODO: call some string parser to parse String into data structure: List<Map> each Map represents a single user(friends)
        friendsMapList = new ArrayList<>();
        HashMap<String, String> f1 = new HashMap<>();
        f1.put(USERID, "001");
        f1.put(USERNAME, "MOMO");
        HashMap<String, String> f2 = new HashMap<>();
        f2.put(USERID, "002");
        f2.put(USERNAME, "ZOE");
        HashMap<String, String> f3 = new HashMap<>();
        f3.put(USERID, "003");
        f3.put(USERNAME, "LISA");
        friendsMapList.add(f1);
        friendsMapList.add(f2);
        friendsMapList.add(f3);
        // TODO: build user name to uid map
        usernameToUidMap.put("MOMO", "001");
        usernameToUidMap.put("ZOE", "002");
        usernameToUidMap.put("LISA", "003");
    }

    private void populateListView() {

        ListView listview = (ListView) view.findViewById(R.id.friends_list_inviting);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listview.setAdapter(adapter);
        listview.setItemsCanFocus(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                CheckBox box = (CheckBox) arg1.findViewById(R.id.checkBox1);
                TextView tv = (TextView) arg1.findViewById(R.id.friend_name);
                if(box.isChecked()){
                    Log.v("test", tv.getText().toString() + " unchecked");
                    box.setChecked(false);
                    friendsUidSelectedList.remove(usernameToUidMap.get(tv.getText().toString()));
                }else{
                    Log.v("test", tv.getText().toString() + " checked");
                    box.setChecked(true);
                    friendsUidSelectedList.add(usernameToUidMap.get(tv.getText().toString()));
                }
            }
        });

        registerForContextMenu(listview);
        SearchView search = (SearchView) view.findViewById(R.id.friend_search_view_inviting);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });
    }
}