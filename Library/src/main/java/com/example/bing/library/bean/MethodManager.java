package com.example.bing.library.bean;

import com.example.bing.library.type.NetType;

import java.lang.reflect.Method;

// To store the methods which require to listen network
public class MethodManager {

    private Class<?> type;

    private NetType netType;

    private Method method;

    public MethodManager(Class<?> type, NetType netType, Method method) {
        this.type = type;
        this.netType = netType;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public NetType getNetType() {
        return netType;
    }

    public Method getMethod() {
        return method;
    }
}
