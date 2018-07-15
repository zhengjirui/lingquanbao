package com.lechuang.lingquanbao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.lechuang.lingquanbao.R;


/**
 * Author: guoning
 * Date: 2017/10/3
 * Description:
 */

public class SpannelTextView extends View {

    private int shopType;//店铺类型
    private Bitmap bitmap;
    private Paint paint;
    private String drawText;
    private String first, second;
    float mTextWidth;
    boolean mNewLine = false;
    private Rect rect = new Rect();
    private float scaleDenisty;

    public SpannelTextView(Context context) {
        this(context, null);
    }

    public SpannelTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpannelTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpannelTextView);
        shopType = typedArray.getInt(R.styleable.SpannelTextView_shopType, 1);
        drawText = typedArray.getString(R.styleable.SpannelTextView_drawText);
        typedArray.recycle();

        scaleDenisty = getResources().getDisplayMetrics().scaledDensity;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(13 * scaleDenisty);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.c_main_text));

        mTextWidth = paint.measureText(drawText) * getResources().getDisplayMetrics().scaledDensity;

        //不要再onDraw中处理这一步,应该避免在onDraw中分配对象内存
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(shopType == 1 ? R.mipmap.ic_taobao : R.mipmap.ic_tianmao);
        bitmap = drawable.getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
        canvas.drawBitmap(bitmap, lp.leftMargin, lp.topMargin, paint);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        if (mNewLine) {
            paint.getTextBounds(first, 0, first.length(), rect);
            canvas.drawText(first, 3 * scaleDenisty + lp.leftMargin + bitmap.getWidth(), (3.5f * scaleDenisty + 0.5f) + lp.topMargin + (fontMetrics.descent - fontMetrics.ascent) / 2, paint);
            canvas.drawText(second, lp.leftMargin, (5f * scaleDenisty + 0.5f) + lp.topMargin + bitmap.getHeight() + (fontMetrics.descent - fontMetrics.ascent) / 2, paint);
        } else {
            canvas.drawText(drawText, 3 * scaleDenisty + lp.leftMargin + bitmap.getWidth(), (3.5f * scaleDenisty + 0.5f) + lp.topMargin + (fontMetrics.descent - fontMetrics.ascent) / 2, paint);
        }



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
        if (bitmap.getWidth() + mTextWidth + lp.leftMargin + lp.rightMargin > width) {
            //换行
            for (int x = 0, length = drawText.length(); x < length; x++) {
                float v = paint.measureText(drawText.substring(0, x + 1));
                if (bitmap.getWidth() + v + lp.leftMargin + lp.rightMargin >= width) {
                    first = drawText.substring(0, x - 1);
                    if (drawText.length() > x + 10) {
                        second = drawText.substring(x - 1, x + 8) + "...";
                    } else {
                        second = drawText.substring(x - 1);
                    }
                    mNewLine = true;
                    break;
                }
            }
        }

        setMeasuredDimension(width, (int) (34 * scaleDenisty + 0.5f));
    }

    public void setPaintColor(int color){
        paint.setColor(getResources().getColor(color));
    }

    @SuppressWarnings("必须在布局中定义默认的drawText")
    public void setDrawText(String drawText) {
        this.drawText = drawText;
        mTextWidth = paint.measureText(drawText) * getResources().getDisplayMetrics().scaledDensity;
        mNewLine=false;
//        measure(0,0);
//        invalidate();
        //三步流程都走
        requestLayout();
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
        if(shopType == -1){
            BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.mipmap.ic_tuijian);
            bitmap = drawable.getBitmap();
            //只走onDraw
            invalidate();
            return;
        }
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(shopType == 1 ? R.mipmap.ic_taobao : R.mipmap.ic_tianmao);
        bitmap = drawable.getBitmap();
        //只走onDraw
        invalidate();
        //postInvalidate();
    }

}
