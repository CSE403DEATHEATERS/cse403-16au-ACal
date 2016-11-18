package com.acalendar.acal;


import android.app.Dialog;
import android.content.Context;
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
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;

import com.acalendar.acal.Friend.Friend;
import com.acalendar.acal.Login.LoginedAccount;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    View view;
    ArrayAdapter adapter;

    //replace this local friends list to the db friends list
    ArrayList<Friend> friends;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_friends, container, false);
        getFriendListFromServer(LoginedAccount.getUserId());

        adapter = new ArrayAdapter<Friend>(getActivity(), R.layout.da_item, friends);

        friendListView();
        addNewFriend();

        return view;
    }

    private void getFriendListFromServer(String userId) {
        friends = new ArrayList<Friend>();
        Map<String, String> query = new HashMap<>();
        query.put("userId", userId);
        Map<String, Object> apiResponse = ApiResource.submitRequest(query, null, ApiResource.GET_REQUEST, ApiResource.REQUEST_GET_FRIENDS);
        List<Map<String, String>> friendsResponse = (List) apiResponse.get("friends");
//        Map<String, String> query = new HashMap<>();
//        query.put("userId", userId);
//        String apiResponse = InvokeAPISample.invokeAPI("GET", "/login", null, query);
//        Log.v("testApi", "response: " + apiResponse);
//        List<Map<String, Object>> list = new Gson().fromJson(apiResponse, new TypeToken<List<HashMap<String, Object>>>(){}.getType());
//        for (Map<String, Object> friendAccount: list) {
//            String fullName = friendAccount.get("firstname") + " "+ friendAccount.get("lastname");
//            res.add(fullName);
//        }
        if (!friendsResponse.isEmpty()) {
            friends.clear();
            for (Map<String, String> friend : friendsResponse) {
                Friend thisFriend = new Friend(friend.get("firstname") + " " + friend.get("lastname"), friend.get("email"), friend.get("username"), friend.get("userId"));
                friends.add(thisFriend);
            }
        }
    }

    private void addNewFriend() {
        Button add = (Button) view.findViewById(R.id.friends_add);
        final TextView userinputtext = (TextView) view.findViewById(R.id.friends_add_input);
        String userId = userinputtext.toString();



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = (LayoutInflater.from(getActivity())).inflate(R.layout.dialog_frame, null);
                AlertDialog.Builder altdial = new AlertDialog.Builder(getActivity());
                altdial.setView(v);

                final EditText userInputView = (EditText) v.findViewById(R.id.dialog_friend_add_input);
                final String userInput = userInputView.getText().toString();
                Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
                Matcher m = p.matcher(userInput);
                Map<String, String> bodyMap = new HashMap<String, String>();
                bodyMap.put("userId_1", LoginedAccount.getUserId());
                if (m.matches()) {
                    bodyMap.put("email", userInput);
                } else {
                    bodyMap.put("username", userInput);
                }
                JSONObject jsonBody = new JSONObject(bodyMap);
                String body = jsonBody.toString();
                Map<String, Object> apiResponse = ApiResource.submitRequest(new HashMap<String, String>(), body, ApiResource.POST_REQUEST, ApiResource.REQUEST_ADD_FRIEND);
                if (apiResponse.get("result") != null) {
                    if (apiResponse.get("result").equals("true")) {
                        //TODO: give feedback message
                    } else {
                        //TODO: ask for input again
                    }
                }
                altdial.setCancelable(true)
                        .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                userinputtext.setText(userInput);
                            }
                        });
                Dialog dialog = altdial.create();
                dialog.setTitle("Enter the username or email of the person you want to add");
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