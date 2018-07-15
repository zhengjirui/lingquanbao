package com.lechuang.lingquanbao.http;

import com.common.app.base.bean.BaseResponseBean;
import com.common.app.http.api.Qurl;
import com.lechuang.lingquanbao.bean.HomeBannerBean;
import com.lechuang.lingquanbao.bean.HomeGunDongTextBean;
import com.lechuang.lingquanbao.bean.HomeKindBean;
import com.lechuang.lingquanbao.bean.HomeLastProgramBean;
import com.lechuang.lingquanbao.bean.HomeProgramBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author: zhengjr
 * @since: 2018/6/22
 * @describe:
 */

public interface HomeApi {
    /**
     * 首页banner图
     */
//    @GET(Qurl.homeLastData)
//    Observable<HomeLastDataBean> getHomeLastData();

    /**
     * 首页轮播图接口
     * 入参 无
     */
    @POST(Qurl.homePageBanner)
    Observable<BaseResponseBean<HomeBannerBean>> homeBanner();

    /**
     * 分类接口
     * 入参 无
     */
    @POST(Qurl.home_classify)
    Observable<BaseResponseBean<HomeKindBean>> homeClassify();

    /**
     * 首页滚动商品的数据
     */
    @POST(Qurl.gunDongTextProduct)
    Observable<BaseResponseBean<HomeGunDongTextBean>> gunDongTextProduct();

    /**
     * 首页四个栏目图片接口
     * 入参 无
     */
    @POST(Qurl.home_programaImg)
    Observable<BaseResponseBean<HomeProgramBean>> homeProgramaImg();

    /**
     *
     * 首页最下栏目分类商品集合接口
     * 入参  page  分页    classTypeId  分类id,全部栏目不传        is_official   全部传1,其他不传
     *
     */
    @FormUrlEncoded
    @POST(Qurl.home_lastPage)
    Observable<BaseResponseBean<HomeLastProgramBean>> homeLastProgram(@FieldMap HashMap<String, String> allParamMap);


}
