package com.acalendar.acal.Login;

import android.util.Log;

import com.acalendar.acal.Events.EventsManager;
import com.acalendar.acal.InvokeAPISample;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
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
        String apiResponse = InvokeAPISample.invokeAPI("GET", "/login", null, query);
        Log.v("testApi", "response: " + apiResponse);
        String events = apiResponse.substring(1, apiResponse.indexOf(','));
        String accountString = apiResponse.substring(apiResponse.indexOf(",") + 1, apiResponse.length() - 1);
        String accountInfo = accountString.substring(accountString.indexOf(':') + 1, accountString.length());
        Log.v("Test", "accountInfo" + accountInfo);
        HashMap<String,String> map = new Gson().fromJson(accountInfo, new TypeToken<HashMap<String, String>>(){}.getType());
        if (map.get("userId") != null) {
            user = new Account(map.get("userId"), map.get("username"), map.get("email"), map.get("lastname"), map.get("firstname"));
            eventsManager = new EventsManager(user.getUserId());
        }
    }

    public static void signUp(String body) {
        Log.v("Test", "query: " + body);
        Map<String, String> query = new HashMap<>();
        String apiResponse = InvokeAPISample.invokeAPI("POST", "/signup", body, query);
        HashMap<String,String> map = new Gson().fromJson(apiResponse, new TypeToken<HashMap<String, String>>(){}.getType());
        user = new Account(map.get("userId"), map.get("username"), map.get("email"), map.get("lastname"), map.get("firstname"));
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
}
