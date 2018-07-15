package com.lechuang.lingquanbao.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TableLayout;

/**
 * Created by cmd on 2018/5/6.
 */

public class TransChangeNesScrollView extends NestedScrollView{

    private View mTransChangeView;
    private float mHeightPixels;

    public TransChangeNesScrollView(Context context) {
        this(context,null);
    }

    public TransChangeNesScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransChangeNesScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHeightPixels = getContext().getResources().getDisplayMetrics().heightPixels;

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mTransChangeView != null) {
            // alpha = 滑出去的高度/(screenHeight/3);

            if (this.mLineView != null && mLineView.getTop() - mTopTab.getHeight() <= getScrollY()){
                mTopTab.setVisibility(VISIBLE);
            }else {
                mTopTab.setVisibility(INVISIBLE);
            }

            float scrollY = getScrollY();//获取划出去的高度
            float sulv = mHeightPixels / 3;
            if (scrollY <= 0 ){
                mTransChangeView.setAlpha(0);
                mTransChangeView.setVisibility(GONE);
            }else {
                mTransChangeView.setAlpha(scrollY / sulv);
                mTransChangeView.setVisibility(VISIBLE);
            }
        }
    }

    /**
     * 设置需要渐变view
     *
     * @param transChangeView
     */
    public void setTransparentChange(View transChangeView) {
        this.mTransChangeView = transChangeView;
    }

    /**
     * 设置头部的tablayout显示隐藏
     * @param lineView  标记线
     * @param topTab
     */
    private View mLineView;
    private TabLayout mTopTab;
    public void setTopTabLayout(View lineView, TabLayout topTab){
        this.mLineView = lineView;
        this.mTopTab = topTab;
    }

    private boolean isNeedScroll = true;
    private float xDistance, yDistance, xLast, yLast;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                if (xDistance > yDistance) {
                    return false;
                }
                return isNeedScroll;

        }
        return super.onInterceptTouchEvent(ev);
    }

    /*
    改方法用来处理NestedScrollView是否拦截滑动事件
     */
    public void setNeedScroll(boolean isNeedScroll) {
        this.isNeedScroll = isNeedScroll;
    }

    private View view;
    public void setNeedScrollView(View view){
        this.view = view;
    }

}
