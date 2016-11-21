package com.acalendar.acal.Login;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.acalendar.acal.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
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
    public void checkUserInputTypeable(){
        onView(withId(R.id.login_username_input)).perform(typeText(USERNAME), closeSoftKeyboard());
        onView(withId(R.id.login_password_input)).perform(typeText(PASSWORD), closeSoftKeyboard());
    }

    @Test
    public void CheckTextViewInScreen(){
        onView(withText("User name")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Password")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("LOGIN")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Forgot password?")).check(ViewAssertions.matches(isDisplayed()));

    }
}