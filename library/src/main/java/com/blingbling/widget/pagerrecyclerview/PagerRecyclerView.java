package com.blingbling.widget.pagerrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by BlingBling on 2018/7/12.
 */
public class PagerRecyclerView extends RecyclerView {

    private LinearSnapHelper mSnapHelper;

    private boolean mCanFling = false;
    private boolean mCallbackInFling = true;

    private int mCurSelectedPosition = -1;
    private OnScrollListener mScrollListener;

    public PagerRecyclerView(Context context) {
        this(context, null);
    }

    public PagerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mSnapHelper = new LinearSnapHelper();
        mSnapHelper.attachToRecyclerView(this);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PagerRecyclerView);
        mCanFling = a.getBoolean(R.styleable.PagerRecyclerView_recycler_can_fling, mCanFling);
        mCallbackInFling = a.getBoolean(R.styleable.PagerRecyclerView_recycler_callback_in_fling, mCallbackInFling);
        a.recycle();
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        return mCanFling ? super.fling(velocityX, velocityY) : false;
    }

    public void setCanFling(boolean canFling) {
        mCanFling = canFling;
    }

    public void setCallbackInFling(boolean callbackInFling) {
        mCallbackInFling = callbackInFling;
    }

    /**
     * Listen for changes to the selected item
     *
     * @author chensuilun
     */
    public interface OnItemSelectedListener {
        /**
         * @param recyclerView The RecyclerView which item view belong to.
         * @param item         The current selected view
         * @param position     The current selected view's position
         */
        void onItemSelected(RecyclerView recyclerView, View item, int position);
    }

    private OnItemSelectedListener mOnItemSelectedListener;

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.setOnItemSelectedListener(onItemSelectedListener, false);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener, boolean callbackInFling) {
        mOnItemSelectedListener = onItemSelectedListener;
        setCallbackInFling(callbackInFling);
        initScrollListener();
    }

    private void initScrollListener() {
        if (mScrollListener == null) {
            mScrollListener = new OnScrollListener() {
                int mState = RecyclerView.SCROLL_STATE_IDLE;

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (mOnItemSelectedListener == null) {
                        return;
                    }
                    mState = newState;
                    if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                        return;
                    }
                    selected(recyclerView);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (mOnItemSelectedListener == null) {
                        return;
                    }
                    if (!mCallbackInFling && mState != RecyclerView.SCROLL_STATE_IDLE) {
                        return;
                    }
                    selected(recyclerView);
                }
            };
            addOnScrollListener(mScrollListener);
        }
    }

    private void selected(RecyclerView recyclerView) {
        View snap = mSnapHelper.findSnapView(recyclerView.getLayoutManager());
        if (snap != null) {
            int selectedPosition = recyclerView.getLayoutManager().getPosition(snap);
            if (selectedPosition != mCurSelectedPosition) {
                mCurSelectedPosition = selectedPosition;
                mOnItemSelectedListener.onItemSelected(recyclerView, snap, mCurSelectedPosition);
            }
        }
    }
}