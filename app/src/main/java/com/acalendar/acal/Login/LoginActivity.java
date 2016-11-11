package com.acalendar.acal.Login;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.acalendar.acal.R;



public class LoginActivity extends Activity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        AutoCompleteTextView usernameView = (AutoCompleteTextView) findViewById(R.id.login_username_input);
        EditText passwordView = (EditText) findViewById(R.id.login_password_input);

        String usernameInput = usernameView.getText().toString();
        String passwordInput = passwordView.getText().toString();




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
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
