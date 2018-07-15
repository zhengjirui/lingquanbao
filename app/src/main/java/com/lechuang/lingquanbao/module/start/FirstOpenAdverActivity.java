package com.lechuang.lingquanbao.module.start;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.common.app.base.BaseActivity;
import com.common.app.constants.Constant;
import com.common.app.utils.SPUtils;
import com.lechuang.lingquanbao.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;


public class FirstOpenAdverActivity extends BaseActivity {

    @BindView(R.id.banner_first_into_adver)
    Banner mBannerFirstIntoAdver;
    private ArrayList<String> mFirstIntoData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_first_open_adver;
    }

    @Override
    protected void initView() {
        mFirstIntoData = getIntent().getStringArrayListExtra("firstIntoData");
        //设置图片集合
        mBannerFirstIntoAdver.setImages(mFirstIntoData);
        //设置指示器位置（当banner模式中有指示器时）
        mBannerFirstIntoAdver.setIndicatorGravity(BannerConfig.CENTER);
        mBannerFirstIntoAdver.isAutoPlay(true);//禁止轮播
        mBannerFirstIntoAdver.setDelayTime(2000);
        mBannerFirstIntoAdver.setViewPagerIsScroll(true);//开启手动滑动
        //轮播时间
        mBannerFirstIntoAdver.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //Glide 加载图片简单用法
                Glide.with(context).load(path).into(imageView);
            }
        });
        //banner设置方法全部调用完毕时最后调用
        mBannerFirstIntoAdver.start();

        mBannerFirstIntoAdver.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if(position == mFirstIntoData.size()-1){
                    //点击跳转主界面
                    Intent intent = new Intent(FirstOpenAdverActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }
            }
        });
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        mBannerFirstIntoAdver.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        mBannerFirstIntoAdver.stopAutoPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPUtils.getInstance().putBoolean(this, Constant.ISFIRSTOPEN, false);
    }
}
