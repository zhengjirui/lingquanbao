package com.lechuang.lingquanbao.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cmd on 2018/7/14.
 */

public class CusViewPager extends ViewPager {

    private int mMinHeight = 800;//设置viewpager的最小高度
    public CusViewPager(Context context) {
        super(context);
    }

    public CusViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }
        if (height < this.mMinHeight){
            height = this.mMinHeight;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setMinHeight(int minHeight){
        this.mMinHeight = getResources().getDisplayMetrics().heightPixels - minHeight;
    }
}
