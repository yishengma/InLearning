package com.inlearning.app.student.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.widget.PersonInfoView;

public class PersonActivity extends AppCompatActivity {

    public static void startActivity(Context context, Student student) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra("student", student);
        context.startActivity(intent);
    }

    private LinearLayout mRootView;
    private PersonInfoView mImageView;
    private PersonInfoView mNumberView;
    private PersonInfoView mClassInfoView;
    private PersonInfoView mNameView;
    private Student mStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        getIntentData();
        initView();
    }

    private void getIntentData() {
        mStudent = (Student) getIntent().getSerializableExtra("student");
    }

    private void initView() {

        mRootView = findViewById(R.id.root_view);
        mImageView = new PersonInfoView(this);
        mImageView.setTitleText("头像");
        mImageView.setPersonImageView(mStudent.getProfilePhotoUrl());
        mNumberView = new PersonInfoView(this);
        mNumberView.setTitleText("学号");
        mNumberView.setPersonContentView(mStudent.getAccount());
        mClassInfoView = new PersonInfoView(this);
        mClassInfoView.setTitleText("班级");
        mClassInfoView.setPersonContentView(mStudent.getClassInfo().getName());
        mNameView = new PersonInfoView(this);
        mNameView.setTitleText("姓名");
        mNameView.setPersonContentView(mStudent.getName());
        mRootView.addView(mImageView);
        addDivideView();
        mRootView.addView(mNumberView);
        addDivideView();
        mRootView.addView(mClassInfoView);
        addDivideView();
        mRootView.addView(mNameView);
        addDivideView();
    }

    public void addDivideView() {
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(R.color.split_line));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);

        mRootView.addView(view, params);
    }
}
