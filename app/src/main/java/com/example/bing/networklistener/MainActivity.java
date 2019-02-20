package com.example.bing.networklistener;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bing.library.NetworkManager;
import com.example.bing.library.listener.NetChangeObserver;
import com.example.bing.library.type.NetType;

public class MainActivity extends AppCompatActivity implements NetChangeObserver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkManager.getDefault().init(getApplication());
        NetworkManager.getDefault().setListener(this);
    }

    @Override
    public void onConnect(NetType netType) {
        Toast.makeText(getApplication(),"Connected with " + netType.name(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDisconnect() {
        Toast.makeText(getApplication(), "Lost internet", Toast.LENGTH_LONG).show();
    }
}
