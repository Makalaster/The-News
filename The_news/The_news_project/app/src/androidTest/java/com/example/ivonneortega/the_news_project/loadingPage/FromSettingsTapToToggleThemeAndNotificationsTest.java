package com.example.ivonneortega.the_news_project.loadingPage;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.ivonneortega.the_news_project.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FromSettingsTapToToggleThemeAndNotificationsTest {

    @Rule
    public ActivityTestRule<LoadingActivity> mActivityTestRule = new ActivityTestRule<>(LoadingActivity.class);

    @Test
    public void fromSettingsTapToToggleThemeAndNotificationsTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.options_toolbar), isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.switch_theme), isDisplayed()));
        switch_.perform(click());

        ViewInteraction switch_2 = onView(
                allOf(withId(R.id.switch_notification), isDisplayed()));
        switch_2.perform(click());

    }

}
