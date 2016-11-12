package com.acalendar.acal.Login;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.acalendar.acal.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginInstrumentationtest {

    private static final String USERNAME = "rettymoo";
    private static final String PASSWORD = "hehe";

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Test
    public void checkLogin(){
        onView(withId(R.id.login_username_input)).perform(typeText(USERNAME), closeSoftKeyboard());
        onView(withId(R.id.login_password_input)).perform(typeText(PASSWORD), closeSoftKeyboard());
        onView(withText("LOGIN")).perform(click());
    }

    @Test
    public void resetPassword(){
        onView(withText("forget password")).perform(click());
    }

}