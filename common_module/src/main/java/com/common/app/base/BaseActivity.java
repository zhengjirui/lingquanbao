package com.common.app.base;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.common.app.http.INetStateLisenter;
import com.common.app.receiver.NetWorkChangReceiver;
import com.common.app.utils.ShowToast;
import com.common.app.utils.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: zhengjr
 * @since: 2018/6/1
 * @describe:
 */

public abstract class BaseActivity extends AppCompatActivity implements INetStateLisenter {
    private final String TAG = "BaseMvpActivity";
    protected Unbinder mUnbinder;

    private NetWorkChangReceiver mNetWorkChangReceiver;
    protected boolean mNetState = false; //网络的连接状态

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        } else if (getLayoutView() != null) {
            setContentView(getLayoutView());
        }
        setTranslucentHeader();
        mUnbinder = ButterKnife.bind(this);

        savedInstance(savedInstanceState);

        //初始化数据和控件
        initDataView();
    }

    private void initDataView() {
        //获得intent
        initIntent();

        //查找控件
        findViews();

        //初始化控件
        initView();

        //初始化数据
        initData();

        //获取数据
        getData();
    }

    protected void setTranslucentHeader(){
        StatusBarUtil.setTranslucentForImageView(BaseActivity.this, setTransAlpha(),null);
    }

    protected int setTransAlpha(){
        return 0;
    }

    //获取布局id
    protected abstract int getLayoutId();

    //获取布局View
    protected View getLayoutView() {
        return null;
    }

    protected void initIntent() {
    }

    protected void findViews() {
    }

    protected abstract void initView();

    protected void initData(){}

    protected void savedInstance(@Nullable Bundle savedInstanceState){}

    protected abstract void getData();

    public void finish(View view){
        finish();
    }


    protected void setNetReceiver() {
        if (mNetWorkChangReceiver == null) {
            mNetWorkChangReceiver = new NetWorkChangReceiver(this);
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetWorkChangReceiver, filter);
    }

    protected void removeNetReceiver() {
        //网络连接监听广播
        if (mNetWorkChangReceiver != null) {
            unregisterReceiver(mNetWorkChangReceiver);
        }
    }

    @Override
    public void netStateLisenter(String connType, boolean netState) {
        mNetState = netState;
        if (mNetState) {
            ShowToast.getInstance().showShortToast("您正在使用" + connType);
        } else {
            ShowToast.getInstance().showShortToast("网络连接断开");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
