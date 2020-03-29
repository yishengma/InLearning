package com.inlearning.app.teacher.attendclass.func.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.inlearning.app.R;

public class VideoFunctionView extends LinearLayout implements View.OnClickListener {

    public VideoFunctionView(Context context) {
        this(context, null);
    }

    public VideoFunctionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoFunctionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public interface ClickListener {
        void onBack();

        void onSelect();

        void onUpload();
    }

    private ClickListener mClickListener;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    private ImageView mBackView;
    private TextView mTitleView;
    private TextView mFinishView;
    private VideoView mVideoView;
    private TextView mSelectView;
    private TextView mUploadView;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_video_function, this);
        mBackView = view.findViewById(R.id.imv_bar_back);
        mTitleView = view.findViewById(R.id.tv_edit_title);
        mVideoView = view.findViewById(R.id.view_video);
        mSelectView = view.findViewById(R.id.tv_select);
        mUploadView = view.findViewById(R.id.tv_upload);
        mFinishView = view.findViewById(R.id.tv_bar_finish);
        mFinishView.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        mSelectView.setOnClickListener(this);
        mUploadView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_bar_back:
                mClickListener.onBack();
                break;
            case R.id.tv_select:
                mClickListener.onSelect();
                break;
            case R.id.tv_upload:
                mClickListener.onUpload();
                break;
        }
    }

    public void setTitle(String msg) {
        mTitleView.setText(msg);
    }

    public void setVideoUrl(String url) {
        mVideoView.setUrl(url); //设置视频地址
        StandardVideoController controller = new StandardVideoController(getContext());
        controller.addDefaultControlComponent(mTitleView.getText().toString(), false);
        mVideoView.setVideoController(controller); //设置控制器
        mVideoView.start();
    }


    public void onPause() {
        mVideoView.pause();
    }

    public void onResume() {
        mVideoView.resume();
    }

    public void onDestroy() {
        mVideoView.release();
    }


    public void onBackPressed() {
        if (!mVideoView.onBackPressed()) {

        }
    }

}
