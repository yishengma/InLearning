package com.inlearning.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.inlearning.app.common.bean.User;
import com.inlearning.app.common.model.UserModel;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.director.ui.DirectorHomeActivity;
import com.inlearning.app.student.ui.StudentHomeActivity;
import com.inlearning.app.teacher.ui.TeacherHomeActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText mAccount;
    private EditText mPassword;
    private Button mLogin;
    private RadioButton mStudentView;
    private RadioButton mTeacherView;
    private RadioButton mDirectorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //检查权限 是否授权
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //请求授权
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            } else {

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void initView() {
        mAccount = findViewById(R.id.et_account);
        mPassword = findViewById(R.id.et_password);
        mAccount.setText("123456");
        mPassword.setText("123456");
        mLogin = findViewById(R.id.btn_login);
        mStudentView = findViewById(R.id.rbtn_student);
        mTeacherView = findViewById(R.id.rbtn_teacher);
        mDirectorView = findViewById(R.id.rbtn_director);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(getAccount())) {
                    Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                }

                if (!TextUtils.isEmpty(getAccount()) && TextUtils.isEmpty(getPassword())) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }

                if (!TextUtils.isEmpty(getAccount()) && !TextUtils.isEmpty(getPassword())) {
                    login(getAccount(), getPassword());
                }
            }
        });


    }

    private void showFail(String reason) {
        Toast.makeText(LoginActivity.this, reason, Toast.LENGTH_SHORT).show();
    }

    private String getPassword() {
        return mPassword.getText().toString();
    }

    private String getAccount() {
        return mAccount.getText().toString();
    }

    private void login(String account, String password) {
        @User.Type int type = User.Type.STUDENT;
        if (mStudentView.isChecked()) {
            type = User.Type.STUDENT;
        } else if (mTeacherView.isChecked()) {
            type = User.Type.TEACHER;
        } else if (mDirectorView.isChecked()) {
            type = User.Type.DIRECTOR;
        }
        UserModel.onLogin(type, account, password, new UserModel.Callback() {
            @Override
            public void onResult(User user) {
                if (user == null) {
                    return;
                }
                switch (user.getType()) {
                    case User.Type.STUDENT:
                        StudentHomeActivity.startHomePageActivity(LoginActivity.this);
                        break;
                    case User.Type.TEACHER:
                        TeacherHomeActivity.startHomePageActivity(LoginActivity.this);
                        break;
                    case User.Type.DIRECTOR:
                        DirectorHomeActivity.startHomePageActivity(LoginActivity.this);
                        break;
                }
            }
        });
    }

}
