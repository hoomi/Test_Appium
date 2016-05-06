package bskyb.com.espresso;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import bskyb.com.hello.appium.MainActivity;

import static android.support.test.espresso.Espresso.onView;

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
        onView()

    }
}
