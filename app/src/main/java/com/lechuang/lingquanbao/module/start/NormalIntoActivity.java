package com.lechuang.lingquanbao.module.start;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.app.base.BaseActivity;
import com.common.app.constants.Constant;
import com.common.app.http.NetWork;
import com.common.app.http.api.Qurl;
import com.lechuang.lingquanbao.MyApplication;
import com.lechuang.lingquanbao.R;
import com.lechuang.lingquanbao.bean.AdvertisementBean;
import com.lechuang.lingquanbao.bean.LiveProductInfoBean;
import com.lechuang.lingquanbao.http.CommonApi;
import com.lechuang.lingquanbao.http.ResultData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NormalIntoActivity extends BaseActivity {


    @BindView(R.id.iv_img)
    ImageView mIvImg;
    @BindView(R.id.tv_time)
    TextView mTvTime;

    private Context mContext;
    //跳转的url
    private String adUrl;
    //广告跳转类型 0：跳app外web页面 1：跳商品详情
    private int type;
    private String alipayCouponId;
    private String alipayItemId;
    //商品名
    private String productName;
    //原价
    private String price;
    //优惠价
    private String preferentialPrice;
    //分享赚
    private String shareIntegral;
    //商品图
    private String img;
    //商品类型
    private String shopType;

    private ArrayList<String> smallImages;

    private LiveProductInfoBean.ProductWithBLOBsBean productWithBLOBs;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_normal_into;
    }

    @Override
    protected void initView() {
        mContext = NormalIntoActivity.this;

        getAdvertisementTime(mTvTime);
        handler.post(t);
        AdvertisementBean.AdvertisingImgBean adverBean = (AdvertisementBean.AdvertisingImgBean) getIntent().getSerializableExtra("adverBean");

        Glide.with(MyApplication.getApplication()).load(adverBean.adImage).into(mIvImg);
        type = adverBean.type;
        adUrl = adverBean.adUrl;
        alipayCouponId = adverBean.alipayCouponId;
        alipayItemId = adverBean.alipayItemId;
        if (alipayItemId != null) {
            Map<String,String> map = new HashMap<>();
            map.put("productId",alipayItemId);
            NetWork.getInstance()
                    .setTag(Qurl.getProductInfo)
                    .getApiService(CommonApi.class)
                    .getProductInfo(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ResultData<LiveProductInfoBean>(mContext,true,false) {

                        @Override
                        public void onSuccess(LiveProductInfoBean response) {
                            productWithBLOBs = response.productWithBLOBs;
                        }
                    });
        }
    }

    @Override
    protected void getData() {

    }

    @OnClick({R.id.iv_img, R.id.tv_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_img:
                if (type == 1) {
                    //handlers.sendEmptyMessage(1);
                    startActivity(new Intent(NormalIntoActivity.this, MainActivity.class));
                    // todo 点击跳转两个
//                    startActivity(new Intent(mContext, ProductDetailsActivity.class)
//                            .putExtra(Constants.listInfo, JSON.toJSONString(productWithBLOBs)));


                } else {
                    startActivity(new Intent(NormalIntoActivity.this, MainActivity.class));
                    //  todo 点击跳转两个
//                    startActivity(new Intent(mContext, EmptyWebActivity.class).putExtra("url", adUrl));
                }
                finish();
                break;
            case R.id.tv_time:
                //跳过广告图
                startActivity(new Intent(NormalIntoActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    //广告图时间倒计时
    private static int advertisement_time = Constant.ADVERTISEMENT_TIME;
    /*
    * 倒计时
    * */
    public static Handler handler;
    public static Thread t;

    //广告图倒计时
    public void getAdvertisementTime(final TextView tv) {
        handler = new Handler();
        t = new Thread() {
            @Override
            public void run() {
                super.run();
                advertisement_time--;
                tv.setText(advertisement_time + "s后跳过广告");

                if (advertisement_time <= 0) {
                    startActivity(new Intent(NormalIntoActivity.this, MainActivity.class));
                    handler = null;
                    finish();
                    return;
                }
                if (handler != null)
                    handler.postDelayed(this, 1000);
            }
        };
    }

    //广告图倒计时回收
    public void advertisementCloseCode() {
        if (handler != null) {
            handler = null;
            t = null;
            advertisement_time = Constant.ADVERTISEMENT_TIME;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        advertisementCloseCode();
    }
}
