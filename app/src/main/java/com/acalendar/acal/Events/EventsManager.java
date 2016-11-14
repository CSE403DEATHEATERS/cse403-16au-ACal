package com.acalendar.acal.Events;

import android.util.Log;

import com.acalendar.acal.InvokeAPISample;
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
    // TODO: EventsManager need to be created and saved in some other class.
    private final String userId; // TODO: should get the singleton userId in other classs
    PriorityQueue<Event> eventsQueue;
    private final static int initialSize = 100;

    public EventsManager(String uid) {
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
        loadAllEvents();
    }

    private void loadAllEvents() {
        // TODO: load all events data.
        // GET
        // userId
        // /getEvents
        // returned list<map<String, object>> each map is an Event, need to parse
    }

    public boolean addEvent(Event e) {
        // put data into request
        Map<String, Object> queryData = new HashMap<>();
        queryData.put("createTime", 482387295);  // fake data, to be deleted
        queryData.put("eventId", "fakeEventId000892471"); // fake data, to be deleted
        // actual data
        queryData.put("ownerId", userId);
        queryData.put("title", e.getEventTitle());
        queryData.put("startTime", e.getStartTime().getTime());
        queryData.put("endTime", e.getEndTime().getTime());
        queryData.put("description", e.getDescription());
        queryData.put("isPublic", e.isPublic());
        queryData.put("attendees", e.getListOfParticipantsUids());
        queryData.put("location", e.getLocation());
        String jsonObjectBody = new JSONObject(queryData).toString();
        // submit request
        Log.v("Test", "create new event query: " + jsonObjectBody);
        Map<String, String> query = new HashMap<>();
        String apiResponse = InvokeAPISample.invokeAPI("POST", "/createEvent", jsonObjectBody, query);
        HashMap<String,String> responseMap = new Gson().fromJson(apiResponse,
                new TypeToken<HashMap<String, String>>(){}.getType());

        // TODO: set the returned eid and createtime and return status
        //String eventId = responseMap.get("eventId");
        //long createTime = responseMap.get("createTime");
        Log.v("Test", responseMap.toString());

        boolean status = eventsQueue.add(e);
        return true;
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
