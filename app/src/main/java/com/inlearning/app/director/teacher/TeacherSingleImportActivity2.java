package com.inlearning.app.director.teacher;

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
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.util.PixeUtil;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.common.widget.EditItemView;
import com.inlearning.app.director.BaseSingleImportActivity;

import static android.view.Gravity.CENTER;

public class TeacherSingleImportActivity2 extends BaseSingleImportActivity implements TextWatcher {
    public static void startSingleImportActivity(Context context) {
        Intent intent = new Intent(context, TeacherSingleImportActivity2.class);
        context.startActivity(intent);
    }

    private EditItemView mNameEditView;
    private EditItemView mJonNumberEditView;
    private EditItemView mTitleEditView;
    private TextView mSaveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initSaveButton();
    }

    private void initView() {
        mTitleView.setText("新增教师");
        mJonNumberEditView = new EditItemView(this);
        mJonNumberEditView.setTextWatcher(this);
        mJonNumberEditView.setHint("工号");
        mNameEditView = new EditItemView(this);
        mNameEditView.setTextWatcher(this);
        mNameEditView.setHint("姓名");
        mTitleEditView = new EditItemView(this);
        mTitleEditView.setTextWatcher(this);
        mTitleEditView.setHint("头衔");
        mRootView.addView(mJonNumberEditView);
        mRootView.addView(mNameEditView);
        mRootView.addView(mTitleEditView);
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
                addTeacher();
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
                && !TextUtils.isEmpty(mJonNumberEditView.getContent())
                && !TextUtils.isEmpty(mTitleEditView.getContent())) {
            mSaveView.setTextColor(Color.WHITE);
            mSaveView.setBackgroundResource(R.drawable.bg_edit_blue_shape);
            mSaveView.setEnabled(true);
        } else {
            mSaveView.setBackgroundResource(R.drawable.bg_edit_gray_shape);
            mSaveView.setEnabled(false);
            mSaveView.setTextColor(Color.parseColor("#61000000"));
        }
    }

    private void addTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName(mNameEditView.getContent())
                .setJobNumber(mJonNumberEditView.getContent())
                .setTitle(mTitleEditView.getContent());
        TeacherModel.addTeacher(teacher, new TeacherModel.Callback<Teacher>() {
            @Override
            public void onResult(boolean suc, Teacher teacher) {
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
                Toast.makeText(TeacherSingleImportActivity2.this, "添加成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
