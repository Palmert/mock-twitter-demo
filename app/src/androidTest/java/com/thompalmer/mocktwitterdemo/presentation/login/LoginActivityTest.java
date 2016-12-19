package com.thompalmer.mocktwitterdemo.presentation.login;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.thompalmer.mocktwitterdemo.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer.*;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void successfulLoginShouldTransitionToFeed() {
        onView(withId(R.id.edt_email))
                .perform(scrollTo(), replaceText("thomapalmer@gmail.com"), closeSoftKeyboard());

        onView(withId(R.id.edt_password))
                .perform(scrollTo(), replaceText("password1"), closeSoftKeyboard());

        onView(withId(R.id.btn_sign_in))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.logout), withText("Logout"), isDisplayed()))
                .check(matches(isDisplayed()));

        onView(withId(R.id.logout))
                .perform(click());
    }

    @Test
    public void attemptLoginWithNonExistentUser_errorMessageShouldUpdate() {
        onView(withId(R.id.edt_email))
                .perform(scrollTo(), replaceText("thoma1palmer@gmail.com"), closeSoftKeyboard());

        onView(withId(R.id.edt_password))
                .perform(scrollTo(), replaceText("password"), closeSoftKeyboard());

        onView(withId(R.id.btn_sign_in))
                .perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withText(MESSAGE_USER_DOES_NOT_EXIST),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_input_layout_password),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText(MESSAGE_USER_DOES_NOT_EXIST)));
    }

    @Test
    public void attemptLoginWithInvalidCredentials_errorMessageShouldUpdate() {
        onView(withId(R.id.edt_email))
                .perform(scrollTo(), replaceText("thomapalmer@gmail.com"), closeSoftKeyboard());

        onView(withId(R.id.edt_password))
                .perform(scrollTo(), replaceText("password"), closeSoftKeyboard());

        onView(withId(R.id.btn_sign_in))
                .perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withText(MESSAGE_INVALID_PASSWORD),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.text_input_layout_password),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText(MESSAGE_INVALID_PASSWORD)));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
