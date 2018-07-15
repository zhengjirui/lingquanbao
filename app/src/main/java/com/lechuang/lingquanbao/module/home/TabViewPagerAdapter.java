package com.lechuang.lingquanbao.module.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lechuang.lingquanbao.bean.GetBean;

import java.util.List;

/**
 * @author: zhengjr
 * @since: 2018/7/12
 * @describe:
 */

public class TabViewPagerAdapter extends FragmentPagerAdapter{

    private Context mContext;
    private List<GetBean.TopTab> mTopTabs;
    private List<TabViewPagerFragment> mTabFragment;
    public TabViewPagerAdapter(Context context,FragmentManager fm, List<GetBean.TopTab> topTabs,List<TabViewPagerFragment> tabFragment) {
        super(fm);
        this.mContext = context;
        this.mTopTabs = topTabs;
        this.mTabFragment = tabFragment;
    }

    @Override
    public Fragment getItem(int position) {
        //https://www.cnblogs.com/android-blogs/p/5524172.html  缓存网址
        Bundle bundle = new Bundle();
        bundle.putSerializable("TopTab",mTopTabs.get(position));
        TabViewPagerFragment tabViewPagerFragment = mTabFragment.get(position);
        tabViewPagerFragment.setArguments(bundle);
        return tabViewPagerFragment;
    }

    @Override
    public int getCount() {
        return mTabFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTopTabs.get(position).rootName;
    }
}
