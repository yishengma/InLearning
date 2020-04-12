package com.inlearning.app.common.util;

import android.os.Looper;
import android.widget.Toast;

import com.inlearning.app.App;

public class ToastUtil {

    public static void showToast(final String message, final int duration) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            ThreadMgr.getInstance().postToUIThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(App.getGlobalContext(), message, duration).show();
                }
            });
        } else {
            Toast.makeText(App.getGlobalContext(), message, duration).show();
        }
    }
}
