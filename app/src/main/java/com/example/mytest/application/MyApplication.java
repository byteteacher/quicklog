package com.example.mytest.application;

import com.byteteacher.library.base.BaseApplication;
import com.byteteacher.library.quicklog.LogSaver;

/**
 * Created by cj on 2021/7/19.
 */
public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LogSaver.init();

    }

}
