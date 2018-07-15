package com.common.app.utils;

import android.widget.Toast;

import com.common.app.base.BaseApplication;


public class ShowToast {

    private Toast toast;

    private static class LazyShowToast{
        private static ShowToast instance = new ShowToast();
    }

    private ShowToast() {
    }

    public static ShowToast getInstance() {
        return LazyShowToast.instance;
    }

    /**
     * 显示一个时间较长的toast信息
     *
     * @param id
     */
    public void showLongToast(int id) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getApplication(), id, Toast.LENGTH_LONG);
        } else {
            toast.setText(id);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }

    /**
     * 显示一个时间较长的toast信息
     *
     * @param msg
     */
    public void showLongToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }

    /**
     * 显示一个时间较短的toast信息
     *
     * @param id
     */
    public void showShortToast(int id) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getApplication(), id, Toast.LENGTH_SHORT);
        } else {
            toast.setText(id);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }


    /**
     * 显示一个时间较短的toast信息
     *
     * @param msg
     */
    public void showShortToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
