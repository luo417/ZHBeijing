package com.example.hailin.zhbeijing.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hailin on 16/10/10.
 */
public class PreferenceUtil {
    private static final String PREF_NAME = "config";
    public static boolean getBoolean(Context context, String key, boolean defaultValue){
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }
    public static void setBoolean(Context context, String key, boolean value){
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }
}
