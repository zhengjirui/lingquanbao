package com.lechuang.lingquanbao.module.home;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.app.base.BaseFragment;
import com.common.app.base.ComponentViewHolder;
import com.common.app.http.NetWork;
import com.common.app.http.api.Qurl;
import com.common.app.utils.DeviceUtils;
import com.common.app.utils.StringUtils;
import com.lechuang.lingquanbao.MyApplication;
import com.lechuang.lingquanbao.R;
import com.lechuang.lingquanbao.bean.GetBean;
import com.lechuang.lingquanbao.bean.HomeBannerBean;
import com.lechuang.lingquanbao.bean.HomeGunDongTextBean;
import com.lechuang.lingquanbao.bean.HomeKindBean;
import com.lechuang.lingquanbao.bean.HomeLastProgramBean;
import com.lechuang.lingquanbao.bean.HomeProgramBean;
import com.lechuang.lingquanbao.http.CommonApi;
import com.lechuang.lingquanbao.http.HomeApi;
import com.lechuang.lingquanbao.http.ResultData;
import com.lechuang.lingquanbao.view.CusViewPager;
import com.lechuang.lingquanbao.view.NoPreloadViewPager;
import com.lechuang.lingquanbao.view.TransChangeNesScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: zhengjr
 * @since: 2018/7/5
 * @describe:
 */

public class HomeFragment extends BaseFragment implements OnRefreshLoadMoreListener {


    @BindView(R.id.home_nesv)
    TransChangeNesScrollView mHomeNesv;
    @BindView(R.id.home_header)
    RelativeLayout mHomeHeader;
    @BindView(R.id.smart_header)
    ClassicsHeader mSmartHeader;
    @BindView(R.id.smart_footer)
    ClassicsFooter mSmartFooter;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefresh;
    @BindView(R.id.banner_home)
    Banner mBannerHome;
    @BindView(R.id.rv_home_kinddata)
    RecyclerView mRvHomeKindData;
    @BindView(R.id.tab_home_bottom)
    TabLayout mTabHomeBottom;
    @BindView(R.id.tab_home_top)
    TabLayout mTabHomeTop;
    @BindView(R.id.v_line_bottom_tab)
    View mLineBottomTab;
    @BindView(R.id.vp_home)
    CusViewPager mVpHome;
    @BindView(R.id.kuaibao_context)
    ViewGroup mKuaibaoVG;
    @BindView(R.id.iv_program1)
    ImageView mIvProgram1;
    @BindView(R.id.iv_program2)
    ImageView mIvProgram2;
    @BindView(R.id.iv_program3)
    ImageView mIvProgram3;
    @BindView(R.id.iv_program4)
    ImageView mIvProgram4;
    @BindView(R.id.ll_header_layout)
    LinearLayout mLlHeaderLayout;



