package com.lechuang.lingquanbao.http;

import com.common.app.base.bean.BaseResponseBean;
import com.common.app.http.api.Qurl;
import com.lechuang.lingquanbao.bean.LoginBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by cmd on 2018/7/15.
 */

public interface LoginApi {

    @FormUrlEncoded
    @POST(Qurl.verificode)
    Observable<BaseResponseBean<String>> verificode(@FieldMap Map<String, String> allParamMap);

    @FormUrlEncoded
    @POST(Qurl.register)
    Observable<BaseResponseBean<String>> register(@FieldMap Map<String, String> allParamMap);

    @FormUrlEncoded
    @POST(Qurl.login)
    Observable<BaseResponseBean<LoginBean>> login(@FieldMap Map<String, String> allParamMap);
}
