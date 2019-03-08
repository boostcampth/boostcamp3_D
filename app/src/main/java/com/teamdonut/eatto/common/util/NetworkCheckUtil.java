package com.teamdonut.eatto.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class NetworkCheckUtil {

    public static boolean networkCheck(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                //WIFI에 연결됨
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                //LTE(이동통신망)에 연결됨
            }
            return true;
        } else {
            // 연결되지않음
            return false;
        }
    }

}
