package com.acalendar.acal.Login;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.acalendar.acal.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignUPActivity extends Activity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);

        buttonSetUp();
    }

    private void buttonSetUp() {
        activatedSignUp();
        activatedBack();
        activatedVerify();
    }

    private void activatedVerify() {
//        Button verify = (Button) findViewById(R.id.signup_verify_button);
//        verify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    private void activatedBack() {
        Button back = (Button) findViewById(R.id.signup_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void activatedSignUp() {
        Button signUp = (Button) findViewById(R.id.signup_signup_button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameInput = (EditText) findViewById(R.id.sign_up_username);
                EditText passwordInput = (EditText) findViewById(R.id.sign_up_password);
                EditText firstnameInput = (EditText) findViewById(R.id.sign_up_firstname);
                EditText lastnameInput = (EditText) findViewById(R.id.sign_up_lastname);
                EditText emailInput = (EditText) findViewById(R.id.sign_up_email);
                String username = usernameInput.getText().toString();
                String firstname = firstnameInput.getText().toString();
                String lastname = lastnameInput.getText().toString();
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                Map<String, String> query = new HashMap<>();
                query.put("username", username);
                query.put("password", password);
                query.put("email", email);
                query.put("firstname", firstname);
                query.put("lastname", lastname);
                JSONObject jsonObject = new JSONObject(query);
                LoginedAccount.signUp(jsonObject.toString());
                finish();
            }
        });
    }
}
