package com.inlearning.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.inlearning.app.common.util.DLog;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.DirectorAppRuntime;
import com.inlearning.app.teacher.TeacherRuntime;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;

public class App extends Application {
    private static final String TAG = "App";
    private static Context mContext;
    private static List<WeakReference<Activity>> mReferences;

    @Override
    public void onCreate() {
        super.onCreate();
        //提供以下两种方式进行初始化操作：

        //第一：默认初始化

        Bmob.initialize(this, "4e50fe02409073ad4ad79f2cedcebde0");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
        mContext = this;
        DirectorAppRuntime.setApplicationContext(this);
        TeacherRuntime.setApplicationContext(this);
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                //使用使用IjkPlayer解码
                .setPlayerFactory(IjkPlayerFactory.create())
                //使用ExoPlayer解码
                //.setPlayerFactory(ExoMediaPlayerFactory.create())
                //使用MediaPlayer解码
                //.setPlayerFactory(AndroidMediaPlayerFactory.create())
                .build());

        final QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                DLog.i(TAG, "onViewInitFinished: " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                DLog.i(TAG, "onCoreInitFinished");

            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
            }

            @Override
            public void onInstallFinish(int i) {
            }

            @Override
            public void onDownloadProgress(int i) {
            }
        });

        //60101755 修复
        ThreadMgr.getInstance().postToSubThread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
                QbSdk.initTbsSettings(map);
                QbSdk.initX5Environment(getApplicationContext(), cb);
                //QbSdk.preInit(context, cb);
            }
        });
        mReferences = new ArrayList<>();
    }

    public static Context getGlobalContext() {
        return mContext;
    }


    public static void onActivityCreate(Activity activity) {
        mReferences.add(new WeakReference<>(activity));
    }

    public static void onActivityDestory(Activity activity) {
        for (WeakReference<Activity> ref : mReferences) {
            if (activity == ref.get()) {
                mReferences.remove(ref);
                break;
            }
        }
    }

    public static void finishAllActivity() {
        for (WeakReference<Activity> ref : mReferences) {
            Activity activity = ref.get();
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        mReferences.clear();
    }

}
