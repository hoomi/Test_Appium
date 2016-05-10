package bskyb.com.espresso.viewactions;

import android.os.SystemClock;
import android.support.test.espresso.InjectEventSecurityException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.view.MotionEvent;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Created by hos05 on 5/10/16.
 */
public class ClickAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return new Matcher<View>() {
            @Override
            public boolean matches(Object item) {
                return item instanceof View;
            }

            @Override
            public void describeMismatch(Object item, Description mismatchDescription) {

            }

            @Override
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void perform(UiController uiController, View view) {
        float[] coords = new float[2];
        coords = GeneralLocation.CENTER.calculateCoordinates(view);
        ;

        try {
            uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis() + 100L, MotionEvent.ACTION_DOWN, coords[0], coords[1], 0));
            uiController.loopMainThreadForAtLeast(200);
            uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, coords[0], coords[1], 0));
        } catch (InjectEventSecurityException e) {
            e.printStackTrace();
        }

    }
}
