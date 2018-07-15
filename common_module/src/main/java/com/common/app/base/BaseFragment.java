package com.common.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.app.utils.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    protected Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getLayoutId() != 0) {
            View inflate = inflater.inflate(getLayoutId(), container,false);
            unbinder = ButterKnife.bind(this, inflate);
            return inflate;
        } else if (getLayoutView(inflater, container, savedInstanceState) != null) {
            View inflate = getLayoutView(inflater, container, savedInstanceState);
            unbinder = ButterKnife.bind(this, inflate);
            return inflate;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    protected void initDataView() {
        //查找控件
        findViews();

        //初始化控件
        initView();

        //用于初始化数据使用
        initData();

        //获取数据
        getData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){//用户切换fragment时需要刷新数据使用
            updataRequest();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化数据和控件
        initDataView();
    }

    protected void setTranslucentHeader(){
        StatusBarUtil.setTranslucentForImageView(getActivity(), setTransAlpha(),null);
    }

    protected int setTransAlpha(){
        return 0;
    }
    //获取布局id
    protected abstract int getLayoutId();

    protected View getLayoutView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return null;
    }

    protected void findViews(){}

    protected abstract void initView();

    protected void initData(){}

    protected abstract void getData();

    protected void updataRequest(){}


    @Override
    public void onDetach() {
        super.onDetach();
        if (unbinder != null){
            unbinder.unbind();
        }
    }

}
