package com.inlearning.app.director.course;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.util.LoadingDialogUtil;
import com.inlearning.app.common.util.PixeUtil;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.common.widget.EditItemView;
import com.inlearning.app.director.BaseSingleImportActivity;

import static android.view.Gravity.CENTER;

public class CourseSingleImportActivity2 extends BaseSingleImportActivity implements TextWatcher {
    public static void startSingleImportActivity(Context context) {
        Intent intent = new Intent(context, CourseSingleImportActivity2.class);
        context.startActivity(intent);
    }

    private EditItemView mNameEditView;
    private EditItemView mTypeEditView;
    private EditItemView mDurationEditView;
    private EditItemView mScoreEditView;
    private TextView mSaveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initSaveButton();
    }

    private void initView() {
        mTitleView.setText("新增课程");
        mNameEditView = new EditItemView(this);
        mNameEditView.setTextWatcher(this);
        mNameEditView.setHint("课程名");
        mTypeEditView = new EditItemView(this);
        mTypeEditView.setTextWatcher(this);
        mTypeEditView.setHint("类型");
        mDurationEditView = new EditItemView(this);
        mDurationEditView.setTextWatcher(this);
        mDurationEditView.setHint("学时");
        mScoreEditView = new EditItemView(this);
        mScoreEditView.setTextWatcher(this);
        mScoreEditView.setHint("学分");
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
        mSaveView.setBackgroundResource(R.drawable.bg_edit_gray_shape);
        mSaveView.setText("保存");
        mSaveView.setEnabled(false);
        mSaveView.setTextColor(Color.parseColor("#61000000"));
        mSaveView.setGravity(CENTER);
        mSaveView.setTextSize(PixeUtil.dp2sp(16));
        mSaveView.setLayoutParams(params);
        mRootView.addView(mSaveView);
        mSaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCourse();
            }
        });
    }

    @Override
    protected void doBack() {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        changeSaveViewStatus();
    }

    private void changeSaveViewStatus() {
        if (!TextUtils.isEmpty(mNameEditView.getContent())
                && !TextUtils.isEmpty(mTypeEditView.getContent())
                && !TextUtils.isEmpty(mScoreEditView.getContent())
                && !TextUtils.isEmpty(mDurationEditView.getContent())) {
            mSaveView.setTextColor(Color.WHITE);
            mSaveView.setBackgroundResource(R.drawable.bg_edit_blue_shape);
            mSaveView.setEnabled(true);
        } else{
            mSaveView.setBackgroundResource(R.drawable.bg_edit_gray_shape);
            mSaveView.setEnabled(false);
            mSaveView.setTextColor(Color.parseColor("#61000000"));
        }
    }

    private void addCourse() {
        Course2 course = new Course2();
        course.setName(mNameEditView.getContent())
                .setType(mTypeEditView.getContent())
                .setTime(mDurationEditView.getContent())
                .setScore(mScoreEditView.getContent());
        LoadingDialogUtil.showLoadingDialog(CourseSingleImportActivity2.this,"正在保存..");
        CourseModel.saveCourseInfo(course, new CourseModel.Callback<Course2>() {
            @Override
            public void onResult(boolean suc, Course2 speciality) {
                LoadingDialogUtil.closeDialog();
                if (suc) {
                    showToast();
                    finish();
                }
            }
        });
    }

    private void showToast() {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CourseSingleImportActivity2.this, "添加成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
