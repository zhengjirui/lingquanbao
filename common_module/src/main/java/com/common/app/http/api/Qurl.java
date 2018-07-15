package com.common.app.http.api;

import com.common.BuildConfig;

/**
 * @author: zhengjr
 * @since: 2018/6/22
 * @describe:
 */

public interface Qurl {

    String HOST = BuildConfig.BASE_URL;


    //首次打开app图片
    String loadingImg = "indexShow/start_img";

    //进入app时的广告图，每次都会请求的数据加载
    String advertisementInfo = "showAdviertisement/advertisingImg";

    /**
     * 通用接口
     */
    //通过商品id获取商品的详细信息
    String getProductInfo = "zhuanPage/zhuanProductDetail";

    //顶部tablayout列表
    String getTopTabList = "zhuanPage/classifyShowAll";


    /**
     * 登录接口
     */
    //发送验证码
    String verificode = "retrieve/activeVerifiCode";
    //注册
    String register = "retrieve/register";
    //登录
    String login  = "retrieve/login";


    /**
     * 首页接口
     */
    //首页轮播图
    String homePageBanner = "indexShow/homePageBanner";
    //首页分类
    String home_classify = "indexShow/classifyShowAll";
    //滚动文字商品
    String gunDongTextProduct="indexShow/daShu_ScrollBar";
    //首页四个栏目图片接口
    String home_programaImg = "indexShow/ProgramaImg";
    //首页最下栏目接口
    String home_lastPage = "indexShow/homeLastPage";


}
