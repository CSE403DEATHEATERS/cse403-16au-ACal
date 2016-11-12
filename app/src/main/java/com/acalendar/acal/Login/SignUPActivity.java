package com.acalendar.acal.Login;


import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.acalendar.acal.R;




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
        Button verify = (Button) findViewById(R.id.signup_verify_button);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
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

                finish();
            }
        });
    }
}
