package com.inlearning.app.common.util;

import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ThreadMgr {
    private static Handler UI_THREAD_HANDLER;
    private ExecutorService mExecutorService;
    private static volatile ThreadMgr sThreadMgr;

    private ThreadMgr() {
        mExecutorService = Executors.newSingleThreadExecutor();
    }

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

    public void postToUIThread(Runnable runnable, long ms) {
        getInstance().getUITreadHandler().postDelayed(runnable, ms);
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


    public void postToSubThread(Runnable runnable) {
        mExecutorService.execute(runnable);
    }
}
