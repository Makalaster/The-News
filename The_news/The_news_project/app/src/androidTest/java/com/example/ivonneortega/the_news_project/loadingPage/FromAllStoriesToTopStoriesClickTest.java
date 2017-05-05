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
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FromAllStoriesToTopStoriesClickTest {

    @Rule
    public ActivityTestRule<LoadingActivity> mActivityTestRule = new ActivityTestRule<>(LoadingActivity.class);

    @Test
    public void loadingActivityTest() {
        ViewInteraction appCompatTextView = onView(
                allOf(withText("All Stories"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withText("Top Stories"), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.top_stories_recycler_view),
                        withParent(withId(R.id.top_swipe_refresh)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

    }

}
