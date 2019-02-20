package com.example.bing.library;

import android.app.Application;
import android.content.IntentFilter;

import com.example.bing.library.listener.NetChangeObserver;
import com.example.bing.library.utils.Constants;

public class NetworkManager {

    private static volatile NetworkManager instance;
    private Application application;
    private NetStateReceiver receiver;

    private NetworkManager() {
        receiver = new NetStateReceiver();
    }

    public static NetworkManager getDefault() {
        if(instance == null) {
            synchronized (NetworkManager.class) {
                if(instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    public Application getApplication() {
        if(application == null) {
            throw new RuntimeException("NetworkManger.getDefault().init() Error");
        }

        return application;
    }

    public void init(Application application) {
        this.application = application;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        application.registerReceiver(receiver, filter);
    }

    public void setListener(NetChangeObserver listener) {
        receiver.setListener(listener);
    }

}
