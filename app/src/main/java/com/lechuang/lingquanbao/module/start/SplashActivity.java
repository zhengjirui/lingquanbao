package com.lechuang.lingquanbao.module.start;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.common.app.base.BaseActivity;
import com.common.app.constants.Constant;
import com.common.app.http.NetWork;
import com.common.app.http.api.Qurl;
import com.common.app.utils.SPUtils;
import com.lechuang.lingquanbao.MyApplication;
import com.lechuang.lingquanbao.R;
import com.lechuang.lingquanbao.bean.AdvertisementBean;
import com.lechuang.lingquanbao.bean.LoadingImgBean;
import com.lechuang.lingquanbao.http.CommonApi;
import com.lechuang.lingquanbao.http.ResultData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    private Context mContext;
    private boolean isFirstOpen;//是否第一次进入
    private List<String> firstIntoData;//第一次进入的数据集合
    private AdvertisementBean.AdvertisingImgBean advertisementBean;//非第一次进入的数据对象
    private boolean currentRun = false;//当前网络请求和即时流程正在run的判断

    private Handler mDelay = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private Runnable mDelayRun = new Runnable() {
        @Override
        public void run() {
            Intent intent;
            if (mNetState && isFirstOpen && firstIntoData != null && firstIntoData.size() > 0) {
                //启动第一次进入的引导view
                intent = new Intent(SplashActivity.this, FirstOpenAdverActivity.class);
                intent.putStringArrayListExtra("firstIntoData", (ArrayList<String>) firstIntoData);
                startActivity(intent);
            } else if (mNetState && advertisementBean != null) {
                //启动非第一次的倒计时广告图
                intent = new Intent(SplashActivity.this, NormalIntoActivity.class);
                intent.putExtra("adverBean", advertisementBean);
                startActivity(intent);
            } else if (mNetState) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                currentRun = false;//当前网络请求和即时流程完毕
                return;
            }
            finish();
            // 设置Activity的切换效果
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
    };

    @Override
    protected int setTransAlpha() {
        return 0;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mContext = SplashActivity.this;
    }

    @Override
    protected void getData() {

    }

    private void getFirstOpenAppData() {

        //首次打开app图片请求
        NetWork.getInstance()
                .setTag(Qurl.loadingImg)
                .getApiService(CommonApi.class)
                .loadingImg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultData<LoadingImgBean>(mContext,true,false) {
                    @Override
                    public void onSuccess(LoadingImgBean response) {

                        if (response == null) {
                            return;
                        }
                        firstIntoData = new ArrayList<>();
                        int size = response.list.size();
                        for (int i = 0; i < size; i++) {
                            firstIntoData.add(response.list.get(i).startImage);
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mDelay.postDelayed(mDelayRun, 1500);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        currentRun = false;//当前网络请求和即时流程完毕
                    }
                });
    }

    private void getAdverData() {
        //加载广告位的信息
        NetWork.getInstance()
                .setTag(Qurl.advertisementInfo)
                .getApiService(CommonApi.class)
                .advertisementInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultData<AdvertisementBean>(mContext,true,false) {
                    @Override
                    public void onSuccess(AdvertisementBean response) {
                        if (response == null) {
                            return;
                        }
                        advertisementBean = response.advertisingImg;
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mDelay.postDelayed(mDelayRun, 1500);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        currentRun = false;//当前网络请求和即时流程完毕
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setNetReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeNetReceiver();
    }


    /**
     * 用于监听网络连接状态
     */
    @Override
    public void netStateLisenter(String connType, boolean netState) {
        super.netStateLisenter(connType, netState);
        if (mNetState) {
            isFirstOpen = SPUtils.getInstance().getBoolean(MyApplication.getApplication(), Constant.ISFIRSTOPEN, false);
//            isFirstOpen = true;
            if (isFirstOpen && !currentRun) {
                getFirstOpenAppData();
            } else if (!currentRun) {
                getAdverData();
            }
            currentRun = true;
//            mDelay.postDelayed(mDelayRun,3000); // todo 这里可以不用处理
        } else {
//            mDelay.removeCallbacks(mDelayRun);  // todo 这里可以不用处理
        }
    }
}
