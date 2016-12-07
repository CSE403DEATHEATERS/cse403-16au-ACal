package com.acalendar.acal.Notification;


import com.acalendar.acal.ApiResource;
import com.acalendar.acal.Friend.Friend;
import com.acalendar.acal.Login.LoginedAccount;
import com.amazonaws.mobileconnectors.amazonmobileanalytics.InitializationException;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
        Map<String, Object> apiResponse = ApiResource.submitRequest(query, null, ApiResource.GET_REQUEST, ApiResource.REQUEST_GET_PENDING_EVENTS);
        if (apiResponse.get("PENDING") != null)
            this.pendingEvents = parseInvitation((List)apiResponse.get("PENDING"));
        else
            this.pendingEvents = new ArrayList<>();
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

    private List<Invitation> parseInvitation(List<Map<String, Object>> mapList) {
        List<Invitation> invitationList = new ArrayList<>();
        for (Map<String, Object> eventMap : mapList) {
            if (eventMap.isEmpty()) {
                continue;
            }
            String title = "";
            if (eventMap.get("title") != null) {
                title = eventMap.get("title").toString();
            }
            String eventId = "";
            if (eventMap.get("eventId") != null) {
                eventId = eventMap.get("eventId").toString();
            }
            String startTime = "";
            if (eventMap.get("startTime") != null) {
                String start = eventMap.get("startTime").toString();
                double startDouble = Double.parseDouble(start);
                Long startLong = (long)startDouble;
                Date startDate = new Date(startLong);
                Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US);
                startTime = format.format(startDate);
            }
            String ownerName = "";
            if (eventMap.get("ownerName") != null) {
                ownerName = eventMap.get("ownerName").toString();
//                ownerName = "Ruoyu Mo";
            }
            String location = "";
            if (eventMap.get("location") != null) {
                Map<String, String> locationMap = (Map<String, String>)eventMap.get("location");
                location = locationMap.get("address");
            }
            Invitation invitation = new Invitation(title, ownerName, startTime, location, eventId, false);
            invitationList.add(invitation);
        }
        return invitationList;
    }

}