package com.example.bing.library.listener;

import com.example.bing.library.type.NetType;

public interface NetChangeObserver {

    void onConnect(NetType netType);

    void onDisconnect();
}
