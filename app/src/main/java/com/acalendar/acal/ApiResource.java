package com.acalendar.acal;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Please use this class to interact with server API call. It contains all resource you need to query,
 * including requestNames, requestType, method for calling the API
 */

public class ApiResource {

    public static final String REQUEST_LOGIN = "/login";
    public static final String REQUEST_SIGNUP = "/signup";
    public static final String REQUEST_CREATE_EVENT = "/createEvent";
    public static final String REQUEST_EDIT_EVENT = "/editEvent";  // eventId required, no create time
    public static final String REQUEST_GET_EVENT = "/getEvents";  // userId, status
    public static final String REQUEST_ADD_FRIEND = "/addFriend";
    public static final String REQUEST_REJECT_FRIEND = "/rejectFriend";
    public static final String REQUEST_ACEEPT_FRIEND = "/acceptFriend";


    /**
     * This method send the given request to server side.
     *
     * @param queryData Map<String, Object>; key represents the database attribute, value is
     *                  the data you want to query.
     * @param requestType a String represents the type of this request. can be either GET_REQUEST or
     *                    POST_REQUEST
     * @param requestName A String that is part of the request path, can be either one of the above
     *                    REQUEST_NAMES.
     * @return Map<String, Object> which key represents the name of the value. When retrieving data
     *          from this map, use casts to get the desired type.
     */
    public static Map<String, Object> submitRequest(Map<String, Object> queryData, String requestType,
                                                     String requestName) {
        String jsonObjectBody = new JSONObject(queryData).toString();
        // submit request
        Log.v("Test", requestType + " : " + jsonObjectBody);
        Map<String, String> query = new HashMap<>();
        String apiResponse = InvokeAPISample.invokeAPI(requestType, requestName, jsonObjectBody, query);
        HashMap<String, Object> responseMap = new Gson().fromJson(apiResponse,
                new TypeToken<HashMap<String, Object>>(){}.getType());
        return responseMap;
    }
}
