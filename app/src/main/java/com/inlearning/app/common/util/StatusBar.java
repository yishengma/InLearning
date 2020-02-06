package com.inlearning.app.common.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class StatusBar {

    public static boolean setStatusBarTranslucent(Activity activity) {
        if (activity == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            return true;
        }
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = activity.getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            return true;
//        }
        return false;
    }

    public static void setStatusBarDarkMode(Activity activity, boolean darkMode) {
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            Window window=activity.getWindow();
            View decor=window.getDecorView();
            int ui=decor.getSystemUiVisibility();
            if(!darkMode){
                ui&=~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }else{
                ui|=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decor.setSystemUiVisibility(ui);
            WindowManager.LayoutParams params=window.getAttributes();
            params.flags|=WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
            window.setAttributes(params);
        }
        return;
    }
}
