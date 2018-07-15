package com.common.app.http;

import android.text.TextUtils;
import android.util.Log;

import com.common.BuildConfig;
import com.common.app.constants.Constant;
import com.common.app.database.manger.UserHelper;
import com.common.app.http.cancle.ApiCancleManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：CHENHAO
 */

public class NetWork {
    private static NetWork manager;
    private Retrofit retrofit;
    /*private RetrofitServer server;*/

    private NetWork() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String msg = URLDecoder.decode(message, "utf-8");
                    Log.i("OkHttpInterceptor----->", msg);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.i("OkHttpInterceptor----->", message);
                }
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor userAgentIntercepter = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String safeToken = UserHelper.getInstence().getUserInfo().getSafeToken();
                Request request = chain.request().newBuilder()
                        .addHeader("safeToken", TextUtils.isEmpty(safeToken) ? "" : safeToken)
                        .addHeader("client", "android")
                        .addHeader("version", BuildConfig.VERSION_NAME)
                        .build();
                return chain.proceed(request);
            }
        };

        /** OKHttp默认三个超时时间是10s，有些请求时间比较长，需要重新设置下 **/
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Constant.OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constant.OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constant.OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .addInterceptor(userAgentIntercepter)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        /*server = retrofit.create(RetrofitServer.class);*/

    }

    /**
     * 单例模式
     */
    public static NetWork getInstance() {
        if (manager == null) {
            synchronized (NetWork.class) {
                if (manager == null) {
                    manager = new NetWork();
                }
            }
        }
        return manager;
    }

    /*public RetrofitServer getApiService(){
        return manager.server;
    }*/

    public <T> T getApiService(Class<T> apiServer) {
        return retrofit.create(apiServer);
    }

    //设置tag取消请求标签
    public NetWork setTag(String tag) {
        ApiCancleManager.getInstance().setTagValue(tag);
        return manager;
    }
}