    private Context mContext;
    private int page = 1;//底部产品数据的加载
    private int mTabToTopY = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mContext = getActivity();
        mSmartRefresh.setOnRefreshLoadMoreListener(this);
        mHomeNesv.setTransparentChange(mHomeHeader);
        mHomeNesv.setTopTabLayout(mLineBottomTab,mTabHomeTop);
        mVpHome.setMinHeight(mLlHeaderLayout.getHeight());
//        mHomeNesv.setNeedScrollView(mLineBottomTab);
    }

    @Override
    protected void initData() {
        super.initData();

        EventBus.getDefault().register(this);

        //初始化头部的banner
        initBannerView();

        //初始化首页分类数据
        initHomeKindData();

        //初始化首页滚动文字数据，双排
        initHomeScrollTextView();

        //初始化首页4个图片栏目数据
        initHomeProgram();

    }

    @Override
    protected void getData() {
        //首页bannder图
        getHomeBannerData();

        //首页分类数据
        getNetHomeKindData();

        //首页滚动文字数据，双排
        getHomeScrollTextView();

        //首页4个图片栏目数据
        getHomeProgram();

        //tablayout的数据
        getTabData();
    }

    /*bannerview   start */

    /**
     * 初始化数据
     */
    private ViewGroup.LayoutParams params;//记录banner的宽高
    private void initBannerView() {
        List<String> imgUrls = new ArrayList<>();
        //设置指示器位置（当banner模式中有指示器时）
        mBannerHome.setIndicatorGravity(BannerConfig.CENTER);
        mBannerHome.isAutoPlay(true);//禁止轮播
        mBannerHome.setDelayTime(2000);
        mBannerHome.setViewPagerIsScroll(true);//开启手动滑动
        mBannerHome.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {

                //Glide 加载图片简单用法
                Glide.with(context)
                        .load(path)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                if (params == null){
                                    Bitmap scaleBitmap = DeviceUtils.getScaleBitmap(resource);
                                    params = mBannerHome.getLayoutParams();
                                    params.height = scaleBitmap.getHeight();
                                    params.width = scaleBitmap.getWidth();
                                    mBannerHome.setLayoutParams(params);
                                    scaleBitmap.recycle();
                                }
                                imageView.setImageBitmap(resource);
                            }
                        });

            }
        });
        mBannerHome.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (position == imgUrls.size() - 1) {
                    //点击跳转界面
                }
            }
        });
        //设置图片集合
        mBannerHome.setImages(imgUrls);
        //banner设置方法全部调用完毕时最后调用
        mBannerHome.start();


    }

    /**
     * 获取首页轮播图数据
     */
    private void getHomeBannerData() {
        //首页轮播图数据
        NetWork.getInstance()
                .setTag(Qurl.homePageBanner)
                .getApiService(HomeApi.class)
                .homeBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultData<HomeBannerBean>(mContext,true,false) {
                    @Override
                    public void onSuccess(HomeBannerBean result) {
                        if (result == null) {
                            return;
                        }
                        setBannerData(result);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mSmartRefresh.finishRefresh();
                    }
                });
    }

    /**
     * 获取数据后更新数据
     *
     * @param result
     */
    List<String> imgUrls;

    private void setBannerData(HomeBannerBean result) {
        List<HomeBannerBean.IndexBannerList> indexBannerList0 = result.indexBannerList0;
        if (indexBannerList0 == null || indexBannerList0.isEmpty()) {
            return;
        }
        if (imgUrls == null) {
            imgUrls = new ArrayList<>();
        }
        imgUrls.clear();
        for (int i = 0; i < indexBannerList0.size(); i++) {
            imgUrls.add(indexBannerList0.get(i).img);
        }
        mBannerHome.update(imgUrls);
    }

    /*bannerview   end */


    /*初始化首页分类数据  start*/
    private GridLayoutManager rvHomeKindLayoutManger;
    private List<HomeKindBean.ListBean> kingBeans = new ArrayList<>();
    private BaseQuickAdapter kingDataAdapter;

    /**
     * 初始化首页分类数据
     */
    private void initHomeKindData() {
        if (rvHomeKindLayoutManger == null) {
            rvHomeKindLayoutManger = new GridLayoutManager(MyApplication.getApplication(), 5);
        }
        mRvHomeKindData.setLayoutManager(rvHomeKindLayoutManger);
        kingDataAdapter = new BaseQuickAdapter<HomeKindBean.ListBean, ComponentViewHolder>
                (R.layout.item_home_kinddata_child, kingBeans) {

            @Override
            protected void convert(ComponentViewHolder helper, HomeKindBean.ListBean item) {
                helper.setDisplayImage(R.id.iv_kinds_img, item.img);
                helper.setText(R.id.tv_kinds_name, item.rootName);
            }
        };
        mRvHomeKindData.setAdapter(kingDataAdapter);
    }

    /**
     * 获取网络首页分类数据
     */
    private void getNetHomeKindData() {
        //首页分类数据
        NetWork.getInstance()
                .setTag(Qurl.home_classify)
                .getApiService(HomeApi.class)
                .homeClassify()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultData<HomeKindBean>(mContext,true,false) {

                    @Override
                    public void onSuccess(HomeKindBean result) {
                        if (result == null)
                            return;
                        setHomeKindData(result);
                    }
                });
    }

    /**
     * 更新设置首页分类数据
     *
     * @param homeKindBean
     */
    public void setHomeKindData(HomeKindBean homeKindBean) {
        if (homeKindBean == null || homeKindBean.tbclassTypeList == null || homeKindBean.tbclassTypeList.isEmpty())
            return;
        kingBeans.clear();
        kingBeans.addAll(homeKindBean.tbclassTypeList);
        //取前10类
        if (kingBeans.size() > 10) {
            kingBeans = kingBeans.subList(0, 10);
        }
        kingDataAdapter.notifyDataSetChanged();
    }

    /*初始化首页分类数据  end*/



    /*初始化首页滚动文字数据，双排  start*/

    /**
     * 初始化首页滚动文字数据，双排
     */
    private void initHomeScrollTextView() {
    }

    /**
     * 首页滚动文字数据，双排
     */
    private void getHomeScrollTextView() {
        NetWork.getInstance()
                .setTag("")
                .getApiService(HomeApi.class)
                .gunDongTextProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultData<HomeGunDongTextBean>(mContext,true,false) {

                    @Override
                    public void onSuccess(HomeGunDongTextBean result) {
                        if (result == null || result.indexMsgList.isEmpty())
                            return;
                        setHomeScrollText(result);
                    }
                });
    }

    private Animator kuaibaoAniator;
    private List<HomeGunDongTextBean.IndexMsgListBean> kuaibaoData;

    private void setHomeScrollText(HomeGunDongTextBean homeGunDongTextBean) {
        if (homeGunDongTextBean == null || homeGunDongTextBean.indexMsgList == null || homeGunDongTextBean.indexMsgList.isEmpty())
            return;
        kuaibaoData = homeGunDongTextBean.indexMsgList;
        kuaibaoIndex = 0;
        kuaibaoRunnable.run();
    }


