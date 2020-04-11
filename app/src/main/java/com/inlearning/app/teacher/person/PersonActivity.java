package com.inlearning.app.teacher.person;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.Director;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.util.PixeUtil;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.widget.PersonInfoView;
import com.inlearning.app.teacher.attendclass.CourseChapterActivity;

public class PersonActivity extends AppCompatActivity {

    public static void startActivity(Context context, Teacher teacher) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra("teacher", teacher);
        context.startActivity(intent);
    }

    private LinearLayout mRootView;
    private PersonInfoView mImageView;
    private PersonInfoView mJobNumberView;
    private PersonInfoView mNameView;
    private PersonInfoView mIdentityView;
    private Teacher mTeacher;

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
        mTeacher = (Teacher) getIntent().getSerializableExtra("teacher");
    }

    private void initView() {

        mRootView = findViewById(R.id.root_view);
        mImageView = new PersonInfoView(this);
        mImageView.setTitleText("头像");
        mImageView.setPersonImageView(mTeacher.getProfilePhotoUrl());
        mJobNumberView = new PersonInfoView(this);
        mJobNumberView.setTitleText("工号");
        mJobNumberView.setPersonContentView(mTeacher.getJobNumber());
        mNameView = new PersonInfoView(this);
        mNameView.setTitleText("姓名");
        mNameView.setPersonContentView(mTeacher.getName());
        mIdentityView = new PersonInfoView(this);
        mIdentityView.setTitleText("职称");
        mIdentityView.setPersonContentView(mTeacher.getTitle());
        mRootView.addView(mImageView);
        addDivideView();
        mRootView.addView(mJobNumberView);
        addDivideView();
        mRootView.addView(mNameView);
        addDivideView();
        mRootView.addView(mIdentityView);
    }

    public void addDivideView() {
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(R.color.split_line));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);

        mRootView.addView(view, params);
    }
}
