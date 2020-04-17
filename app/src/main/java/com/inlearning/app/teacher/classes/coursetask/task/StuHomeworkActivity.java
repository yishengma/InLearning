package com.inlearning.app.teacher.classes.coursetask.task;

import android.content.Context;
import android.content.Intent;
import com.inlearning.app.BaseActivity;
import android.os.Bundle;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.teacher.classes.coursetask.CourseTaskActivity;

import java.util.List;

public class StuHomeworkActivity extends BaseActivity {

    public static void startActivity(Context context, CourseChapter chapter, Student student) {
        Intent intent = new Intent(context, StuHomeworkActivity.class);
        intent.putExtra("coursechapter", chapter);
        intent.putExtra("student", student);
        context.startActivity(intent);
    }

    private HomeworkPresenter mHomeworkPresenter;
    private CourseChapter mChapter;
    private Student mStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_homework);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        getIntentData();
        initPresenter();
    }

    private void getIntentData() {
        mChapter = (CourseChapter) getIntent().getSerializableExtra("coursechapter");
        mStudent = (Student) getIntent().getSerializableExtra("student");
    }

    private void initPresenter() {
        mHomeworkPresenter = new HomeworkPresenter(this, mChapter, mStudent);
        mHomeworkPresenter.show();
    }
}
