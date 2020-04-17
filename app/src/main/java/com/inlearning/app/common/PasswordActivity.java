package com.inlearning.app.common;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.design.widget.TextInputEditText;

import com.inlearning.app.App;
import com.inlearning.app.BaseActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.BaseActivity;
import com.inlearning.app.LoginActivity;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.User;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ToastUtil;
import com.inlearning.app.director.DirectorAppRuntime;
import com.inlearning.app.student.StudentRuntime;
import com.inlearning.app.teacher.TeacherRuntime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class PasswordActivity extends BaseActivity implements View.OnClickListener {
    @IntDef({User.Type.STUDENT, User.Type.TEACHER, User.Type.DIRECTOR})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
    public @interface Type {
        int STUDENT = 0;
        int TEACHER = 1;
        int DIRECTOR = 2;
    }

    public static void startPasswordActivity(Context context, @Type int type) {
        Intent intent = new Intent(context, PasswordActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    private ImageView mBackView;
    private int mType;
    private TextInputEditText mOldPasswordEt;
    private TextInputEditText mNewPasswordEt;
    private TextInputEditText mAgainPasswordEt;
    private TextView mUpdateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        setContentView(R.layout.activity_password);
        initView();
        getIntentData();
    }

    private void initView() {
        mBackView = findViewById(R.id.imv_edit_back);
        mBackView.setOnClickListener(this);
        mOldPasswordEt = findViewById(R.id.et_old_password);
        mNewPasswordEt = findViewById(R.id.et_new_password);
        mAgainPasswordEt = findViewById(R.id.et_password_again);
        mUpdateView = findViewById(R.id.tv_update);
        mUpdateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassword(mOldPasswordEt.getText().toString(),
                        mNewPasswordEt.getText().toString(),
                        mAgainPasswordEt.getText().toString());
            }
        });
    }

    private void getIntentData() {
        mType = getIntent().getIntExtra("type", -1);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_edit_back:
                finish();
                break;
        }
    }

    private void checkPassword(String old, String newP, String again) {
        if (TextUtils.isEmpty(old) || TextUtils.isEmpty(newP) || TextUtils.isEmpty(newP)) {
            ToastUtil.showToast("密码不能为空", Toast.LENGTH_SHORT);
            return;
        }
        if (!again.equals(newP)) {
            ToastUtil.showToast("新密码不一致", Toast.LENGTH_SHORT);
            return;
        }
        switch (mType) {
            case Type.DIRECTOR:
                DirectorAppRuntime.getsDirector().setPassword(again);
                DirectorAppRuntime.getsDirector().update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtil.showToast("更新成功", Toast.LENGTH_SHORT);
                            App.finishAllActivity();
                            LoginActivity.startLoginActivity(PasswordActivity.this);
                        }
                    }
                });
                break;
            case Type.TEACHER:
                TeacherRuntime.getCurrentTeacher().setPassword(again);
                TeacherRuntime.getCurrentTeacher().update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtil.showToast("更新成功", Toast.LENGTH_SHORT);
                            App.finishAllActivity();
                            LoginActivity.startLoginActivity(PasswordActivity.this);
                        }
                    }
                });
                break;
            case Type.STUDENT:

                StudentRuntime.getStudent().setPassword(again);

                StudentRuntime.getStudent().update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtil.showToast("更新成功", Toast.LENGTH_SHORT);
                            App.finishAllActivity();
                            LoginActivity.startLoginActivity(PasswordActivity.this);
                        }
                    }
                });
                break;
        }
    }
}
