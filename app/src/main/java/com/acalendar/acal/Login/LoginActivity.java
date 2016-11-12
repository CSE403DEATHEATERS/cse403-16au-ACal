package com.acalendar.acal.Login;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

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
                AutoCompleteTextView usernameView = (AutoCompleteTextView) findViewById(R.id.login_username_input);
                EditText passwordView = (EditText) findViewById(R.id.login_password_input);

                String usernameInput = usernameView.getText().toString();
                String passwordInput = passwordView.getText().toString();
                Log.v("TestInput", usernameInput + " " + passwordInput);
                LoginedAccount.logIn(usernameInput, passwordInput);
                Account user = LoginedAccount.getCurrentUser();
                Intent intent = new Intent();
                if (user != null) {
//                    if (LoginedAccount.isLogedIn()) {
//                        LinearLayout ll = ((R.id.nav_header);
//                        TextView profileView = (TextView) ll.findViewById(R.id.UserFullName);
//                        profileView.setText(LoginedAccount.getUserFullName());
//                        TextView emailView = (TextView) ll.findViewById(R.id.AccountInfo);
//                        emailView.setText(LoginedAccount.getEmail());
//                    }
                    setResult(RESULT_OK, intent);
                } else {
                    setResult(RESULT_CANCELED, intent);
                }
                finish();
            }
        });
    }

}
