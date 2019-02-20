package com.example.bing.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.bing.library.listener.NetChangeObserver;
import com.example.bing.library.type.NetType;
import com.example.bing.library.utils.Constants;
import com.example.bing.library.utils.NetworkUtils;

public class NetStateReceiver extends BroadcastReceiver {

    private NetType netType;
    private NetChangeObserver listener;

    public NetStateReceiver() {
        this.netType = NetType.NONE;
    }

    public void setListener(NetChangeObserver listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent == null || intent.getAction() == null) {
            Log.e(Constants.LOG_TAG, "Error");
            return;
        }

        if(intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)) {
            Log.e(Constants.LOG_TAG, "Network changed");
            netType = NetworkUtils.getNetType();
            if(NetworkUtils.isNetworkAvailable()) {
                Log.e(Constants.LOG_TAG, "Network connected");
                listener.onConnect(netType);
            } else {
                Log.e(Constants.LOG_TAG, "Network disconnected");
                listener.onDisconnect();
            }
        }
    }
}
