package bskyb.com.espresso.viewactions;

import android.os.SystemClock;
import android.support.test.espresso.InjectEventSecurityException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Created by hoomanostovari on 08/05/2016.
 */
public class ScrollingAction implements ViewAction {

    private final int SCROLLING_SPEED = 10;
    private final int position;
    private int topPosition = 0;

    public ScrollingAction(int position) {
        this.position = position;
    }


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
        if (view instanceof ViewGroup) {
            topPosition = 0;
            while (view.canScrollVertically(1) && topPosition != position) {
                int scrollAmount = getScrollAmount((ViewGroup) view);
                try {
                    scrollDown(uiController, scrollAmount);
                    topPosition++;

                } catch (InjectEventSecurityException e) {
                    e.printStackTrace();
                }

            }

            uiController.loopMainThreadForAtLeast(5000);
        }


    }

    private void scrollDown(UiController uiController, int scrollBy) throws InjectEventSecurityException {
        uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis() + 100L, MotionEvent.ACTION_DOWN, 0, 300.0f, 0));
        for (int i = 0; i < scrollBy; i = i + Math.min(SCROLLING_SPEED, scrollBy)) {
            uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE, 0, 300f - i, 0));
            if (i == scrollBy - 1) {
                uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 300f - i, 0));

            }
        }
    }

    private int getScrollAmount(ViewGroup viewGroup) {
        View viewAfter = viewGroup.getChildAt(1);
        View viewItself = viewGroup.getChildAt(0);
        if (viewAfter == null) {
            return viewItself == null ? 0 : viewItself.getTop();
        }
        return viewAfter.getTop();
    }


}
