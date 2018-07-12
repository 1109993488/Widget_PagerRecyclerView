package com.blingbling.widget.pagerrecyclerview.transformer;

import android.support.v7.widget.PagerLinearLayoutManager;
import android.view.View;

/**
 * Created by BlingBling on 2018/7/12.
 */
public class ScaleTransformer implements PagerLinearLayoutManager.ItemTransformer {

    private float mScale = 0;

    public ScaleTransformer() {
        this(0.75f);
    }

    public ScaleTransformer(float minScale) {
        mScale = 1 - minScale;
    }

    @Override
    public void transformItem(PagerLinearLayoutManager layoutManager, View item, float fraction) {
        final float scale = 1 - mScale * Math.abs(fraction);
        item.setScaleX(scale);
        item.setScaleY(scale);
    }
}
