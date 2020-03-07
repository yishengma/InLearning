package com.inlearning.app.director.speciality.classinfo;

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
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.util.PixeUtil;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.common.widget.EditItemView;
import com.inlearning.app.director.BaseSingleImportActivity;

import static android.view.Gravity.CENTER;

public class StudentSingleImportActivity extends BaseSingleImportActivity implements TextWatcher {
    public static void startEditActivity(Context context, ClassInfo classInfo) {
        Intent intent = new Intent(context, StudentSingleImportActivity.class);
        intent.putExtra("classInfo", classInfo);
        context.startActivity(intent);
    }

    private EditItemView mNameEditView;
    private EditItemView mNumberEditView;
    private EditItemView mSexEditView;
    private TextView mSaveView;
    private ClassInfo mClassInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initSaveButton();
        getIntentData();
    }

    private void initView() {
        mTitleView.setText("新增学生");
        mNameEditView = new EditItemView(this);
        mNameEditView.setHint("学生姓名");
        mNumberEditView = new EditItemView(this);
        mNumberEditView.setHint("学号");
        mSexEditView = new EditItemView(this);
        mSexEditView.setHint("性别");
        mNameEditView.setTextWatcher(this);
        mNumberEditView.setTextWatcher(this);
        mSexEditView.setTextWatcher(this);
        mRootView.addView(mNumberEditView);
        mRootView.addView(mNameEditView);
        mRootView.addView(mSexEditView);
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
                addStudent();
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mClassInfo = (ClassInfo) intent.getSerializableExtra("classInfo");
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
        if (!TextUtils.isEmpty(mSexEditView.getContent())
                && !TextUtils.isEmpty(mNumberEditView.getContent())
                && !TextUtils.isEmpty(mNameEditView.getContent())) {
            mSaveView.setTextColor(Color.WHITE);
            mSaveView.setBackgroundResource(R.drawable.bg_edit_blue_shape);
            mSaveView.setEnabled(true);
        } else {
            mSaveView.setBackgroundResource(R.drawable.bg_edit_gray_shape);
            mSaveView.setEnabled(false);
            mSaveView.setTextColor(Color.parseColor("#61000000"));
        }
    }

    private void addStudent() {
        Student student = new Student();
        student.setSex(mSexEditView.getContent())
                .setClassInfo(mClassInfo)
                .setName(mNameEditView.getContent())
                .setAccount(mNumberEditView.getContent());
        ClassInfoModel.saveStudent(student, new ClassInfoModel.Callback<Student>() {
            @Override
            public void onResult(boolean suc, Student student) {
                showToast();
                finish();
            }
        });

    }

    private void showToast() {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(StudentSingleImportActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
