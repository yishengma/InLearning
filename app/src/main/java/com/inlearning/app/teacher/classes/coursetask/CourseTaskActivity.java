package com.inlearning.app.teacher.classes.coursetask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.util.StatusBar;

public class CourseTaskActivity extends AppCompatActivity implements View.OnClickListener {

    public static void startActivity(Context context, ClassSchedule schedule, CourseChapter chapter) {
        Intent intent = new Intent(context, CourseTaskActivity.class);
        intent.putExtra("classschedule", schedule);
        intent.putExtra("coursechapter", chapter);
        context.startActivity(intent);
    }

    private ClassSchedule mSchedule;
    private CourseChapter mChapter;
    private ImageView mBackView;
    private TextView mTitleView;
    private LinearLayout mLayout;
    private TextView mTimeView;
    private TextView mHomeworkView;
    private TextView mClassView;
    private TextView mCourseView;
    private TextView mChapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_course_task);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this,true);
        getIntentData();
        initView();
    }

    private void getIntentData() {
        mSchedule = (ClassSchedule) getIntent().getSerializableExtra("classschedule");
        mChapter = (CourseChapter) getIntent().getSerializableExtra("coursechapter");
    }

    @SuppressLint("DefaultLocale")
    private void initView() {
        mBackView = findViewById(R.id.imv_bar_back);
        mTitleView = findViewById(R.id.tv_edit_title);
        mChapterView = findViewById(R.id.tv_chapter_name);
        mCourseView = findViewById(R.id.tv_course_name);
        mTimeView = findViewById(R.id.tv_time);
        mHomeworkView = findViewById(R.id.tv_homework);
        mClassView = findViewById(R.id.tv_class);
        mBackView.setOnClickListener(this);
        mTimeView.setOnClickListener(this);
        mHomeworkView.setOnClickListener(this);
        mClassView.setOnClickListener(this);
        mLayout = findViewById(R.id.tab_view);
        mChapterView.setText(mChapter.getChapterName());
        mCourseView.setText(mSchedule.getCourse2().getName());
        ClassInfo classInfo = mSchedule.getClassInfo();
        mTitleView.setText(String.format("%s/%d人", classInfo.getName(), classInfo.getCount()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_bar_back:
                finish();
                break;
            case R.id.tv_time:
                changeTabViewState(mTimeView);
                break;
            case R.id.tv_homework:
                changeTabViewState(mHomeworkView);
                break;
            case R.id.tv_class:
                changeTabViewState(mClassView);
                break;
        }
    }

    private void changeTabViewState(View view) {
        LinearLayout layout = mLayout;
        for (int i = 0; i < layout.getChildCount(); i++) {
            TextView textView = (TextView) layout.getChildAt(i);
            if (textView == view) {
                textView.setTextColor(getColor(R.color.app_global_blue));
                textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_on));
                continue;
            }
            textView.setTextColor(getColor(R.color.textBlack));
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_off));
        }
    }
}
