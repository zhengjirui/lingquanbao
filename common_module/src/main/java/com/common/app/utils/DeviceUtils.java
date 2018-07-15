package com.common.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.common.app.base.BaseApplication;

/**
 * Author：CHENHAO
 * date：2018/5/3
 * desc：
 */

public class DeviceUtils {
    /**
     * 是否有网
     *
     * @return boolean
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    /**
     * 获取频宽的比例图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getScaleBitmap(Bitmap bitmap) {
        int newWidth = BaseApplication.getApplication().getResources().getDisplayMetrics().widthPixels;
        // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleWidth);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
