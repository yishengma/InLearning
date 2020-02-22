package com.inlearning.app.common.util;

import android.os.Handler;
import android.os.Looper;

public final class ThreadMgr {
    private static Handler UI_THREAD_HANDLER;

    private static volatile ThreadMgr sThreadMgr;

    public static ThreadMgr getInstance() {
        if (null == sThreadMgr) {
            synchronized (ThreadMgr.class) {
                if (null == sThreadMgr) {
                    sThreadMgr = new ThreadMgr();
                }
            }
        }
        return sThreadMgr;
    }

    public void postToUIThread(Runnable runnable) {
        getInstance().getUITreadHandler().post(runnable);
    }

    private Handler getUITreadHandler() {
        if (UI_THREAD_HANDLER == null) {
            synchronized (this) {
                if (UI_THREAD_HANDLER == null) {
                    UI_THREAD_HANDLER = new Handler(Looper.getMainLooper());
                }
            }
        }
        return UI_THREAD_HANDLER;
    }
}
