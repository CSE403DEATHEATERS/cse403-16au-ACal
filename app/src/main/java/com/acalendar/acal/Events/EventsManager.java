package com.acalendar.acal.Events;

import android.util.Log;

import com.acalendar.acal.ApiResource;
import com.acalendar.acal.Friend.Friend;
import com.acalendar.acal.Login.Account;
import com.acalendar.acal.Login.LoginedAccount;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manage all events of this user. Can used to query events based on Date, add and delete events.
 */
public class EventsManager {
    private final String userId;
    private Map<String, List<Event>> eventMap;
    private Map<String, Event> idToEventMap;

    public EventsManager(Map<String, List<Map<String, Object>>> mapOfListofEventMaps) {
        userId = LoginedAccount.getCurrentUser().getUserId();
        eventMap = new HashMap<>();
        idToEventMap = new HashMap<>();
        parseAllEvents(mapOfListofEventMaps.get("ACCEPT"));
    }

    /*
     * reload all events into local event list
     */
    private void parseAllEvents(List<Map<String, Object>> listOfEventMaps) {
        Log.v("Test", "starting to parse all events got from db. size = " + listOfEventMaps.size());
        for (Map<String, Object> event : listOfEventMaps) {
            if (event.size() == 0) {
                Log.v("Test", "this object map is empty.");
                continue;
            }
            if (event.isEmpty()) {
                continue;
            }
            Event entry = parseEventFromMap(event);
            // put into map
            String key = dateToString(entry.getStartTime());
            if (!eventMap.containsKey(key)) {
                eventMap.put(key, new ArrayList<Event>());
            }
            // add to eventId to event Map
            if (!idToEventMap.containsKey(entry.getEventId())) {
                idToEventMap.put(entry.getEventId(), entry);
            }
            Log.v("Test", "parsing this object map, key parsed is " + key);
            Log.v("Test", "parsed entry is being added, status : " + eventMap.get(key).add(entry));
        }
    }

    private Event parseEventFromMap(Map<String, Object> event) {
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
        List<String> listOfParticipantsUids = (List<String>) event.get("attendees");
        for (String uid : listOfParticipantsUids) {
            entry.addParticipant(LoginedAccount.getFriendManager().getFriendbyUserId(uid));
        }
        return entry;
    }

    public boolean addEvent(Event e) {
        // put data into request
        Map<String, Object> queryData = new HashMap<>();
        queryData.put("ownerId", userId);
        if (e.getEventTitle() != null && !e.getEventTitle().isEmpty()) {
            queryData.put("title", e.getEventTitle());
        }
        queryData.put("startTime", e.getStartTime().getTime());
        Log.v("Adding event", "start of this event is " + e.getStartTime().toString());
        queryData.put("endTime", e.getEndTime().getTime());
        if (e.getDescription() != null && !e.getDescription().isEmpty()) {
            queryData.put("description", e.getDescription());
        }
        queryData.put("isPublic", e.isPublic());

        if (e.getListOfParticipantingFriendsUserIds() != null &&
                e.getListOfParticipantingFriendsUserIds().size() != 0) {
            queryData.put("attendees", e.getListOfParticipantingFriendsUserIds());
        }
        if (e.getLocation() != null) {
            queryData.put("location", e.getLocation().getInfo()); // Location
        }
        String jsonObjectBody = (new JSONObject(queryData)).toString();
        // submit request
        Map<String, String> query = new HashMap<>();
        Map<String, Object> responseMap = ApiResource.submitRequest(query, jsonObjectBody,
                ApiResource.POST_REQUEST,
                ApiResource.REQUEST_CREATE_EVENT);

        if (responseMap.isEmpty()) {
            Log.v("Adding event", "responseMap is empty, failed to add new event");
            return false;
        }
        String eid = (String) responseMap.get("eventId");
        Date createTime = new Date(((Double)responseMap.get("createTime")).longValue());
        e.setEventId(eid);
        e.setCreateTime(createTime);
        String key = dateToString(e.getStartTime());
        Log.v("Adding event", "*** this event entry will be added to key " +
                key + " event id is " + e.getEventId());
        if (!eventMap.containsKey(key)) {
            eventMap.put(key, new ArrayList<Event>());
        }
        boolean status = eventMap.get(key).add(e);
        if (!idToEventMap.containsKey(e.getEventId())) {
            idToEventMap.put(e.getEventId(), e);
        }
        return status;
    }