//    private int[] kuaibaoImageRes = {R.drawable.kuaibao_1, R.drawable.kuaibao_2, R.drawable.kuaibao_3,
//            R.drawable.kuaibao_4, R.drawable.kuaibao_5, R.drawable.kuaibao_6, R.drawable.kuaibao_7,
//            R.drawable.kuaibao_8, R.drawable.kuaibao_9, R.drawable.kuaibao_10};

    /**
     * 返回快报的垂直滚动动画
     */
    private Animator getAnimatorKuaibao() {
        if (kuaibaoAniator == null) {
            Property<View, Integer> p = new Property<View, Integer>(Integer.class, "y") {
                @Override
                public Integer get(View object) {
                    ViewGroup.MarginLayoutParams vmlp = (ViewGroup.MarginLayoutParams) object.getLayoutParams();
                    return vmlp.topMargin;
                }

                @Override
                public void set(View object, Integer value) {
                    ViewGroup.MarginLayoutParams vmlp = (ViewGroup.MarginLayoutParams) object.getLayoutParams();
                    vmlp.topMargin = value;
                    object.requestLayout();
                }
            };
            kuaibaoAniator = ObjectAnimator.ofInt(mKuaibaoVG, p, 0, -mKuaibaoVG.getChildAt(0).getLayoutParams().height);
            kuaibaoAniator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    kuaibaoIndex++;
                    setKuaibaoInfo();
                    //位置还原
                    ViewGroup.MarginLayoutParams vmlp = (ViewGroup.MarginLayoutParams) mKuaibaoVG.getLayoutParams();
                    vmlp.topMargin = 0;
                    mKuaibaoVG.requestLayout();
                    handler.postDelayed(kuaibaoRunnable, 3000);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            kuaibaoAniator.setDuration(300);
        }
        return kuaibaoAniator;
    }

    private Handler handler = new Handler();
    /**
     * 快报当前滚动的索引，结束后才会+1
     */
    private int kuaibaoIndex;
    private Runnable kuaibaoRunnable = () -> {
        if (kuaibaoData == null) {
            return;
        }
        if (isVisible()) {
            Animator animator = getAnimatorKuaibao();
            if (animator != null) {
                setKuaibaoInfo();
                animator.start();
            }
        } else {
            //如果不显示，3秒钟后再判断
            handler.postDelayed(this.kuaibaoRunnable, 3000);
        }
    };

    /**
     * 设置快报信息
     */
    private void setKuaibaoInfo() {
        int[] indexs = {kuaibaoIndex % kuaibaoData.size(), (kuaibaoIndex + 1) % kuaibaoData.size()};
        for (int i = 0; i < indexs.length; i++) {
            View child = mKuaibaoVG.getChildAt(i);
            TextView name = child.findViewById(R.id.kuaibao_name);
            TextView text = child.findViewById(R.id.kuaibao_text);
            ImageView image = child.findViewById(R.id.kuaibao_image);
            HomeGunDongTextBean.IndexMsgListBean item = kuaibaoData.get(indexs[i]);
            name.setText(item.productName);
            text.setText(item.productText);
            //这里会有一个小BUG，可能有几张图片永远不会显示，但估计没有人会在意。
//            image.setBackgroundResource(kuaibaoImageRes[indexs[i]]);
            Glide.with(MyApplication.getApplication()).load(item.img).into(image);
        }
        if (kuaibaoIndex % kuaibaoData.size() == 0) {
            //防止无限大
            kuaibaoIndex = 0;
        }
    }

    /*初始化首页滚动文字数据，双排  end*/




    /*初始化首页4个栏目数据  start*/
    /**
     * 初始化首页4个栏目数据
     */
    private void initHomeProgram() {

    }

    /**
     * 首页4个图片栏目数据
     */
    private void getHomeProgram() {
        NetWork.getInstance()
                .getApiService(HomeApi.class)
                .homeProgramaImg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultData<HomeProgramBean>(mContext,true,false) {

                    @Override
                    public void onSuccess(HomeProgramBean result) {
                        if (result == null)
                            return;
                        setHomeProgram(result);
                    }
                });
    }

    private void setHomeProgram(HomeProgramBean homeProgramBean) {
        if (homeProgramBean == null || homeProgramBean.programaImgList == null || homeProgramBean.programaImgList.isEmpty())
            return;

        List<HomeProgramBean.ListBean> programaImgList = homeProgramBean.programaImgList;

        Glide.with(MyApplication.getApplication()).load(StringUtils.getString(programaImgList.get(0).img)).into(mIvProgram1);
        Glide.with(MyApplication.getApplication()).load(StringUtils.getString(programaImgList.get(1).img)).into(mIvProgram2);
        Glide.with(MyApplication.getApplication()).load(StringUtils.getString(programaImgList.get(2).img)).into(mIvProgram3);
        Glide.with(MyApplication.getApplication()).load(StringUtils.getString(programaImgList.get(3).img)).into(mIvProgram4);
    }

    /*初始化首页4个栏目数据  end*/


    /*获取首页的tablayout数据  start*/
    /**
     * 获取首页的tablayout数据
     */
    private void getTabData() {
        NetWork.getInstance()
                .getApiService(CommonApi.class)
                .topTabList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultData<GetBean>(mContext,true,false) {

                    @Override
                    public void onSuccess(GetBean result) {

                        if (result == null || result.tbclassTypeList == null || result.tbclassTypeList.isEmpty()) {
                            return;
                        }
                        setTabData(result);
                    }
                });
    }

    /**
     * 设置首页的tablayout数据
     *
     * @param helper
     * @param getBean
     */
    private List<GetBean.TopTab> mTopTabs = new ArrayList<>();
    private List<TabViewPagerFragment> mTabFragment = new ArrayList<>();
    private int mCurrentSelectTab = 0;

    private void setTabData(GetBean result) {
        if (mTopTabs.isEmpty()) {
            GetBean.TopTab topTab = new GetBean.TopTab();
            topTab.rootId = 99;
            topTab.rootName = "精选";
            mTopTabs.add(topTab);
            for (GetBean.TopTab tab : result.tbclassTypeList) {
                mTopTabs.add(tab);
            }
            mTabHomeBottom.removeAllTabs();
            mTabHomeTop.removeAllTabs();
            mVpHome.setOffscreenPageLimit(0);
            //mTabHomeBottom.setupWithViewPager(mVpHome);//将TabLayout和ViewPager关联起来。
            //mTabHomeTop.setupWithViewPager(mVpHome);//将TabLayout和ViewPager关联起来。
            mTabHomeBottom.setFocusable(false);
            mTabHomeTop.setFocusable(false);
            for (int i = 0; i < mTopTabs.size(); i++) {
                mTabFragment.add(TabViewPagerFragment.newInstence());
                mTabHomeBottom.addTab(mTabHomeBottom.newTab().setText(mTopTabs.get(i).rootName));
                mTabHomeTop.addTab(mTabHomeTop.newTab().setText(mTopTabs.get(i).rootName));
            }
            mVpHome.setAdapter(new TabViewPagerAdapter(MyApplication.getApplication(), getChildFragmentManager(), mTopTabs, mTabFragment));

            TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mCurrentSelectTab = tab.getPosition();
                    mVpHome.setCurrentItem(mCurrentSelectTab);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            };

            mTabHomeBottom.addOnTabSelectedListener(onTabSelectedListener);
            mTabHomeTop.addOnTabSelectedListener(onTabSelectedListener);
            mVpHome.setOnPageChangeListener(new NoPreloadViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    mTabToTopY = mLineBottomTab.getTop() - mHomeHeader.getHeight();
                    Log.e("-------",mTabToTopY +"*******" + mLineBottomTab.getTop() + "#####" +mHomeHeader.getHeight());
                    mHomeNesv.smoothScrollTo(0,mTabToTopY);
                    mCurrentSelectTab = position;
                    mTabHomeBottom.setScrollPosition(mCurrentSelectTab,0,true);
                    mTabHomeTop.setScrollPosition(mCurrentSelectTab,0,true);

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getProductData(HomeProductEvent homeProductEvent) {
        if (mTabFragment == null || mTabFragment.isEmpty() || mTabFragment.size() < mCurrentSelectTab + 1) {
            return;
        }
        TabViewPagerFragment tabViewPagerFragment = mTabFragment.get(mCurrentSelectTab);
        if (tabViewPagerFragment == null) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("page", homeProductEvent.page + "");
        map.put("classTypeId", homeProductEvent.classTypeId);
        NetWork.getInstance()
                .setTag(Qurl.home_lastPage)
                .getApiService(HomeApi.class)//13683766091
                .homeLastProgram(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultData<HomeLastProgramBean>(mContext,false,true) {
                    @Override
                    public void onSuccess(HomeLastProgramBean result) {
                        if (result == null){
                            return;
                        }
                        tabViewPagerFragment.refreshAndLoadMoreData(page,result);
                        onFinishRefreshLoadMore(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        tabViewPagerFragment.mIsFirstVisible = true;
                        onFinishRefreshLoadMore(false);
                    }

                    @Override
                    public void onFailed(int errorCode, String moreInfo) {
                        super.onFailed(errorCode, moreInfo);
                        tabViewPagerFragment.mIsFirstVisible = true;
                        onFinishRefreshLoadMore(false);
                    }
                });
    }

    /*获取首页的tablayout数据  end*/

    /**
     * 这里的加载更多是对底部的产品加载更多的数据
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        //触发加载底部数据
        if (mTopTabs == null || mTopTabs.isEmpty() ||mTopTabs.size() < mCurrentSelectTab + 1 )
            return;
        EventBus.getDefault().post(new HomeProductEvent(page,StringUtils.getString(String.valueOf(mTopTabs.get(mCurrentSelectTab).rootId))));
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page = 1;
        getData();//刷新其他所有数据


        //触发加载底部数据
        if (mTopTabs == null || mTopTabs.isEmpty() ||mTopTabs.size() < mCurrentSelectTab + 1 )
            return;
        EventBus.getDefault().post(new HomeProductEvent(page,StringUtils.getString(String.valueOf(mTopTabs.get(mCurrentSelectTab).rootId))));
    }

    private void onFinishRefreshLoadMore(boolean stateRefreshLoadMore) {
        if (page == 1) {
            mSmartRefresh.finishRefresh(500, stateRefreshLoadMore);
        } else {
            mSmartRefresh.finishLoadMore(500);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
