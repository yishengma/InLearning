package com.inlearning.app.director.course;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.inlearning.app.R;
import com.inlearning.app.director.course.edit.IconItemView;
import com.inlearning.app.director.course.edit.NameItemView;
import com.inlearning.app.director.course.edit.ScoreItemView;
import com.inlearning.app.director.course.edit.TimeItemView;
import com.inlearning.app.director.course.edit.TypeItemView;

public class CourseEditActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CourseEditActivity.class);
        context.startActivity(intent);
    }

    private IconItemView mIconItemView;
    private NameItemView mNameItemView;
    private TypeItemView mTypeItemView;
    private TimeItemView mTimeItemView;
    private ScoreItemView mScoreItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        initView();

    }

    private void initView() {
        View view = findViewById(R.id.root_view);
        mIconItemView = new IconItemView(view);
        mNameItemView = new NameItemView(view);
        mTypeItemView = new TypeItemView(view);
        mTimeItemView = new TimeItemView(view);
        mScoreItemView = new ScoreItemView(view);
    }
}
