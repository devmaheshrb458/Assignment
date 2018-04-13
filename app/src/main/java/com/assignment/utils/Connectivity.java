package com.assignment.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Provides utility methods to check : </br>
 * 1. Network connection exist to do http request
 *
 *
 * @author Mahesh R Bhatkande (mahesh.bhatkande@infosys.com)
 * @since 13 Apr, 2018
 */

public class Connectivity {

    /**
     * Check active network connection is to send request
     *
     * @param context {@link Context}
     * @return {@code true} if connection is available else {@code false}
     */
    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo!=null && activeNetworkInfo.isConnected();
        }else {
            return false;
        }
    }

}
