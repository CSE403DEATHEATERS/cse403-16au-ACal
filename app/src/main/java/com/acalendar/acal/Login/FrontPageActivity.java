package com.acalendar.acal.Login;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.acalendar.acal.MainActivity;
import com.acalendar.acal.R;




public class FrontPageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);

        buttonSetUp();


        Button openSingUp = (Button) findViewById(R.id.openNewSignUp);
        openSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrontPageActivity.this, SignUPActivity.class);
                startActivity(intent);
            }
        });
    }

    private void buttonSetUp() {
        activatedLogin();
        activatedSignUp();
    }

    private void activatedLogin() {
        Button openNewLogin = (Button) findViewById(R.id.openNewLogin);
        openNewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrontPageActivity.this, LoginActivity.class);
                startActivityForResult(intent, 1);

            }
        });
    }

    private void activatedSignUp() {
        Button openSingUp = (Button) findViewById(R.id.openNewSignUp);
        openSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrontPageActivity.this, SignUPActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                setResult(RESULT_OK);
                finish();
            } else {
                return;
            }
        }
    }
}