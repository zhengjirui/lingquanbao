package com.common.app.constants;

import com.common.BuildConfig;

/**
 * Author：CHENHAO
 * date：2018/5/3
 * desc：
 */

public interface Constant {
    String BASE_URL = BuildConfig.BASE_URL;
    int OKHTTP_TIMEOUT = 10;

    String LOGIN_SUCCESS = "login_success";
    String LOGOUT_SUCCESS = "logout_success";

    String ISFIRSTOPEN = "is_first_open";//判断用户第一次打开APP

    //60秒倒计时
    int TIME = 60;
    //广告图6秒倒计时
    int ADVERTISEMENT_TIME = 6;

    int LY_0 = 0;
    int LY_1 = 1;
    int LY_2 = 2;
    int LY_3 = 3;
    int LY_4 = 4;
    int LY_5 = 5;
}
