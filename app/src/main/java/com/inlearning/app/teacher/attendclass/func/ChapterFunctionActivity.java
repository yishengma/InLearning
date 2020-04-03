package com.inlearning.app.teacher.attendclass.func;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.teacher.attendclass.func.homework.HomeworkPresenter;
import com.inlearning.app.teacher.attendclass.func.material.MaterialPresenter;
import com.inlearning.app.teacher.attendclass.func.video.VideoPresenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ChapterFunctionActivity extends AppCompatActivity {

    @IntDef({FLAG.VIDEO_FUNCTION, FLAG.MATERIAL_FUNCTION, FLAG.HOMEWORK_FUNCTION})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
    public @interface FLAG {
        int VIDEO_FUNCTION = 0;
        int MATERIAL_FUNCTION = 1;
        //        int EXERCISE_FUNCTION = 2;
        int HOMEWORK_FUNCTION = 3;
    }

    public static void startActivity(Context context, CourseChapter chapter, @FLAG int flag) {
        Intent intent = new Intent(context, ChapterFunctionActivity.class);
        intent.putExtra("chapter", chapter);
        intent.putExtra("flag", flag);
        context.startActivity(intent);
    }

    private VideoPresenter mVideoPresenter;
    private MaterialPresenter mMaterialPresenter;
    private HomeworkPresenter mHomeworkPresenter;
    private View mRootView;
    private int mFunctionFlag = -1;
    private CourseChapter mChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_function);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        getIntentData();
        initPresenter();
    }

    private void getIntentData() {
        mFunctionFlag = getIntent().getIntExtra("flag", -1);
        mChapter = (CourseChapter) getIntent().getSerializableExtra("chapter");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoPresenter.onDestroy();
        mMaterialPresenter.onDestory();
    }

    private void initPresenter() {
        mRootView = findViewById(R.id.root_view);
        mVideoPresenter = new VideoPresenter(this, mRootView);
        mVideoPresenter.setChapter(mChapter);
        mMaterialPresenter = new MaterialPresenter(this, mChapter);
        mHomeworkPresenter = new HomeworkPresenter(this, mRootView, mChapter);
        switch (mFunctionFlag) {
            case FLAG.VIDEO_FUNCTION:
                mVideoPresenter.show();
                break;
            case FLAG.MATERIAL_FUNCTION:
                mMaterialPresenter.showMaterialView();
                break;
            case FLAG.HOMEWORK_FUNCTION:
                mHomeworkPresenter.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (mFunctionFlag) {
            case FLAG.VIDEO_FUNCTION:
                mVideoPresenter.onActivityResult(requestCode, resultCode, data);
                break;
            case FLAG.MATERIAL_FUNCTION:
                mMaterialPresenter.onActivityResult(requestCode, resultCode, data);
                break;
            case FLAG.HOMEWORK_FUNCTION:
                mHomeworkPresenter.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (mFunctionFlag) {
            case FLAG.HOMEWORK_FUNCTION:
                mHomeworkPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
}
