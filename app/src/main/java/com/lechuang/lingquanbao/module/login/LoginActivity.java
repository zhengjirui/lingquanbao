package com.lechuang.lingquanbao.module.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Network;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.app.base.BaseActivity;
import com.common.app.database.bean.UserInfoBean;
import com.common.app.database.manger.UserHelper;
import com.common.app.http.NetWork;
import com.common.app.http.RxObserver;
import com.common.app.utils.ShowToast;
import com.common.app.utils.StringUtils;
import com.common.app.utils.TDevice;
import com.common.app.utils.Utils;
import com.lechuang.lingquanbao.R;
import com.lechuang.lingquanbao.bean.LoginBean;
import com.lechuang.lingquanbao.http.LoginApi;
import com.lechuang.lingquanbao.http.ResultData;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_tv)
    TextView loginTv;
    @BindView(R.id.login_line)
    View loginLine;
    @BindView(R.id.reg_tv)
    TextView regTv;
    @BindView(R.id.reg_line)
    View regLine;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.ver_et)
    EditText verEt;
    @BindView(R.id.getVer)
    TextView getVer;
    @BindView(R.id.verrl)
    RelativeLayout verrl;
    @BindView(R.id.pass_et)
    EditText passEt;
    @BindView(R.id.delu_rl)
    RelativeLayout deluRl;
    @BindView(R.id.button)
    TextView button;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.bottomll)
    LinearLayout bottomll;

    private boolean isLogin;
    public static boolean startups = false;
    private int time = 60;
    private CountDownTimer countDownTimer;
    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mContext = LoginActivity.this;
        isLogin = true;
        showDL();
    }

    @Override
    protected void initData() {
        super.initData();
        UserHelper.getInstence().deleteAllUserInfo();
    }

    @Override
    protected void getData() {

    }
    @OnClick({R.id.reg_ly, R.id.login_ly, R.id.getVer, R.id.userxieyi, R.id.getPass, R.id.button, R.id.tb_lg, R.id.tb_lg1, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reg_ly:
                if (isLogin)
                    showREG();
                TDevice.hideSoftKeyboard(getWindow().getDecorView());
                break;
            case R.id.login_ly:
                if (!isLogin) {
                    if (isCountTime) {
                        countDownTimer.cancel();
                        isCountTime = false;
                        time = 60;
                        getVer.setEnabled(true);
                        getVer.setText("获取验证码");
                        getVer.setTextColor(Color.WHITE);
                    }
                    showDL();
                }
                TDevice.hideSoftKeyboard(getWindow().getDecorView());
                break;
            case R.id.getVer:
                getVerCode();
                break;
            case R.id.userxieyi:
//                NormalWebActivity.lanuchActivity(this, "用户协议", QUrl.userAgreement);
                break;
            case R.id.getPass:
//                startActivity(new Intent(this, GetPass.class));
                break;
            case R.id.button:
                TDevice.hideSoftKeyboard(getWindow().getDecorView());
                if (isLogin)
                    login();
                else
                    regUser();
                break;
            case R.id.tb_lg:
            case R.id.tb_lg1:
                taobaoLogin();
                break;
            case R.id.back:
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onDestroy();
        startups = false;
    }


    private void showDL() {
        isLogin = true;
        content.setBackgroundResource(R.mipmap.bg_denglu);
        loginTv.setTextColor(Color.parseColor("#FF5C19"));
        loginLine.setVisibility(View.VISIBLE);
        regTv.setTextColor(Color.parseColor("#848484"));
        regLine.setVisibility(View.INVISIBLE);
        verrl.setVisibility(View.GONE);
        deluRl.setVisibility(View.VISIBLE);
        bottomll.setVisibility(View.VISIBLE);
        button.setText("登录");

    }

    private void showREG() {
        isLogin = false;
        content.setBackgroundResource(R.mipmap.bg_denglu);
        regTv.setTextColor(Color.parseColor("#FF5C19"));
        regLine.setVisibility(View.VISIBLE);
        loginTv.setTextColor(Color.parseColor("#848484"));
        loginLine.setVisibility(View.INVISIBLE);
        verrl.setVisibility(View.VISIBLE);
        verrl.setVisibility(View.VISIBLE);
        deluRl.setVisibility(View.GONE);
        bottomll.setVisibility(View.GONE);
        button.setText("完成");
        getVer.setTextColor(Color.WHITE);
    }

    private void login() {
        String phone = phoneEt.getText().toString().trim();
        String pass = passEt.getText().toString().trim();
        if (TextUtils.isEmpty(pass) || pass.length() < 6) {
            ShowToast.getInstance().showShortToast("请正确输入密码");
            return;
        }
        pass = StringUtils.md5(pass);
        Map<String,String> param = new HashMap();
        param.put("u",phone);
        param.put("p",pass);


        //开始登陆
        NetWork.getInstance()
                .getApiService(LoginApi.class)
                .login(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultData<LoginBean>(mContext) {
                    @Override
                    public void onSuccess(LoginBean result) {
                        if (result == null || result.getUser() == null){
                            return;
                        }
                        LoginBean.UserBean user = result.getUser();
                        UserInfoBean userInfoBean = new UserInfoBean();
                        userInfoBean.setId(StringUtils.getString(user.getId()));
                        userInfoBean.setIsLogin(true);
                        userInfoBean.setSafeToken(StringUtils.getString(user.getSafeToken()));
                        userInfoBean.setPhone(StringUtils.getString(user.getPhone()));
                        userInfoBean.setFirstLoginFlag(StringUtils.getString(user.getFirstLoginFlag()));
                        UserHelper.getInstence().saveUserInfo(userInfoBean);
                        finish();
                    }
                });
    }

    private void regUser() {
        String phone = phoneEt.getText().toString().trim();
        if (!StringUtils.isPhoneNum(phone)) {
            ShowToast.getInstance().showShortToast("请正确输入手机号");
            return;
        }
        String verCode = verEt.getText().toString().trim();
        if (TextUtils.isEmpty(verCode) || verCode.length() != 6) {
            ShowToast.getInstance().showShortToast("请正确输入验证码");
            return;
        }
        String pass = passEt.getText().toString().trim();
        if (TextUtils.isEmpty(pass) || pass.length() < 6) {
            ShowToast.getInstance().showShortToast("请正确输入密码");
            return;
        }
        pass = StringUtils.md5(pass);
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", pass);
        map.put("verifiCode", verCode);
        //开始注册
        NetWork.getInstance()
                .getApiService(LoginApi.class)
                .register(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultData<String>(mContext) {

                    @Override
                    public void onSuccess(String result) {
                        ShowToast.getInstance().showShortToast(result);
                    }
                });
    }

    private void getVerCode() {
        String phone = phoneEt.getText().toString().trim();
        if (!StringUtils.isPhoneNum(phone)) {
            ShowToast.getInstance().showShortToast("请正确输入手机号");
            return;
        }
        Map<String,String> param = new HashMap<>();
        param.put("phone",phone);
        NetWork.getInstance()
                .getApiService(LoginApi.class)
                .verificode(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultData<String>(mContext) {
                    @Override
                    public void onSuccess(String result) {
                        countTime();
                    }
                });

    }

    private boolean isCountTime = false;

    private void countTime() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                getVer.setText("重新发送(" + --time + ")");
            }

            @Override
            public void onFinish() {
                isCountTime = false;
                time = 60;
                getVer.setEnabled(true);
                getVer.setText("获取验证码");
                getVer.setTextColor(Color.WHITE);
            }
        };
        getVer.setTextColor(Color.parseColor("#999999"));
        getVer.setEnabled(false);
        countDownTimer.start();
        isCountTime = true;
    }

    private void taobaoLogin() {
        /*final AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.showLogin(this, new AlibcLoginCallback() {

            @Override
            public void onFailure(int i, String s) {

            }

            @Override
            public void onSuccess() {
                Session taobao = alibcLogin.getSession();
                UserInfo userInfo = new UserInfo();
                userInfo.photo = taobao.avatarUrl;
                userInfo.nickName = taobao.nick;
                UserHelper.saveUserInfo(LoginActivity.this, userInfo);
                threeLogin(userInfo.nickName);
            }
        });*/


    }

    /**
     * @author li
     * 邮箱：961567115@qq.com
     * @time 2017/10/5  19:24
     * @describe 绑定手机号
     */
    public void threeLogin(String params) {
        /*Network.getInstance().getApi(CommenApi.class)
                .threeLogin(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetResultHandler<UserInfo>() {

                    @Override
                    public void success(UserInfo result) {  //代表之前绑定过手机号码
                        //用户信息
                        //登录状态设为true
                        if (result == null)
                            return;
                        //绑定的支付宝号
                        UserHelper.saveUserInfo(LoginActivity.this, result);
                        finish();
                    }

                    @Override
                    public void error(int code, String msg) {
                        if (code == 300) {    //第一次登录淘宝账号,要绑定手机号
                            showShortToast(msg);
                            startActivity(new Intent(LoginActivity.this, BoundPhoneActivity.class));
                            finish();
                        }
                    }
                });*/

    }
}
