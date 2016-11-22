package com.acalendar.acal.Friend;

import android.util.Log;

import com.acalendar.acal.ApiResource;
import com.acalendar.acal.Login.LoginedAccount;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class manages the friends features including adding friends,
 * getting all friends, etc.
 * It should manage a list of Accounts who are friends with loggedin user.
 */

public class FriendManager {
    private static List<Friend> listOfFriend;
    private static Map<String, Friend> uidToFriend;

    public FriendManager() {
        listOfFriend = new ArrayList<>();
        uidToFriend = new HashMap<>();
        // call get all friends list, parse responce map
        Map<String, String> query = new HashMap<>();
        query.put("userId", LoginedAccount.getUserId());
        Map<String, Object> apiResponse = ApiResource.submitRequest(
                query, null,
                ApiResource.GET_REQUEST,
                ApiResource.REQUEST_GET_FRIENDS);
        List<Map<String, String>> friendsResponse =
                (List) apiResponse.get("ACCEPT");

        if (friendsResponse != null && !friendsResponse.isEmpty()) {
            for (Map<String, String> friend : friendsResponse) {
                Friend thisFriend = new Friend(
                        friend.get("lastname"),
                        friend.get("firstname"),
                        friend.get("email"),
                        friend.get("username"),
                        friend.get("userId"));
                listOfFriend.add(thisFriend);
                uidToFriend.put(friend.get("userId"), thisFriend);
            }
        }
        // accept. pending(to be accepted), sent(to others)
    }

    public List<Friend> getListOfFriend() {
        return new ArrayList<>(listOfFriend);
    }

    public Friend getFriendbyUserId(String uid) {
        return this.uidToFriend.get(uid);
    }
    public boolean addFriend(String userInput) {
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(userInput);
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("userId_1", LoginedAccount.getUserId());
        if (m.matches()) {
            bodyMap.put("email", userInput);
        } else {
            bodyMap.put("username", userInput);
        }
        JSONObject jsonBody = new JSONObject(bodyMap);
        String body = jsonBody.toString();
        Map<String, Object> apiResponse = ApiResource.submitRequest(
                new HashMap<String, String>(), body,
                ApiResource.POST_REQUEST, ApiResource.REQUEST_ADD_FRIEND);
        if (apiResponse.get("result") != null) {
            if (apiResponse.get("result").equals("true")) {
                //TODO: give feedback message
            } else {
                //TODO: ask for input again
            }
        }
        return false;
    }

    public boolean acceptFriend(Friend f) {
        // uid_1, uid_2, result, true, post
        Map<String, String> addFriendQuery = new HashMap<>();
        addFriendQuery.put("userId_1", LoginedAccount.getUserId());
        addFriendQuery.put("userId_2", f.getUserId());
        Map<String, Object> apiResponse = ApiResource.submitRequest(
                addFriendQuery, null,
                ApiResource.GET_REQUEST,
                ApiResource.REQUEST_ACEEPT_FRIEND);
        if (apiResponse.get("result") == null) {
            return false;
        }
        Log.v("FriendManager", "accepted friend request from " + f.getName());
        return true;
    }

    public boolean deleteFriend() {
        return false;
    }

    public boolean declineFriend() {
        return false;
    }

}