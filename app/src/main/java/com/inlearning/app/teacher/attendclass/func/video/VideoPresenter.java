package com.inlearning.app.teacher.attendclass.func.video;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.inlearning.app.R;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.util.FileUtil;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.common.util.ToastUtil;

import org.apache.xmlbeans.impl.soap.Text;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;


public class VideoPresenter implements VideoUploadMgr.UploadListener {

    private Activity mContext;
    private VideoFunctionView mVideoFunctionView;
    private String mPath;
    private CourseChapter mChapter;
    private int mPos;


    public VideoPresenter(Activity context, View rootView, int pos) {
        mContext = context;
        mPos = pos;
        mVideoFunctionView = rootView.findViewById(R.id.view_video_func);
        mVideoFunctionView.setClickListener(new VideoFunctionView.ClickListener() {
            @Override
            public void onBack() {
                mContext.finish();
            }

            @Override
            public void onSelect() {
                pickVideo(mContext);
            }

            @Override
            public void onUpload() {
                if (TextUtils.isEmpty(mPath)) {
                    return;
                }
                Toast.makeText(mContext, "正在后台上传", Toast.LENGTH_LONG).show();
                ThreadMgr.getInstance().postToSubThread(new Runnable() {
                    @Override
                    public void run() {
                        uploadFile(mChapter, mPath);
                    }
                });
            }

            @Override
            public void finish() {
                mContext.finish();
            }
        });
        VideoUploadMgr.getInstance().addListener(this);
    }

    public void setChapter(CourseChapter chapter) {
        mChapter = chapter;
    }

    public void show() {
        mVideoFunctionView.setVisibility(View.VISIBLE);
        if (mChapter.getVideoFile() != null && !TextUtils.isEmpty(mChapter.getVideoFile().getFileUrl())) {
            mVideoFunctionView.setVideoUrl(mChapter.getVideoFile().getFileUrl());
        }
        mVideoFunctionView.setTitle(mChapter.getChapterName());

    }

    private void pickVideo(Activity context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("video/*");
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("video/*");
        }
        context.startActivityForResult(Intent.createChooser(intent, "选择要导入的视频"), 2);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        Uri uri = data.getData();
        if (uri == null) {
            return;
        }

        mVideoFunctionView.setVideoUrl(uri.toString());
        mPath = FileUtil.getChooseFileResultPath(mContext, uri);
    }

    public void onResume() {
        mVideoFunctionView.onResume();
    }


    public void onPause() {
        mVideoFunctionView.onPause();
    }


    public void onDestroy() {
        VideoUploadMgr.getInstance().removeListener(this);
        mVideoFunctionView.onDestroy();
    }

    private void uploadFile(final CourseChapter chapter, String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        final CourseChapter courseChapter = chapter;
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                VideoUploadMgr.getInstance().uploadDone(mPos, courseChapter, bmobFile);
            }

            @Override
            public void onProgress(Integer value) {
                VideoUploadMgr.getInstance().notifyProgress(chapter, value);
            }
        });
    }

    @Override
    public void onProgress(CourseChapter chapter, int progress) {

    }

    @Override
    public void onUploadDone(int pos, CourseChapter chapter, BmobFile file) {
        Toast.makeText(mContext, chapter.getChapterName() + "上传成功", Toast.LENGTH_SHORT).show();
    }
}
