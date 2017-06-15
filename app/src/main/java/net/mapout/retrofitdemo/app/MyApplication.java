package net.mapout.retrofitdemo.app;

import android.app.Application;

import net.mapout.retrofit.util.MobileInfo;

/**
 * Created by Chasing on 2017/6/9.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MobileInfo.getInstance().initDeviceInfo(this);
    }
}
