package com.acalendar.acal.Events;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.acalendar.acal.R;

import java.util.ArrayList;

public class InviteFriendsActivity extends Activity {

    View view;
    ArrayAdapter adapter;

    // Replace this local friends list to the db friends list
    ArrayList<String> friends;

    ArrayList<String> toBeInvited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_invite_friends);

        friends = new ArrayList<>();
        friends.add("Lisa");
        friends.add("Moo Moo");
        friends.add("Gao");
        friends.add("Snail");
        friends.add("Shen");
        friends.add("YaoZi");

        toBeInvited = new ArrayList<>();

        view = this.findViewById(android.R.id.content);

        adapter = new ArrayAdapter<>(InviteFriendsActivity.this, R.layout.invite_friend_item, R.id.friend_name, friends);

        populateListView();
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
                }else{
                    Log.v("test", tv.getText().toString() + " checked");
                    box.setChecked(true);
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
