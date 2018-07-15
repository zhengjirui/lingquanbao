package com.lechuang.lingquanbao.module.home;

/**
 * Created by cmd on 2018/7/15.
 */

public class HomeProductEvent {

    public int page;
    public String classTypeId;

    public HomeProductEvent(int page, String classTypeId) {
        this.page = page;
        this.classTypeId = classTypeId;
    }
}
