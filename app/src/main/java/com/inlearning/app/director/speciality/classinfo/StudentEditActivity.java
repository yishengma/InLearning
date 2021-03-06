package com.inlearning.app.director.speciality.classinfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.inlearning.app.BaseActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.util.PixeUtil;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.common.widget.EditItemView;

import static android.view.Gravity.CENTER;

public class StudentEditActivity extends BaseActivity implements TextWatcher {
    public static void startActivity(Context context, Student student) {
        Intent intent = new Intent(context, StudentEditActivity.class);
        intent.putExtra("student", student);
        context.startActivity(intent);
    }

    private Student mStudent;
    private static final String TAG = "StudentEditActivity";

    protected ViewGroup mRootView;
    protected ImageView mBackView;
    protected TextView mTitleView;
    private EditItemView mNameEditView;
    private EditItemView mNumberEditView;
    private EditItemView mSexEditView;
    private TextView mSaveView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_edit);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        getIntentData();
        initView();
        initSaveButton();
    }


    private void getIntentData() {
        Intent intent = getIntent();
        mStudent = (Student) intent.getSerializableExtra("student");
    }

    private void initView() {
        mNameEditView = new EditItemView(this);
        mNameEditView.setHint("姓名");
        mNameEditView.setText(mStudent.getName());
        mNumberEditView = new EditItemView(this);
        mNumberEditView.setHint("学号");
        mNumberEditView.setText(mStudent.getAccount());
        mNumberEditView.setEnableEdit(false);
        mSexEditView = new EditItemView(this);
        mSexEditView.setHint("性别");
        mSexEditView.setText(mStudent.getSex());
        mRootView = findViewById(R.id.root_view);
        mBackView = findViewById(R.id.imv_edit_back);
        mTitleView = findViewById(R.id.tv_edit_title);
        mTitleView.setText("学生信息");
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRootView.addView(mNumberEditView);
        mRootView.addView(mNameEditView);
        mRootView.addView(mSexEditView);
        mNameEditView.setTextWatcher(this);
        mSexEditView.setTextWatcher(this);
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
                mStudent.setSex(mSexEditView.getContent())
                        .setName(mNameEditView.getContent())
                        .setAccount(mNameEditView.getContent());
                ClassInfoModel.updateStudent(mStudent, new ClassInfoModel.Callback<Student>() {
                    @Override
                    public void onResult(boolean suc, Student student) {
                        if (suc) {
                            showToast("更新成功");
                            finish();

                        }
                    }
                });

            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
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

    private void showToast(final String msg) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(StudentEditActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
