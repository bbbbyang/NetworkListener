package com.example.bing.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.bing.library.annotation.Network;
import com.example.bing.library.bean.MethodManager;
import com.example.bing.library.type.NetType;
import com.example.bing.library.utils.Constants;
import com.example.bing.library.utils.NetworkUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NetStateReceiver extends BroadcastReceiver {

    private NetType netType;
    private Map<Object, List<MethodManager>> networkList;

    public NetStateReceiver() {
        this.netType = NetType.NONE;
        networkList = new HashMap<>();
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
            } else {
                Log.e(Constants.LOG_TAG, "Network disconnected");
            }
        }
        
        post(netType);
    }

    private void post(NetType netType) {
        Set<Object> set = networkList.keySet();
        for ( final Object getter : set ) {
            List<MethodManager> methodeList = networkList.get(getter);
            if( methodeList != null ) {
                for( final MethodManager method : methodeList ) {
                    if( method.getType().isAssignableFrom(netType.getClass() ) ) {
                        switch ( method.getNetType() ) {
                            case AUTO:
                                invoke( method, getter, netType );
                                break;
                            case WIFI:
                                if( netType == NetType.WIFI || netType == NetType.NONE ) {
                                    invoke( method, getter, netType );
                                }
                                break;
                            case MOBILE:
                                if( netType == NetType.MOBILE || netType == NetType.NONE ) {
                                    invoke( method, getter, netType );
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    private void invoke(MethodManager method, Object getter, NetType netType) {
        Method execute = method.getMethod();
        try {
            execute.invoke(getter, netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void registerObserver(Object register) {
        List<MethodManager> methodList = networkList.get(register);
        if( methodList == null ) {
            methodList = findAnnotationMethod(register);
            networkList.put(register, methodList);
            Log.e(Constants.LOG_TAG, register.getClass().getName() + " Registered");
        }
    }

    private List<MethodManager> findAnnotationMethod( Object register ) {
        List<MethodManager> methodList = new ArrayList<>();

        Class<?> clazz = register.getClass();
        Method[] methods = clazz.getMethods();
        for( Method method : methods ) {
            Network network = method.getAnnotation(Network.class);
            if( network == null ) {
                continue;
            }

            Type returnType = method.getGenericReturnType();
            if( !"void".equals( returnType.toString() ) ) {
                throw new RuntimeException( method.getName() + " return type should be void" );
            }

            Class<?>[] parameterTypes = method.getParameterTypes();
            if( parameterTypes.length != 1 ) {
                throw new RuntimeException( method.getName() + " should have only one parameter" );
            }

            MethodManager methodManager = new MethodManager( parameterTypes[0], network.netType(), method );
            methodList.add(methodManager);
        }

        return methodList;
    }

    public void unRegisterObserver(Object register) {
        if( !networkList.isEmpty() ) {
            networkList.remove( register );
        }
        Log.e(Constants.LOG_TAG, register.getClass().getName() + " Unregistered");
    }

    public void unRegisterAllObserver() {
        if( !networkList.isEmpty() ) {
            networkList.clear();
        }
        NetworkManager.getDefault().getApplication().unregisterReceiver(this);
        networkList = null;
        Log.e(Constants.LOG_TAG, "All activities unregistered");
    }
}
