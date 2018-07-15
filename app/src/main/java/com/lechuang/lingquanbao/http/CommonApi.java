package com.lechuang.lingquanbao.http;

import com.common.app.base.bean.BaseResponseBean;
import com.common.app.http.api.Qurl;
import com.lechuang.lingquanbao.bean.AdvertisementBean;
import com.lechuang.lingquanbao.bean.GetBean;
import com.lechuang.lingquanbao.bean.LiveProductInfoBean;
import com.lechuang.lingquanbao.bean.LoadingImgBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author: zhengjr
 * @since: 2018/7/5
 * @describe:
 */

public interface CommonApi {


    //首次打开app的导航图
    @GET(Qurl.loadingImg)
    Observable<BaseResponseBean<LoadingImgBean>> loadingImg();

    //进入app时的广告图，每次都会加载的信息
    @POST(Qurl.advertisementInfo)
    Observable<BaseResponseBean<AdvertisementBean>> advertisementInfo();

    //根据商品id获取商品详细信息
    @FormUrlEncoded
    @POST(Qurl.getProductInfo)
    Observable<BaseResponseBean<LiveProductInfoBean>> getProductInfo(@FieldMap Map<String, String> allParamMap);

    /**
     * 顶部tab列表
     * @return
     */
    @POST(Qurl.getTopTabList)
    Observable<BaseResponseBean<GetBean>> topTabList();
}
