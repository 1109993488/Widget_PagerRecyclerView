
package android.support.v7.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by BlingBling on 2018/7/12.
 */
public class PagerLinearLayoutManager extends LinearLayoutManager {

    public PagerLinearLayoutManager(Context context) {
        super(context);
    }

    public PagerLinearLayoutManager(Context context, int orientation) {
        super(context, orientation, false);
    }

    public PagerLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public PagerLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        if (getItemCount() != 0) {
            fillCover(0);
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        dx = super.scrollHorizontallyBy(dx, recycler, state);
        if (dx != 0) {
            fillCover(dx);
        }
        return dx;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        dy = super.scrollVerticallyBy(dy, recycler, state);
        if (dy != 0) {
            fillCover(dy);
        }
        return dy;
    }

    private void fillCover(int scrollDelta) {
        if (mItemTransformer != null) {
            View child;
            for (int i = 0; i < getChildCount(); i++) {
                child = getChildAt(i);
                mItemTransformer.transformItem(this, child, calculateToCenterFraction(child, scrollDelta));
            }
        }
    }

    private OrientationHelper getOrientationHelper() {
        return super.mOrientationHelper;
    }

    private float calculateToCenterFraction(View child, float pendingOffset) {
        int distance = calculateDistanceCenter(child, pendingOffset);
        int childLength = getOrientation() == LinearLayoutManager.HORIZONTAL ? child.getWidth() : child.getHeight();

        return Math.max(-1.f, Math.min(1.f, distance * 1.f / childLength));
    }

    /**
     * @param child
     * @param pendingOffset child view will scroll by
     * @return
     */
    private int calculateDistanceCenter(View child, float pendingOffset) {
        OrientationHelper orientationHelper = getOrientationHelper();
        int parentCenter = (orientationHelper.getEndAfterPadding() - orientationHelper.getStartAfterPadding()) / 2 + orientationHelper.getStartAfterPadding();
        if (getOrientation() == LinearLayoutManager.HORIZONTAL) {
            return (int) (child.getWidth() / 2 - pendingOffset + child.getLeft() - parentCenter);
        } else {
            return (int) (child.getHeight() / 2 - pendingOffset + child.getTop() - parentCenter);
        }
    }

    /**
     * A ItemTransformer is invoked whenever a attached item is scrolled.
     * This offers an opportunity for the application to apply a custom transformation
     * to the item views using animation properties.
     */
    public interface ItemTransformer {

        /**
         * Apply a property transformation to the given item.
         *
         * @param layoutManager Current LayoutManager
         * @param item          Apply the transformation to this item
         * @param fraction      of page relative to the current front-and-center position of the pager.
         *                      0 is front and center. 1 is one full
         *                      page position to the right, and -1 is one page position to the left.
         */
        void transformItem(PagerLinearLayoutManager layoutManager, View item, float fraction);
    }

    private ItemTransformer mItemTransformer;

    public void setItemTransformer(ItemTransformer itemTransformer) {
        mItemTransformer = itemTransformer;
    }
}