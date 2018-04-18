package com.assignment;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.assignment.facts.FactsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Mahesh R Bhatkande (mahesh.bhatkande@infosys.com)
 * @since 16 Apr, 2018
 */

@RunWith(AndroidJUnit4.class)
public class FactsLoadingTest {

    @Rule
    public final ActivityTestRule<FactsActivity> facstsActivityTestRule = new ActivityTestRule<FactsActivity>(FactsActivity.class);

    @Test
    public void noInternetConnectionOnStart() {

        onView(withId(R.id.textErrorMessage))
                .check(matches(isDisplayed()))
                .perform(click());
    }
}
