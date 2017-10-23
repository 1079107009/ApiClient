package com.lp.apiclient;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * @author LiPin
 * @date 2017/10/23 10:00
 * 描述：
 */

public class App extends Application {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Stetho.initializeWithDefaults(this);
    }
}
