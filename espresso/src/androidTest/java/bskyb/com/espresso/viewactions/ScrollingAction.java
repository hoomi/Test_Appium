package bskyb.com.espresso.viewactions;

import android.os.SystemClock;
import android.support.test.espresso.InjectEventSecurityException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Created by hoomanostovari on 08/05/2016.
 */
public class ScrollingAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return new Matcher<View>() {
            @Override
            public boolean matches(Object item) {
                return (item instanceof ListView || item instanceof RecyclerView);
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
        return "Scrolling to id";
    }

    @Override
    public void perform(UiController uiController, View view) {
        uiController.loopMainThreadForAtLeast(5000);
        try {
            uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis() + 100L,MotionEvent.ACTION_DOWN,0,300.0f,0));
            uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis() + 110L,MotionEvent.ACTION_MOVE,0,280.0f,0));
            uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis() + 120L,MotionEvent.ACTION_MOVE,0,260.0f,0));
            uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis() + 130L,MotionEvent.ACTION_MOVE,0,240.0f,0));
            uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis() + 140L,MotionEvent.ACTION_MOVE,0,220.0f,0));
            uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis() + 150L,MotionEvent.ACTION_MOVE,0,200.0f,0));
            uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis() + 160L,MotionEvent.ACTION_MOVE,0,180.0f,0));
            uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis() + 170L,MotionEvent.ACTION_UP,0,180.0f,0));

        } catch (InjectEventSecurityException e) {
            e.printStackTrace();
        }

        uiController.loopMainThreadForAtLeast(5000);
    }
}