    public boolean editEvent(Event originalEvent, Event newEvent) {
        // TODO: diff 2 events, if different add the corresponding attribute to queryData
        Map<String, Object> queryData = new HashMap<>();
        queryData.put("eventId", originalEvent.getEventId());
        if (!originalEvent.getEventTitle().equals(newEvent.getEventTitle())) {
            queryData.put("title", newEvent.getEventTitle());
        }
        if (!originalEvent.getStartTime().equals(newEvent.getStartTime())) {
            queryData.put("startTime", newEvent.getStartTime().getTime());
        }
        if (!originalEvent.getEndTime().equals(newEvent.getEndTime())) {
            queryData.put("endTime", newEvent.getEndTime().getTime());
        }
        if (!originalEvent.getLocation().getAddress().equals(newEvent.getLocation().getAddress())) {
            // TODO: diff location
            Map<String, Object> location = newEvent.getLocation().getInfo();
            queryData.put("location", location);
        }
        if (!originalEvent.getDescription().equals(newEvent.getDescription())) {
            queryData.put("description", newEvent.getEndTime().getTime());
        }
        JSONObject jsonObject= new JSONObject(queryData);
        Map<String, Object> responceMap = ApiResource.submitRequest(
                new HashMap<String, String>(),
                jsonObject.toString(),
                ApiResource.POST_REQUEST,
                ApiResource.REQUEST_EDIT_EVENT);

        boolean success = (boolean)responceMap.get("result");
        if (!success) {
            return false;
        }
        newEvent.setEventId(originalEvent.getEventId());
        Log.v("EditEvent", "new eventid is set to be old event" + newEvent.getEventId());
        List<Event> list = this.eventMap.get(dateToString(newEvent.getStartTime()));
        list.remove(originalEvent);
        list.add(newEvent);
        this.idToEventMap.put(newEvent.getEventId(), newEvent);
        return true;
    }

    public boolean deleteEvent(String eventId) {
        // TODO: fix bug
        if (eventId == null) {
            Log.v("EventManager", "eventId passed in to delete is null.");
            return false;
        }
        Map<String, String> queryData = new HashMap<>();
        queryData.put("eventId", eventId);
        Map<String, Object> responceMap = ApiResource.submitRequest(queryData, null,
                ApiResource.GET_REQUEST,
                ApiResource.REQUEST_DELETE_EVENT);
        boolean success = (boolean)(responceMap.get("result"));
        Event eventToDelete = idToEventMap.get(eventId);
        if (!success) {
            return false;
        }
        this.idToEventMap.remove(eventId);
        this.eventMap.get(dateToString(eventToDelete.getStartTime())).remove(eventToDelete);
        return true;
    }

    public boolean editParticipants(ArrayList<Friend> addList, ArrayList<Friend> deleteList) {
        // take evenid, userid, listOfUserIdAdded, listOfUserIdDeleted(empty for now).
        // remove temporarily not available in front end but work in backend.

        return false;
    }

    public List<Event> getEventsInDate(String key) {
        List<Event> ret = eventMap.get(key);
        if (ret == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(ret);
    }

    public List<Event> getEventsInDate(Date eventDate) {
        String key = dateToString(eventDate);
        return getEventsInDate(key);
    }

    public Event getEventById(String eid) {
        return this.idToEventMap.get(eid);
    }
    public static String dateToString(Date startTime) {
        return startTime.getYear() + " "
                + startTime.getMonth() + " " + startTime.getDate();
    }

    // TODO: remove if not needed
    public void refreshAllEvents() {
        Map<String, String> query = new HashMap<>();
        query.put("userId", this.userId);
        Map<String, Object> apiResponse = ApiResource.submitRequest(query, null,
                ApiResource.GET_REQUEST, ApiResource.REQUEST_GET_EVENTS);
        List<Map<String, String>> acceptedEvents = (List)apiResponse.get("ACCEPT");
        List<Map<String, String>> pendingEvents = (List)apiResponse.get("PENDING");
    }

    // TODO: remove if not needed
    public void refreshAllAcceptedEvents() {
        Map<String, String> query = new HashMap<>();
        query.put("userId", this.userId);
        query.put("status", "ACCEPT");
        Map<String, Object> apiResponse = ApiResource.submitRequest(query, null,
                ApiResource.GET_REQUEST, ApiResource.REQUEST_GET_EVENTS);
        List<Map<String, Object>> acceptedEvents = (List)apiResponse.get("ACCEPT");
        eventMap.clear();
        idToEventMap.clear();
        parseAllEvents(acceptedEvents);
    }

    /***
     * Get all the dates on which this user has events scheduled.
     *
     * @return a list of Date objects
     */
    public List<Date> getAllDates() {
        List<Date> results = new ArrayList<Date>();
        for (String date : this.eventMap.keySet()) {
            Date coolDate = stringToDate(date);
            results.add(coolDate);
        }
        return results;
    }

    private static Date stringToDate(String date) {
        String[] splited = date.split(" ");
        int year = Integer.parseInt(splited[0]) - 100 + 2000;
        int month = Integer.parseInt(splited[1]);
        int day = Integer.parseInt(splited[2]);
        return new GregorianCalendar(year, month, day).getTime();
    }
}