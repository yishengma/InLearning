package com.inlearning.app.student.course.func.video;

import android.content.Context;
import android.content.Intent;
import com.inlearning.app.BaseActivity;
import android.os.Bundle;
import android.util.Log;

import com.dueeeke.videoplayer.player.ProgressManager;
import com.dueeeke.videoplayer.player.VideoView;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.ChapterProgress;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.student.StudentRuntime;
import com.inlearning.app.student.course.func.ChapterProgressModel;

public class StuVideoActivity extends BaseActivity {

    public static void startActivity(Context context, CourseChapter chapter) {
        Intent intent = new Intent(context, StuVideoActivity.class);
        intent.putExtra("chapter", chapter);
        context.startActivity(intent);
    }

    private VideoView mVideoView;
    private CourseChapter mChapter;
    private ChapterProgress mChapterProgress;
    private StuVideoController mStuVideoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_video);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        getIntentData();
        initVideoView();
        initStudyProgress();
    }

    private void getIntentData() {
        mChapter = (CourseChapter) getIntent().getSerializableExtra("chapter");
    }

    private void initVideoView() {
        mVideoView = findViewById(R.id.view_video);
        mStuVideoController = new StuVideoController(StuVideoActivity.this);
        mStuVideoController.addDefaultControlComponent(mChapter.getChapterName(), false);
        mVideoView.setVideoController(mStuVideoController); //设置控制器
        mVideoView.startFullScreen();
        mStuVideoController.setCanChangePosition(false);
        mStuVideoController.getStuVodControlView().setSeekBarEnable(false);
        mVideoView.setUrl(mChapter.getVideoFile().getUrl());
        mStuVideoController.getStuVodControlView().setListener(new StuVodControlView.ProgressListener() {
            @Override
            public void onProgress(int progress, int duration) {
                ChapterProgressModel.updateStudyProgress(mChapterProgress, progress, duration);
            }
        });
    }

    private void initStudyProgress() {
        ChapterProgressModel.getVideoStudyProgress(mChapter, StudentRuntime.getStudent(), new ChapterProgressModel.Callback<ChapterProgress>() {
            @Override
            public void onResult(ChapterProgress chapterProgress) {
                mChapterProgress = chapterProgress;
                playVideo(chapterProgress.isDone());
            }
        });
    }

    private void playVideo(final boolean changePosition) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                mStuVideoController.setCanChangePosition(changePosition);
                mStuVideoController.getStuVodControlView().setSeekBarEnable(changePosition);
                if (!changePosition) {
                    mVideoView.seekTo(mChapterProgress.getStudyDuration());
                }
                mVideoView.start();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.release();
    }


    @Override
    public void onBackPressed() {
        if (!mVideoView.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
