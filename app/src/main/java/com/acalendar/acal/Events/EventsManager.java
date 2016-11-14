package com.acalendar.acal.Events;

import android.util.Log;

import com.acalendar.acal.InvokeAPISample;
import com.acalendar.acal.Login.Account;
import com.acalendar.acal.Login.LoginedAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manage all events of this user. Can used to query events based on Date, add and delete events.
 */
public class EventsManager {
    private final String userId;
    Map<String, List<Event>> eventMap;

    public EventsManager(List<Map<String, Object>> listOfEventMaps) {
        userId = LoginedAccount.getCurrentUser().getUserId();
        eventMap = new HashMap<>();
        parseAllEvents(listOfEventMaps);
    }

    private void parseAllEvents(List<Map<String, Object>> listOfEventMaps) {
        for (Map<String, Object> event : listOfEventMaps) {
            String eid = (String) event.get("eventId");
            String ownerId = (String) event.get("ownerId");
            Date createTime = new Date(((long)event.get("createTime")));
            String eventTitle = (String) event.get("title");
            Date startTime = new Date(((long)event.get("startTime")));
            Date endTime = new Date(((long)event.get("endTime")));
            String description = (String) event.get("description");
            Boolean isPublic = (Boolean) event.get("isPublic");
            Map<String, Object> locationMap = (Map<String, Object>) event.get("location");
            Location location = Location.parseLocation(locationMap);

            Event entry = new Event(eid, ownerId, createTime,
                    eventTitle, startTime, endTime, null, description, isPublic);
            // set parsed locations
            entry.setLocation(location);
            // TODO: set list of participants uids
            List<String> listOfParticipantsUids = (List<String>) event.get("attendees");
            for (String uid : listOfParticipantsUids) {
                entry.addParticipant(new Account(uid, null,null,null,null));
            }
            // put into map
            String key = dateToString(startTime);
            if (!eventMap.containsKey(key)) {
                eventMap.put(key, new ArrayList<Event>());
            }
            eventMap.get(key).add(entry); // TODO: sort eventList
        }
    }

    public boolean addEvent(Event e) {
        // put data into request
        Map<String, Object> queryData = new HashMap<>();
        queryData.put("ownerId", userId);
        if (e.getEventTitle() != null && !e.getEventTitle().isEmpty()) {
            queryData.put("title", e.getEventTitle());
        }
        queryData.put("startTime", e.getStartTime().getTime());
        queryData.put("endTime", e.getEndTime().getTime());
        if (e.getDescription() != null && !e.getDescription().isEmpty()) {
            queryData.put("description", e.getDescription());
        }
        queryData.put("isPublic", e.isPublic());
        if (e.getListOfParticipantsUids() != null && e.getListOfParticipantsUids().size() != 0) {
            queryData.put("attendees", e.getListOfParticipantsUids());
        }
        if (e.getLocation() != null) {
            queryData.put("location", e.getLocation().getInfo()); // Location
        }
        Log.v("Test", "create new event query: " + queryData.toString());
        String jsonObjectBody = (new JSONObject(queryData)).toString();
        // submit request
        Log.v("Test", "create new event query: " + jsonObjectBody);
        Map<String, String> query = new HashMap<>();
        String apiResponse = InvokeAPISample.invokeAPI("POST", "/createEvent", jsonObjectBody, query);

        Map<String, Object> responseMap = new Gson().fromJson(apiResponse,
                new TypeToken<HashMap<String, Object>>(){}.getType());

        if (responseMap.isEmpty()) {
            return false;
        }
        Log.v("Test", responseMap.toString());

        String eid = (String) responseMap.get("eventId");
        Date createTime = new Date((long)(double)responseMap.get("createTime"));
        Log.v("Test", "event id was " + e.getEventId());
        e.setEventId(eid);
        e.setCreateTime(createTime);
        String key = dateToString(e.getStartTime());
        if (!eventMap.containsKey(key)) {
            eventMap.put(key, new ArrayList<Event>());
        }
        boolean status = eventMap.get(key).add(e);
        return status;
    }

    public boolean deleteEvent(String eventId) {
        // TODO: remove event in local
        // TODO: post request to server for deleting the event
        return false;
    }

    public List<Event> getEventsInDate(Date eventDate) {
        // TODO: loop through the event queue to find events in given date: Binary Searchs
        return null;
    }

    private String dateToString(Date startTime) {
        return startTime.getYear() + " "
                + startTime.getMonth() + " " + startTime.getDate();
    }

}
