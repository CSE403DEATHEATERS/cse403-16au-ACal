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
    public static final String REQUEST_GET_EVENTS = "/getEvents";  // userId, status
    public static final String REQUEST_GET_FRIENDS = "getFriends";
    public static final String REQUEST_ADD_FRIEND = "/addFriend";
    public static final String REQUEST_REJECT_FRIEND = "/rejectFriend";
    public static final String REQUEST_ACEEPT_FRIEND = "/acceptFriend";

    public static final String GET_REQUEST = "GET";
    public static final String POST_REQUEST = "POST";

    /**
     * This method send the given request to server side.
     *
     * @param queryData Map<String, Object>; key represents the database attribute, value is
     *                  the data you want to query.
     * @param requestMethod a String represents the type of this request. can be either GET_REQUEST or
     *                    POST_REQUEST. Method of this API call
     * @param requestName A String that is part of the request path, can be either one of the above
     *                    REQUEST_NAMES.
     * @return Map<String, Object> which key represents the name of the value. When retrieving data
     *          from this map, use casts to get the desired type.
     */
    public static Map<String, Object> submitRequest(Map<String, String> queryData, String requestBody,
                                                    String requestMethod, String requestName) {
        String jsonObjectBody = new JSONObject(queryData).toString();
        // submit request
        Log.v("Test", requestMethod + " : " + jsonObjectBody);
        String apiResponse = InvokeAPISample.invokeAPI(requestMethod, requestName, requestBody, queryData);
        HashMap<String, Object> responseMap = new Gson().fromJson(apiResponse,
                new TypeToken<HashMap<String, Object>>(){}.getType());
        return responseMap;
    }
}
