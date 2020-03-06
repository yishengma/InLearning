package com.inlearning.app.common.util;

import android.content.Context;
import android.util.DisplayMetrics;

import com.inlearning.app.App;

public class PixeUtil {

    public static int dp2px(float dp) {
        return (int) (dp * getDensity(App.getGlobalContext()) + 0.5f);
    }

    public static int dp2sp(float dp) {
        return px2sp(dp2px(dp));
    }

    public static int px2sp(float pxValue) {
        final float fontScale = getScaleDensity(App.getGlobalContext());
        return (int) (pxValue / fontScale + 0.5f);
    }

    private static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getApplicationContext()
                .getResources().getDisplayMetrics();
    }

    private static float density;
    private static float scaledDensity;


    public static float getDensity(Context context) {
        if (density > 0) {
            return density;
        }
        density = getDisplayMetrics(context).density;
        return density;
    }


    public static float getScaleDensity(Context context) {
        if (scaledDensity > 0) {
            return scaledDensity;
        }
        scaledDensity = getDisplayMetrics(context).scaledDensity;
        return scaledDensity;
    }

}
