package com.acalendar.acal;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.acalendar.acal.Login.LoginedAccount;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    View view;
    ArrayAdapter adapter;

    //replace this local friends list to the db friends list
    ArrayList<String> friends;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_friends, container, false);
        friends =  getFriendListFromServer(LoginedAccount.getUserId());

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.da_item, friends);

        friendListView();
        addNewFriend();

        return view;
    }

    private ArrayList<String> getFriendListFromServer(String userId) {
        ArrayList<String> res = new ArrayList<>();
        // TODO: 11/15/2016  copy the list of friends from datebase to res by userId
        res.add("momo");
        res.add("Lisa");
//        Map<String, String> query = new HashMap<>();
//        query.put("userId", userId);
//        String apiResponse = InvokeAPISample.invokeAPI("GET", "/login", null, query);
//        Log.v("testApi", "response: " + apiResponse);
//        List<Map<String, Object>> list = new Gson().fromJson(apiResponse, new TypeToken<List<HashMap<String, Object>>>(){}.getType());
//        for (Map<String, Object> friendAccount: list) {
//            String fullName = friendAccount.get("firstname") + " "+ friendAccount.get("lastname");
//            res.add(fullName);
//        }
        return res;
    }

    private void addNewFriend() {
        Button add = (Button) view.findViewById(R.id.friends_add);
        final TextView userinputtext = (TextView) view.findViewById(R.id.friends_add_input);
        String userId = userinputtext.toString();

        // TODO: 11/15/2016 add friend by query by userId

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = (LayoutInflater.from(getActivity())).inflate(R.layout.dialog_frame, null);
                AlertDialog.Builder altdial = new AlertDialog.Builder(getActivity());
                altdial.setView(v);

                final EditText userInput = (EditText) v.findViewById(R.id.dialog_friend_add_input);

                altdial.setCancelable(true)
                        .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                userinputtext.setText(userInput.getText());
                            }
                        });
                Dialog dialog = altdial.create();
                dialog.setTitle("Enter the name of the person you want to add");
                dialog.show();
            }
        });
    }


    private void friendListView() {

        ListView listView = (ListView) view.findViewById(R.id.friends_list);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        SearchView search = (SearchView) view.findViewById(R.id.friend_search_view);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.firend_popup, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId())
        {
            case R.id.friend_delete:
                friends.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.friend_invite_event:

                // call event activity






                return true;
            default: return super.onContextItemSelected(item);

        }
    }

}