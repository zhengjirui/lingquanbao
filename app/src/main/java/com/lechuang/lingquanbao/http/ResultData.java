package com.lechuang.lingquanbao.http;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.common.app.base.BaseApplication;
import com.common.app.base.bean.BaseResponseBean;
import com.common.app.http.RxObserver;
import com.common.app.utils.ShowToast;
import com.lechuang.lingquanbao.module.login.LoginActivity;

/**
 * Created by cmd on 2018/7/15.
 */

public abstract class ResultData<T> extends RxObserver<T> {


    public ResultData(Context context) {
        super(context);
    }

    public ResultData(Context context, boolean canCancle) {
        super(context, canCancle);
    }

    public ResultData(Context context, boolean canCancle, boolean isShowProgress) {
        super(context, canCancle, isShowProgress);
    }

    @Override
    public void onNext(BaseResponseBean<T> result) {
        //关闭弹窗进度条
        stopProgressDialog();

        if (result.errorCode == 200) {
            onSuccess(result.data);
        } else if (result.errorCode == 401 || result.errorCode == 303) {  //错误码401 303 登录
            onFailed(result.errorCode,result.moreInfo);
            if (!TextUtils.isEmpty(result.moreInfo)) {
                ShowToast.getInstance().showLongToast(result.moreInfo);
            }
            reLoggedIn();
        } else if (result.errorCode == 300) {
            onFailed(result.errorCode, result.moreInfo);
            if (!TextUtils.isEmpty(result.moreInfo)) {
                ShowToast.getInstance().showLongToast(result.moreInfo);
            }
        } else {
            onFailed(result.errorCode, result.moreInfo);
            if (!TextUtils.isEmpty(result.moreInfo)) {
                ShowToast.getInstance().showLongToast(result.moreInfo);
            }
        }
    }

    /**
     * 重新登录提示
     */
    private synchronized void reLoggedIn() {
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
