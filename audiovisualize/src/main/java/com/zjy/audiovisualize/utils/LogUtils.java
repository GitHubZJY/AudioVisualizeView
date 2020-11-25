package com.zjy.audiovisualize.utils;

import android.util.Log;

/**
 * Date: 2020/11/25
 * Author: Yang
 * Describe:
 */
public class LogUtils {

    private static final String TAG = "AudioVisualize";

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }
}
