package com.inlearning.app.common.util;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

public class DLog {

    private static final String APP_NAME = "InLearning";

    public static void i(String tag, String msg) {
        Log.i(preTag(tag), msg);
    }

    public static void d(String tag, String msg) {
        Log.d(preTag(tag), msg);
    }

    public static void v(String tag, String msg) {
        Log.v(preTag(tag), msg);
    }

    public static void w(String tag, String msg) {
        Log.w(preTag(tag), msg);
    }

    public static void e(String tag, String msg) {
        Log.e(preTag(tag), msg);
    }

    @NotNull
    private static String preTag(String tag) {
        return String.format("%s_%s", APP_NAME, tag);
    }
}
