package com.inlearning.app.director.course;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.util.LoadingDialogUtil;
import com.inlearning.app.common.util.PixeUtil;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.common.widget.EditItemView;

import static android.view.Gravity.CENTER;

public class CourseEditActivity extends AppCompatActivity {
    public static void startActivity(Context context, Course2 course) {
        Intent intent = new Intent(context, CourseEditActivity.class);
        intent.putExtra("course", course);
        context.startActivity(intent);
    }

    protected ViewGroup mRootView;
    protected ImageView mBackView;
    protected TextView mTitleView;
    private EditItemView mNameEditView;
    private EditItemView mTypeEditView;
    private EditItemView mDurationEditView;
    private EditItemView mScoreEditView;
    private TextView mSaveView;
    private Course2 mCourse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        getIntentData();
        initView();
        initSaveButton();
    }


    private void getIntentData() {
        Intent intent = getIntent();
        mCourse = (Course2) intent.getSerializableExtra("course");
    }

    private void initView() {
        mNameEditView = new EditItemView(this);
        mNameEditView.setHint("课程名");
        mNameEditView.setText(mCourse.getName());
        mTypeEditView = new EditItemView(this);
        mTypeEditView.setHint("课程类型");
        mTypeEditView.setText(mCourse.getType());
        mDurationEditView = new EditItemView(this);
        mDurationEditView.setHint("学时");
        mDurationEditView.setText(mCourse.getTime());
        mScoreEditView = new EditItemView(this);
        mScoreEditView.setHint("学分");
        mScoreEditView.setText(mCourse.getScore());
        mRootView = findViewById(R.id.root_view);
        mBackView = findViewById(R.id.imv_edit_back);
        mTitleView = findViewById(R.id.tv_edit_title);
        mTitleView.setText("课程信息");
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRootView.addView(mNameEditView);
        mRootView.addView(mTypeEditView);
        mRootView.addView(mDurationEditView);
        mRootView.addView(mScoreEditView);
    }

    private void initSaveButton() {
        mSaveView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = PixeUtil.dp2px(24);
        params.rightMargin = PixeUtil.dp2px(24);
        params.topMargin = PixeUtil.dp2px(24);
        mSaveView.setBackgroundResource(R.drawable.bg_edit_blue_shape);
        mSaveView.setText("更改并保存");
        mSaveView.setEnabled(true);
        mSaveView.setTextColor(Color.WHITE);
        mSaveView.setGravity(CENTER);
        mSaveView.setTextSize(PixeUtil.dp2sp(16));
        mSaveView.setLayoutParams(params);
        mRootView.addView(mSaveView);
        mSaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCourse.setName(mNameEditView.getContent())
                        .setType(mTypeEditView.getContent())
                        .setTime(mDurationEditView.getContent())
                        .setScore(mScoreEditView.getContent());
                LoadingDialogUtil.showLoadingDialog(CourseEditActivity.this,"正在更新..");
                CourseModel.updateCourse(mCourse, new CourseModel.Callback<Course2>() {
                    @Override
                    public void onResult(boolean suc, Course2 course2) {
                        LoadingDialogUtil.closeDialog();
                        if (suc) {
                            finish();
                            showToast("更新成功");
                        }
                    }
                });
            }
        });
    }

    private void showToast(final String msg) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CourseEditActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
