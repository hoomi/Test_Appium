package bskyb.com.espresso;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import bskyb.com.espresso.viewactions.ScrollingAction;
import bskyb.com.hello.appium.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

/**
 * Created by hos05 on 5/6/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SampleTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void test_scrolling_test() throws Exception {
        onView(new Matcher<View>() {
            @Override
            public boolean matches(Object item) {
                return (item instanceof ListView || item instanceof RecyclerView);
            }

            @Override
            public void describeMismatch(Object item, Description mismatchDescription) {
                mismatchDescription.appendText("Could not find a: " + item.toString());
            }

            @Override
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            @Override
            public void describeTo(Description description) {

            }
        }).perform(new ScrollingAction(40, click()));

    }


}
