package bskyb.com.espresso.viewactions;

import android.os.SystemClock;
import android.support.test.espresso.InjectEventSecurityException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Created by hoomanostovari on 08/05/2016.
 */
public class ScrollingAction implements ViewAction {

    private final int SCROLLING_SPEED = 50;
    private final int position;
    private ViewAction viewAction;
    private int topPosition = 0;

    public ScrollingAction(int position, ViewAction viewAction) {
        this.position = position;
        this.viewAction = viewAction;
    }


    @Override
    public Matcher<View> getConstraints() {
        return new Matcher<View>() {
            @Override
            public boolean matches(Object item) {
                return item instanceof ViewGroup;
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
        return "Scrolling to position";
    }

    @Override
    public void perform(UiController uiController, View view) {
        if (view instanceof ViewGroup) {
            topPosition = 0;
            try {
                while (view.canScrollVertically(1) && topPosition != position) {
                    int scrollAmount = getScrollAmount((ViewGroup) view);
                    scrollDown(uiController, scrollAmount, view.getHeight());
                    uiController.loopMainThreadUntilIdle();
                    topPosition++;
                }
            } catch (InjectEventSecurityException e) {
                e.printStackTrace();
            }

            if (viewAction != null) {
                if (topPosition == position) {
                    uiController.loopMainThreadForAtLeast(500);
                    viewAction.perform(uiController, ((ViewGroup) view).getChildAt(findPositionForTopFullyVisibleView((ViewGroup) view)));
                } else {
                    int childPosition = position - topPosition;
                    uiController.loopMainThreadForAtLeast(500);
                    viewAction.perform(uiController, ((ViewGroup) view).getChildAt(childPosition));

                }
            }
            uiController.loopMainThreadForAtLeast(1000);
        }


    }

    private void scrollDown(UiController uiController, int scrollBy, int maxTouchArea) throws InjectEventSecurityException {
        uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, maxTouchArea, 0));
        int scrollSpeed = Math.min(SCROLLING_SPEED, scrollBy);
        for (int i = scrollSpeed; i <= scrollBy; i = i + scrollSpeed) {
            uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE, 0, maxTouchArea - i, 0));
                    uiController.loopMainThreadForAtLeast(30);

        }
        uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE, 0, maxTouchArea - scrollBy, 0));
        uiController.loopMainThreadForAtLeast(100);
        uiController.injectMotionEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, maxTouchArea - scrollBy, 0));
    }

    private int getScrollAmount(ViewGroup viewGroup) {
        int i = findPositionForTopFullyVisibleView(viewGroup);
        View viewItself = viewGroup.getChildAt(i);
        View viewAfter = i == 0 ? viewGroup.getChildAt(1) : viewGroup.getChildAt(i + 1);
        if (viewAfter == null) {
            return viewItself == null ? 0 : viewItself.getTop();
        }
        return viewAfter.getTop() - viewGroup.getPaddingTop() / 2;
    }

    private int findPositionForTopVisibleView(ViewGroup viewGroup) {
        int i = 0;
        View viewItself = viewGroup.getChildAt(i);
        while (viewItself.getTop() < 0 && viewItself.getHeight() <= Math.abs(viewItself.getTop()) && i < viewGroup.getChildCount()) {
            viewItself = viewGroup.getChildAt(++i);
        }
        return i;
    }

    private int findPositionForTopFullyVisibleView(ViewGroup viewGroup) {
        int i = 0;
        View viewItself = viewGroup.getChildAt(i);
        while (viewItself.getTop() < 0 && i < viewGroup.getChildCount()) {
            viewItself = viewGroup.getChildAt(++i);
        }
        return i;
    }
}
