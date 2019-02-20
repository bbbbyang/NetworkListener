package com.example.bing.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.bing.library.NetworkManager;
import com.example.bing.library.type.NetType;

public class NetworkUtils {

    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) NetworkManager.getDefault().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }

        NetworkInfo[] infos = connectivityManager.getAllNetworkInfo();

        if(infos != null) {
            for(NetworkInfo info : infos) {
                if(info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static NetType getNetType() {
        ConnectivityManager connectivityManager = (ConnectivityManager) NetworkManager.getDefault().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager == null) {
            return NetType.NONE;
        }

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null) {
            return NetType.NONE;
        }
        int type = networkInfo.getType();

        if(type == ConnectivityManager.TYPE_MOBILE) {
            return NetType.MOBILE;
        } else if(type == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }
        return NetType.NONE;
    }
}
