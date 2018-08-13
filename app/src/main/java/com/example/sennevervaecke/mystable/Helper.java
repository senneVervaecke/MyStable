package com.example.sennevervaecke.mystable;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by sennevervaecke on 8/3/2018.
 */

public class Helper {
    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
    }
    public static String toTitleCase(String value){
        value = value.toLowerCase();
        String words[] = value.split(" ");
        String result = "";
        for (String word : words) {
            result += firstToUpper(word) + " ";
        }
        return result.substring(0, result.length() - 1);
    }

    public static String firstToUpper(String value){
        if(value.length() == 0){
            return "";
        }
        if(value.length() == 1){
            return value.toUpperCase();
        }
        return value.substring(0,1).toUpperCase() + value.substring(1, value.length());
    }
}
