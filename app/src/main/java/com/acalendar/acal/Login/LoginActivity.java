package com.acalendar.acal.Login;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.acalendar.acal.InvokeAPISample;
import com.acalendar.acal.LambdaInvoker.LambdaInterface;
import com.acalendar.acal.LambdaInvoker.NameInfo;
import com.acalendar.acal.MainActivity;
import com.acalendar.acal.R;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;


public class LoginActivity extends Activity {
    public static final Regions REGION = Regions.US_WEST_2;

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
                InvokeAPISample.invokeAPI("GET", "/login", "{}");
                finish();
            }
        });
    }

    private LambdaInvokerFactory invokeLogin() {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                this.getApplicationContext(),
                "us-west-2_UU8IM4FSD",
                REGION);
        LambdaInvokerFactory factory = new LambdaInvokerFactory(
                this.getApplicationContext(),
                REGION,
                credentialsProvider);
        return factory;
    }

}
