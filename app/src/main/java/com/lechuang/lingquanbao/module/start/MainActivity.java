package com.lechuang.lingquanbao.module.start;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.app.base.BaseActivity;
import com.common.app.database.manger.UserHelper;
import com.lechuang.lingquanbao.MyApplication;
import com.lechuang.lingquanbao.R;
import com.lechuang.lingquanbao.module.login.LoginActivity;
import com.lechuang.lingquanbao.fragment.ClasslyFragment;
import com.lechuang.lingquanbao.module.home.HomeFragment;
import com.lechuang.lingquanbao.fragment.MineFragment;
import com.lechuang.lingquanbao.fragment.SouFanLiFragment;
import com.lechuang.lingquanbao.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.ll_contest)
    LinearLayout llContest;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.main_item0)
    TextView mainItem0;
    @BindView(R.id.main_item1)
    TextView mainItem1;
    @BindView(R.id.main_item2)
    TextView mainItem2;
    @BindView(R.id.main_item3)
    TextView mainItem3;
    @BindView(R.id.main_item4)
    TextView mainItem4;


    //fragment集合
    private List<Fragment> fragments;
    //textview集合
    private List<TextView> views;

    //指定显示的界面
    private static int oldIndex = 0;
    private HomeFragment mHomeFragment;
    private ClasslyFragment mClasslyFragment;
    private VideoFragment mVideoFragment;
    private SouFanLiFragment mSouFanLiFragment;
    private MineFragment mMineFragment;

    @Override
    protected void savedInstance(@Nullable Bundle savedInstanceState) {
        super.savedInstance(savedInstanceState);
        if (savedInstanceState != null) {
            saveData();
        } else {
            initFragments();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        views = new ArrayList<>();
        views.clear();
        views.add(mainItem0);
        views.add(mainItem1);
        views.add(mainItem2);
        views.add(mainItem3);
        views.add(mainItem4);
        if (oldIndex == 0){
            views.get(0).setSelected(true);
        }
    }

    @Override
    protected void getData() {
    }

    /**
     * 用户重新加载当前界面
     */
    private void saveData() {
        mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
        mClasslyFragment = (ClasslyFragment) getSupportFragmentManager().findFragmentByTag(ClasslyFragment.class.getName());
        mVideoFragment = (VideoFragment) getSupportFragmentManager().findFragmentByTag(VideoFragment.class.getName());
        mSouFanLiFragment = (SouFanLiFragment) getSupportFragmentManager().findFragmentByTag(SouFanLiFragment.class.getName());
        mMineFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag(MineFragment.class.getName());
        fragments = new ArrayList<>();
        fragments.clear();
        fragments.add(mHomeFragment);
        fragments.add(mClasslyFragment);
        fragments.add(mVideoFragment);
        fragments.add(mSouFanLiFragment);
        fragments.add(mMineFragment);
        views = new ArrayList<>();
        views.clear();
        views.add(mainItem0);
        views.add(mainItem1);
        views.add(mainItem2);
        views.add(mainItem3);
        views.add(mainItem4);
        views.get(oldIndex).setSelected(false);
        views.get(oldIndex).setSelected(true);
    }

    /**
     * 初始化用到的Fragment
     */
    private void initFragments() {
        mHomeFragment = new HomeFragment();
        mClasslyFragment = new ClasslyFragment();
        mVideoFragment = new VideoFragment();
        mSouFanLiFragment = new SouFanLiFragment();
        mMineFragment = new MineFragment();
        fragments = new ArrayList<>();
        fragments.clear();
        fragments.add(mHomeFragment);
        fragments.add(mClasslyFragment);
        fragments.add(mVideoFragment);
        fragments.add(mSouFanLiFragment);
        fragments.add(mMineFragment);
//        默认加载前两个Fragment，其中第一个展示，第二个隐藏
        getSupportFragmentManager().beginTransaction()
                .add(R.id.ll_contest, mHomeFragment,mHomeFragment.getClass().getName())
                .add(R.id.ll_contest, mClasslyFragment,mClasslyFragment.getClass().getName())
                .add(R.id.ll_contest, mVideoFragment,mVideoFragment.getClass().getName())
                .add(R.id.ll_contest, mSouFanLiFragment,mSouFanLiFragment.getClass().getName())
                .add(R.id.ll_contest, mMineFragment,mMineFragment.getClass().getName())
                .hide(mClasslyFragment)
                .hide(mVideoFragment)
                .hide(mSouFanLiFragment)
                .hide(mMineFragment)
                .commit();

    }


    @OnClick({R.id.main_item0, R.id.main_item1, R.id.main_item2, R.id.main_item3, R.id.main_item4})
    public void onViewClicked(View view) {
        int current = oldIndex;
        switch (view.getId()) {
            case R.id.main_item0:
                current = 0;
                break;
            case R.id.main_item1:
                current = 1;
                break;
            case R.id.main_item2:
                current = 2;
                break;
            case R.id.main_item3:
                current = 3;
                break;
            case R.id.main_item4:
                if (!UserHelper.getInstence().isLogin()) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    current = 4;
                }
                break;
        }
        showCurrentFragment(current);
    }


    /**
     * 展示当前选中的Fragment
     *
     * @param currentIndex
     */
    public void showCurrentFragment(int currentIndex) {
        if (currentIndex != oldIndex) {
            views.get(oldIndex).setSelected(false);
            views.get(currentIndex).setSelected(true);
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            ft.hide(fragments.get(oldIndex));
            if (!fragments.get(currentIndex).isAdded()) {
                ft.add(R.id.ll_contest, fragments.get(currentIndex),fragments.get(currentIndex).getClass().getName());
            }
            ft.show(fragments.get(currentIndex)).commit();
            oldIndex = currentIndex;
        }
    }

    /**
     * 双击返回
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true;
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {

                @Override
                public void run() {

                    isExit = false;
                }
            }, 2000);
        } else {
            MyApplication.getApplication().exit();
            Process.killProcess(Process.myPid());
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){//竖屏

        }
    }

}
