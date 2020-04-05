package com.inlearning.app.student.course.func.video;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.dueeeke.videoplayer.player.VideoView;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.ChapterProgress;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.student.StudentRuntime;
import com.inlearning.app.student.course.func.ChapterProgressModel;

public class StuVideoActivity extends AppCompatActivity {

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
        mStuVideoController.getStuVodControlView().setSeekBarEnable(false);
        mVideoView.setUrl(mChapter.getVideoFile().getUrl());
    }

    private void initStudyProgress() {
        ChapterProgressModel.getVideoStudyProgress(mChapter, StudentRuntime.getStudent(), new ChapterProgressModel.Callback<ChapterProgress>() {
            @Override
            public void onResult(ChapterProgress chapterProgress) {

            }
        });
    }

    private void playVideo(final boolean changePosition) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                mStuVideoController.setCanChangePosition(changePosition);
                mVideoView.start();
                mVideoView.addOnStateChangeListener(new VideoView.OnStateChangeListener() {
                    @Override
                    public void onPlayerStateChanged(int playerState) {

                    }

                    @Override
                    public void onPlayStateChanged(int playState) {

                    }
                });
            }
        });
    }
}
