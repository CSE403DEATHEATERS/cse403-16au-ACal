package com.acalendar.acal.Login;


import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.acalendar.acal.R;




public class LoginActivity extends Activity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        Button login = (Button) findViewById(R.id.login_login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
