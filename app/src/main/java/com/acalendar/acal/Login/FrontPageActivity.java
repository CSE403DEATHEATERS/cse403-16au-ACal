package com.acalendar.acal.Login;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.acalendar.acal.R;




public class FrontPageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);

        Button openNewLogin = (Button) findViewById(R.id.openNewLogin);
        openNewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrontPageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button openSingUp = (Button) findViewById(R.id.openNewSignUp);
        openSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrontPageActivity.this, SignUPActivity.class);
                startActivity(intent);
            }
        });
    }
}