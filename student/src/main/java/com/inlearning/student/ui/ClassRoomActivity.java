package com.inlearning.student.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoView;
import com.inlearning.student.R;

public class ClassRoomActivity extends AppCompatActivity {
    private VideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_room);
        mVideoView = findViewById(R.id.player);
        mVideoView.setPlayerFactory(IjkPlayerFactory.create());
        mVideoView.setUrl("https://cdn.letv-cdn.com/2018/12/05/JOCeEEUuoteFrjCg/playlist.m3u8"); //设置视频地址
        StandardVideoController controller = new StandardVideoController(this);
        mVideoView.setVideoController(controller); //设置控制器
        mVideoView.start();
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
    public static void startActivity(Context context) {
        Intent intent = new Intent(context,ClassRoomActivity.class);
        context.startActivity(intent);
    }
}
