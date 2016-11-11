package com.acalendar.acal.Login;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.acalendar.acal.InvokeAPISample;
import com.acalendar.acal.MainActivity;
import com.acalendar.acal.R;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        buttonSetUP();
    }

    private void buttonSetUP() {
        activatedLogin();
        activatedReset();
        activatedBack();
    }

    private void activatedBack() {
        Button back = (Button) findViewById(R.id.login_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void activatedReset() {
        Button reset = (Button) findViewById(R.id.login_reset_button);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void activatedLogin() {
        Button login = (Button) findViewById(R.id.login_login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String apiResponse = InvokeAPISample.invokeAPI("GET", "/login", "", "?username=myfriend&password=hehe");
                String fullname = getFullname(apiResponse);
                finish();
            }
        });
    }

    private String getFullname(String json) {
        HashMap<String,String> map = new Gson().fromJson(json, new TypeToken<HashMap<String, String>>(){}.getType());
        if (map.containsKey("firstname") || map.containsKey("lastname"))
            return "" + map.get("firsname") + map.get("lastname");
        else
            return "";
    }

}
