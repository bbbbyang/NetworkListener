package com.example.bing.networklistener;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bing.library.NetworkManager;
import com.example.bing.library.annotation.Network;
import com.example.bing.library.listener.NetChangeObserver;
import com.example.bing.library.type.NetType;
import com.example.bing.library.utils.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkManager.getDefault().registerObserver(this);
    }

    @Network(netType = NetType.AUTO)
    public void network(NetType netType) {
        switch (netType) {
            case WIFI:
                Log.e(Constants.LOG_TAG, "WIFI");
                break;
            case MOBILE:
                Log.e(Constants.LOG_TAG, "MOBILE");
                break;
            case NONE:
                Log.e(Constants.LOG_TAG, "Network Disconnected");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getDefault().unRegisterObserver(this);
        NetworkManager.getDefault().unRegisterAllObserver();
    }
}
