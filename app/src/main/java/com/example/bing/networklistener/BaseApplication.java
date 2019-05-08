package com.example.bing.networklistener;

import android.app.Application;

import com.example.bing.library.NetworkManager;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getDefault().init(this);
    }
}
