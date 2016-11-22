package com.acalendar.acal.Notification;


import com.acalendar.acal.ApiResource;
import com.acalendar.acal.Friend.Friend;
import com.acalendar.acal.Login.LoginedAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationManager {
    List<Invitation> pendingEvents;
    List<Friend> pendingFriends;

    public NotificationManager() {
        //TODO: get all notifications
        refreshPendingEvents();
        refreshPendingFriends();
    }

    public void refreshPendingEvents() {
        Map<String, String> query = new HashMap<>();
        query.put("userId", LoginedAccount.getUserId());
        query.put("status", "PENDING");
        Map<String, Object> apiResponse = ApiResource.submitRequest(query, null, ApiResource.GET_REQUEST, ApiResource.REQUEST_GET_EVENTS);
        if (apiResponse.get("PENDING") != null)
            this.pendingEvents = ((List)apiResponse.get("PENDING"));
    }

    public void refreshPendingFriends() {
        Map<String, String> query = new HashMap<>();
        query.put("userId", LoginedAccount.getUserId());
        query.put("status", "PENDING");
        Map<String, Object> apiResponse = ApiResource.submitRequest(query, null, ApiResource.GET_REQUEST, ApiResource.REQUEST_GET_FRIENDS);
        if (apiResponse.get("PENDING") != null)
            this.pendingFriends = parseFriendList((List)apiResponse.get("PENDING"));
        else
            this.pendingFriends = new ArrayList<>();
    }

    private List<Friend> parseFriendList(List<Map<String, String>> mapList) {
        List<Friend> friendList = new ArrayList<>();
        for (Map<String, String> friendMap : mapList) {
            String firstname = "";
            if (friendMap.get("firstname") != null) {
                firstname = friendMap.get("firstname");
            }
            String lastname = "";
            if (friendMap.get("lastname") != null) {
                lastname = friendMap.get("lastname");
            }
            String username = "";
            if (friendMap.get("username") != null) {
                username = friendMap.get("username");
            }
            String email = "";
            if (friendMap.get("email") != null) {
                email = friendMap.get("email");
            }
            String userId = "";
            if (friendMap.get("userId") != null) {
                userId = friendMap.get("userId");
            }

            Friend friend = new Friend(lastname, firstname, email, username, userId);
            friendList.add(friend);
        }
        return friendList;
    }

}