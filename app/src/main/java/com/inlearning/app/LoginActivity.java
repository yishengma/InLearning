package com.inlearning.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import com.inlearning.app.BaseActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.inlearning.app.common.bean.Director;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.bean.User;
import com.inlearning.app.common.model.UserModel;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ToastUtil;
import com.inlearning.app.director.DirectorAppRuntime;
import com.inlearning.app.director.DirectorHomeActivity;
import com.inlearning.app.student.StudentHomeActivity;
import com.inlearning.app.student.StudentRuntime;
import com.inlearning.app.teacher.TeacherHomeActivity;
import com.inlearning.app.teacher.TeacherRuntime;
import com.inlearning.app.teacher.classes.coursetask.task.StuHomeworkActivity;

import org.apache.commons.logging.Log;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private TextInputEditText mAccount;
    private TextInputEditText mPassword;
    private Button mLogin;
    private RadioButton mStudentView;
    private RadioButton mTeacherView;
    private RadioButton mDirectorView;

    public static void startLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //检查权限 是否授权
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //请求授权
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
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
        if (mStudentView.isChecked()) {
            UserModel.onLogin(User.Type.STUDENT, account, password, new UserModel.Callback<Student>() {
                @Override
                public void onResult(Student student) {
                    if (!isLoginSuccess(student)) {
                        return;
                    }
                    StudentRuntime.setStudent(student);
                    StudentHomeActivity.startHomePageActivity(LoginActivity.this);
                    finish();
                }
            });
        } else if (mTeacherView.isChecked()) {

            UserModel.onLogin(User.Type.TEACHER, account, password, new UserModel.Callback<Teacher>() {
                @Override
                public void onResult(Teacher teacher) {
                    if (!isLoginSuccess(teacher)) {
                        return;
                    }
                    TeacherRuntime.setCurrentTeacher(teacher);
                    TeacherHomeActivity.startHomePageActivity(LoginActivity.this);
                    finish();
                }
            });
        } else if (mDirectorView.isChecked()) {
            UserModel.onLogin(User.Type.DIRECTOR, account, password, new UserModel.Callback<Director>() {
                @Override
                public void onResult(Director director) {
                    if (!isLoginSuccess(director)) {
                        return;
                    }
                    DirectorAppRuntime.setsDirector(director);
                    DirectorHomeActivity.startHomePageActivity(LoginActivity.this);
                    finish();
                }
            });
        }

    }

    public boolean isLoginSuccess(Object o) {
        if (o == null) {
            ToastUtil.showToast("登录失败，该账户不存在！", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

}
//https://www.iconfont.cn/collections/detail?spm=a313x.7781069.1998910419.d9df05512&cid=19238