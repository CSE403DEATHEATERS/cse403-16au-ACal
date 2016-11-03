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


        Button signup = (Button) findViewById(R.id.signup_signup_button);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
