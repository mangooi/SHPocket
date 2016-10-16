package com.mangooi.shpocket;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

/**
 *
 * Created by Administrator on 2016/10/13.
 */

public class MyApplication extends Application{

    Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext=this;
        init();
    }

    private void init() {
        SDKInitializer.initialize(mAppContext);
    }
}
