package com.acalendar.acal.Events;

import android.util.Log;

import com.acalendar.acal.ApiResource;
import com.acalendar.acal.InvokeAPISample;
import com.acalendar.acal.Login.Account;
import com.acalendar.acal.Login.LoginedAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Manage all events of this user. Can used to query events based on Date, add and delete events.
 */
public class EventsManager {
    private final String userId;
    PriorityQueue<Event> eventsQueue;
    private final static int initialSize = 100;

    public EventsManager(List<Map<String, Object>> listOfEventMaps) {
        userId = LoginedAccount.getCurrentUser().getUserId();
        Comparator<Event> eventComparator = new Comparator<Event>() {
            @Override
            public int compare(Event lhs, Event rhs) {
                Date lhsStartTime = lhs.getStartTime();
                Date rhsStartTime = rhs.getStartTime();
                return lhsStartTime.compareTo(rhsStartTime);
            }
        };
        eventsQueue = new PriorityQueue<>(initialSize, eventComparator);
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

            // TODO: list of participants uids
            List<String> listOfParticipantsUids = (List<String>) event.get("attendees");
            for (String uid : listOfParticipantsUids) {
                entry.addParticipant(new Account(uid, null,null,null,null));
            }



            this.eventsQueue.add(entry);
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
            queryData.put("location", e.getLocation()); // Location
        }

        String jsonObjectBody = (new JSONObject(queryData)).toString();
        // submit request
        Log.v("Test", "create new event query: " + jsonObjectBody);
        Map<String, String> query = new HashMap<>();
        String apiResponse = InvokeAPISample.invokeAPI("POST", "/createEvent", jsonObjectBody, query);

        Map<String, Object> responseMap = new Gson().fromJson(apiResponse,
                new TypeToken<HashMap<String, Object>>(){}.getType());

        // TODO: set the returned eid and createtime and return status
        if (responseMap.isEmpty()) {
            return false;
        }
        Log.v("Test", responseMap.toString());

        String eid = (String) responseMap.get("eventId");
        Date createTime = new Date((long) responseMap.get("createTime"));
        Log.v("Test", "event id was " + e.getEventId());
        e.setEventId(eid);
        e.setCreateTime(createTime);
        boolean status = eventsQueue.add(e);
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

}
