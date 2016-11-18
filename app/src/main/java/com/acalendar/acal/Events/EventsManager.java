package com.acalendar.acal.Events;

import android.util.Log;

import com.acalendar.acal.ApiResource;
import com.acalendar.acal.InvokeAPISample;
import com.acalendar.acal.Login.Account;
import com.acalendar.acal.Login.LoginedAccount;
import com.google.gson.Gson;
import com.google.gson.internal.ObjectConstructor;
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
    Map<String, Map<String, Object>> idToEventMap;

    public EventsManager(List<Map<String, Object>> listOfEventMaps) {
        userId = LoginedAccount.getCurrentUser().getUserId();
        eventMap = new HashMap<>();
        parseAllEvents(listOfEventMaps);
        idToEventMap = new HashMap<>();
        parseEventsWithId(listOfEventMaps);
    }

    /*
     * reload all events into local event list
     */
    private void parseAllEvents(List<Map<String, Object>> listOfEventMaps) {
        for (Map<String, Object> event : listOfEventMaps) {
            if (event.size() == 0) {
                continue;
            }
            String eid = (String) event.get("eventId");
            String ownerId = (String) event.get("ownerId");
            Date createTime = new Date(((Double)event.get("createTime")).longValue());
            String eventTitle = (String) event.get("title");
            Date startTime = new Date(((Double)event.get("startTime")).longValue());
            Date endTime = new Date(((Double)event.get("endTime")).longValue());
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
            if (!idToEventMap.containsKey(eid)) {
                idToEventMap.put(eid, event);
            }
            Log.v("Test", "key of this event is " + key);
            Log.v("Test", "entry added status : " + eventMap.get(key).add(entry));
            // TODO: sort eventList;
        }
    }

    private void parseEventsWithId(List<Map<String, Object>> listOfEventMaps) {
        for (Map<String, Object> event : listOfEventMaps) {
            if (event.size() == 0)
                continue;
            idToEventMap.put(event.get("eventId").toString(), event);
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
        Log.v("Test", "start of this event is " + e.getStartTime().toString());
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
        String jsonObjectBody = (new JSONObject(queryData)).toString();
        // submit request
        Log.v("Test", "create new event query: " + jsonObjectBody);
        Map<String, String> query = new HashMap<>();
        String apiResponse = InvokeAPISample.invokeAPI("POST", "/createEvent", jsonObjectBody, query);

        Map<String, Object> responseMap = new Gson().fromJson(apiResponse,
                new TypeToken<HashMap<String, Object>>(){}.getType());

        if (responseMap.isEmpty()) {
            Log.v("Test", "responseMap is empty, failed to add new event");
            return false;
        }
        Log.v("Test", "responce map is " + responseMap.toString());

        String eid = (String) responseMap.get("eventId");
        Date createTime = new Date(((Double)responseMap.get("createTime")).longValue());
        e.setEventId(eid);
        e.setCreateTime(createTime);
        String key = dateToString(e.getStartTime());
        Log.v("Test", "************************key gonna be added is " + key);
        if (!eventMap.containsKey(key)) {
            eventMap.put(key, new ArrayList<Event>());
        }
        boolean status = eventMap.get(key).add(e);
        if (!idToEventMap.containsKey(e.getEventId())) {
            idToEventMap.put(e.getEventId(), responseMap);
        }
        return status;
    }

    public boolean editEvent(Event originalEvent, Event newEvent) {
        // TODO: diff 2 events, if different add the corresponding attribute to queryData
        return false;
    }

    public boolean deleteEvent(String eventId) {
        // TODO: remove event in local
        // TODO: post request to server for deleting the event
        return false;
    }

    public List<Event> getEventsInDate(String key) {
        List<Event> ret = eventMap.get(key);
        if (ret == null) {
            return new ArrayList<>();
        }
        return ret;
    }

    public List<Event> getEventsInDate(Date eventDate) {
        String key = dateToString(eventDate);
        return getEventsInDate(key);
    }

    public Map<String, Object> getEventById(String eid) {
        return this.idToEventMap.get(eid);
    }
    public static String dateToString(Date startTime) {
        return startTime.getYear() + " "
                + startTime.getMonth() + " " + startTime.getDate();
    }

    public void refreshAllEvents() {
        Map<String, String> query = new HashMap<>();
        query.put("userId", this.userId);
        Map<String, Object> apiResponse = ApiResource.submitRequest(query, null, ApiResource.GET_REQUEST, ApiResource.REQUEST_GET_EVENTS);
        List<Map<String, String>> acceptedEvents = (List)apiResponse.get("ACCEPT");
        List<Map<String, String>> pendingEvents = (List)apiResponse.get("PENDING");

    }

    public void refreshAllAcceptedEvents() {
        Map<String, String> query = new HashMap<>();
        query.put("userId", this.userId);
        query.put("status", "ACCEPT");
        Map<String, Object> apiResponse = ApiResource.submitRequest(query, null, ApiResource.GET_REQUEST, ApiResource.REQUEST_GET_EVENTS);
        List<Map<String, Object>> acceptedEvents = (List)apiResponse.get("ACCEPT");
        eventMap.clear();
        idToEventMap.clear();
        parseAllEvents(acceptedEvents);

    }

}
