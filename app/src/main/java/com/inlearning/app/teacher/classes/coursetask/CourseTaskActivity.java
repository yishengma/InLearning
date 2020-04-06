package com.inlearning.app.teacher.classes.coursetask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.inlearning.app.R;
import com.inlearning.app.common.util.StatusBar;

public class CourseTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_course_task);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this,true);
    }
}
