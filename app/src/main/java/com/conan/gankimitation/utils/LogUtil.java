package com.conan.gankimitation.utils;

import android.util.Log;

import com.conan.gankimitation.BuildConfig;

/**
 * Description：Log帮助类
 * Created by：JasmineBen
 * Time：2017/10/29
 */
public class LogUtil {

    private static final String GLOBAL_TAG = "GankImitation";

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(GLOBAL_TAG, "[" + tag + "] " + msg);
        }
    }
}
