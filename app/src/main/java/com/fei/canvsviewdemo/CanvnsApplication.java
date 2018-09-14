package com.fei.canvsviewdemo;

import android.os.Environment;

import com.fei.feilibs_1_0_0.BaseApplication;

public class CanvnsApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected String setErrorLogPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+getPackageName();
    }
}
