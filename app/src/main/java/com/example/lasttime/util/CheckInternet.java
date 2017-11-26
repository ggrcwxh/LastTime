package com.example.lasttime.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by 67014 on 2017/11/26.
 */

public class CheckInternet {
    /**
     * 判断移动网络是否连接成功
     *
     * @param context
     * @return
     */
    public static final boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] networks = connectivityManager.getAllNetworks();
        NetworkInfo networkInfo;
        for (Network mNetwork : networks) {
            networkInfo = connectivityManager.getNetworkInfo(mNetwork);
            if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                return true;
            }
        }
        return false;
    }
}
