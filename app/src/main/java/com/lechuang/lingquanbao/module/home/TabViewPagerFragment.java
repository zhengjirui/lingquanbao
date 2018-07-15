package com.lechuang.lingquanbao.module.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.app.base.ComponentViewHolder;
import com.common.app.base.LazyBaseFragment;
import com.common.app.utils.StringUtils;
import com.lechuang.lingquanbao.MyApplication;
import com.lechuang.lingquanbao.R;
import com.lechuang.lingquanbao.bean.GetBean;
import com.lechuang.lingquanbao.bean.HomeLastProgramBean;
import com.lechuang.lingquanbao.view.SpannelTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: zhengjr
 * @since: 2018/7/12
 * @describe:
 */

public class TabViewPagerFragment extends LazyBaseFragment {
    @BindView(R.id.rv_home_tabviewpager)
    RecyclerView mRvHomeTabViewPager;

    private GetBean.TopTab mTopTab;

    public static TabViewPagerFragment newInstence() {
        return newInstence(null);
    }

    public static TabViewPagerFragment newInstence(Bundle bundle){
        TabViewPagerFragment mTabViewPagerFragment = new TabViewPagerFragment();
        if (bundle != null){
            mTabViewPagerFragment.setArguments(bundle);
        }
        return mTabViewPagerFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tabviewpager;
    }

    @Override
    protected void initView() {

        Bundle arguments = getArguments();
        if (arguments != null){
            mTopTab = (GetBean.TopTab) arguments.getSerializable("TopTab");
        }
    }

    private List<HomeLastProgramBean.ListBean> mProductList = new ArrayList<>();
    private BaseQuickAdapter mBaseQuickAdapter;
    @Override
    protected void initData() {
        super.initData();

        if (mBaseQuickAdapter == null){
            mBaseQuickAdapter = new BaseQuickAdapter<HomeLastProgramBean.ListBean,ComponentViewHolder>(R.layout.item_home_tabviewpager,mProductList) {
                @Override
                protected void convert(ComponentViewHolder helper, HomeLastProgramBean.ListBean item) {
                    if (item == null){
                        return;
                    }
                    helper.setDisplayImage(R.id.iv_img,StringUtils.getString(item.imgs));
                    SpannelTextView stv = helper.getView(R.id.stv_product_name);
                    stv.setDrawText(StringUtils.getString(item.name));
                    stv.setShopType(Integer.valueOf(item.shopType));
                    helper.setText(R.id.tv_zhuan, StringUtils.getString(item.zhuanMoney));
                    helper.setText(R.id.tv_yuanjia,StringUtils.getString(item.price));
                    helper.setText(R.id.tv_xiaoliang,StringUtils.getString(String.valueOf(item.nowNumber)));
                    helper.setText(R.id.tv_quanMoney,"领券减" +StringUtils.getString(item.couponMoney));

                }
            };
            mBaseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                }
            });
        }
        mRvHomeTabViewPager.setAdapter(mBaseQuickAdapter);
        mRvHomeTabViewPager.setHasFixedSize(true);
        mRvHomeTabViewPager.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MyApplication.getApplication(), 2);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        mRvHomeTabViewPager.setLayoutManager(gridLayoutManager);
    }

    private int page = 1;
    @Override
    protected void getData() {
        EventBus.getDefault().post(new HomeProductEvent(page,StringUtils.getString(String.valueOf(mTopTab.rootId))));
    }

    public void refreshAndLoadMoreData(int page,HomeLastProgramBean homeLastProgramBean){
        if (page == 1){
            mProductList.clear();
        }
        mProductList.addAll(homeLastProgramBean.productList);
        mBaseQuickAdapter.notifyDataSetChanged();
    }
}
