package com.example.mylibrary;

import android.content.Context;
import android.util.Log;

public class LogUtils {
    private static final String TAG = "LogUtils";
    public static void Log(String tag,String log){
        Log.d(tag,log);
    }
}
