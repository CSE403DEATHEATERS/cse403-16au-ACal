package com.acalendar.acal.Login;

import android.util.Log;

import com.acalendar.acal.ApiResource;
import com.acalendar.acal.Events.EventsManager;
import com.acalendar.acal.InvokeAPISample;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginedAccount {
    private static Account user;
    private static EventsManager eventsManager;

    public static Account getCurrentUser() {
        if (user == null) {
            return null;
        }
        return new Account(user);
    }

    public static void logIn(String username, String password) {
        if (user != null) {
            return;
        }
        Map<String, String> query = new HashMap<>();
        query.put("username", username);
        query.put("password", password);
        Map<String, Object> map = ApiResource.submitRequest(query, null, ApiResource.GET_REQUEST, ApiResource.REQUEST_LOGIN);
        Log.v("testApi", "response: " + map);
        if (!map.isEmpty()) {
            Map<String, String> account = (Map<String, String>) map.get("account");
            user = new Account(account.get("userId"), account.get("username"), account.get("email"), account.get("lastname"), account.get("firstname"));
            eventsManager = new EventsManager((List<Map<String, Object>>) map.get("event"));
        }
    }

    public static boolean signUp(String body) {
        Log.v("Test", "query: " + body);
        Map<String, String> query = new HashMap<>();
        Map<String,Object> map = ApiResource.submitRequest(query, body, ApiResource.POST_REQUEST, ApiResource.REQUEST_SIGNUP);
//        String apiResponse = InvokeAPISample.invokeAPI("POST", "/signup", body, query);
        Log.v("testApi", "response: " + map);
        if (map.isEmpty()) {
            return false;

        }
        return true;
    }

    public static void logOut() {
        user = null;
        eventsManager = null;
    }

    public static boolean isLogedIn() {
        return user != null;
    }

    public static String getUserName() {
        return user.getUsername();
    }

    public static String getUserFullName() {
        return user.getFirstname() + " " + user.getLastname();
    }

    public static String getEmail() {
        return user.getEmail();
    }

    public static EventsManager getEventsManager() {
        return eventsManager;
    }

    public static String getUserId() {
        return user.getUserId();
    }
}
