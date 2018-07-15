package com.common.app.base;

import android.app.Activity;
import android.app.Application;

import com.common.app.utils.Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Author：CHENHAO
 * date：2018/5/3
 * desc：组件开发中我们的application是放在debug包下的，进行集成合并时是需要移除掉的，
 * 所以组件module中不能使用debug包下的application的context，
 * 因此组件中必须通过Utils.getContext()方法来获取全局 Context
 */

public class BaseApplication extends Application {

    private static BaseApplication application;
    //单例模式中获取唯一的MyApplication实例
    private List<Activity> activityList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Utils.init(this);
    }

    public static BaseApplication getApplication(){
        return application;
    }


    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    //遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}
