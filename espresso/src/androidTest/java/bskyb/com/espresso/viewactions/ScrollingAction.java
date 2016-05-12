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
            int scrollThreshold = getScrollingThreshold(view);
            topPosition = 0;

            try {
                while (view.canScrollVertically(1) && topPosition != position) {
                    int scrollAmount = getScrollAmount((ViewGroup) view);
                    scrollUp(uiController, scrollAmount, scrollThreshold, view.getTop() + view.getHeight());
                    uiController.loopMainThreadForAtLeast(200);
                    if (isTheTopChildFullyVisible((ViewGroup) view, scrollThreshold)) {
                        topPosition++;
                    }
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

    private void scrollUp(UiController uiController, int scrollBy, int scrollThreshold, int endTouchArea) throws InjectEventSecurityException {
        long timeStarted = SystemClock.uptimeMillis();
        uiController.injectMotionEvent(MotionEvent.obtain(timeStarted,
                SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 50, endTouchArea, 0));
        int TIME_DISTANCE = 720 / scrollThreshold;
        long initialActionMove = timeStarted + TIME_DISTANCE;
        int scrollSpeed = scrollThreshold / 2;
        for (int i = scrollSpeed; i <= scrollBy; i = i + scrollSpeed) {
            initialActionMove += TIME_DISTANCE;
            uiController.injectMotionEvent(MotionEvent.obtain(timeStarted,
                    initialActionMove, MotionEvent.ACTION_MOVE, 50, endTouchArea - i, 0));


        }
        initialActionMove += TIME_DISTANCE;
        uiController.injectMotionEvent(MotionEvent.obtain(timeStarted,
                initialActionMove, MotionEvent.ACTION_MOVE, 50, endTouchArea - scrollBy, 0));
        initialActionMove += 17;
        uiController.injectMotionEvent(MotionEvent.obtain(timeStarted,
                initialActionMove, MotionEvent.ACTION_UP, 50, endTouchArea - scrollBy - scrollThreshold, 0));
    }

    private int getScrollAmount(ViewGroup viewGroup) {
        int i = findPositionForTopFullyVisibleView(viewGroup);
        View viewItself = viewGroup.getChildAt(i);
        View viewAfter = i == 0 ? viewGroup.getChildAt(1) : viewGroup.getChildAt(i + 1);
        if (viewAfter == null) {
            return viewItself == null ? 0 : viewItself.getTop();
        }

        return viewAfter.getTop();
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

    private boolean isTheTopChildFullyVisible(ViewGroup viewGroup, int scrollThreshold) {
        int i = findPositionForTopFullyVisibleView(viewGroup);

        int top = viewGroup.getChildAt(i).getTop();
        return (top <= scrollThreshold + scrollThreshold / 2);
    }

    private int getScrollingThreshold(View view) {
        float densityDpi = view.getContext().getResources().getDisplayMetrics().density;
        return (int) (8 * densityDpi);

    }
}
