package com.inlearning.app.teacher.classes.coursetask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.inlearning.app.BaseActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.ChapterProgress;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.HomeworkProgress;
import com.inlearning.app.common.bean.Question;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.speciality.classinfo.ClassInfoModel;
import com.inlearning.app.teacher.classes.ClassModel;
import com.inlearning.app.teacher.classes.coursetask.task.HomeworkDetailActivity;
import com.inlearning.app.teacher.classes.coursetask.task.HomeworkModel;
import com.inlearning.app.teacher.classes.coursetask.task.HomeworkView;
import com.inlearning.app.teacher.classes.coursetask.task.LearnTimeView;
import com.inlearning.app.teacher.classes.coursetask.task.StuListView;

import java.util.List;

public class CourseTaskActivity extends BaseActivity implements View.OnClickListener {

    public static void startActivity(Context context, ClassSchedule schedule, CourseChapter chapter, int num) {
        Intent intent = new Intent(context, CourseTaskActivity.class);
        intent.putExtra("classschedule", schedule);
        intent.putExtra("coursechapter", chapter);
        intent.putExtra("chapternum", num);
        context.startActivity(intent);
    }

    private ClassSchedule mSchedule;
    private CourseChapter mChapter;
    private int mChapterNum;
    private ImageView mBackView;
    private TextView mTitleView;
    private LinearLayout mLayout;
    private TextView mTimeView;
    private TextView mHomeworkView;
    private TextView mClassView;
    private TextView mCourseView;
    private TextView mChapterView;
    private LearnTimeView mLearnTimeView;
    private HomeworkView mHomeworkTaskView;
    private StuListView mStuListView;
    private FrameLayout mTaskLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_course_task);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        getIntentData();
        initView();
        initData();
    }

    private void getIntentData() {
        mSchedule = (ClassSchedule) getIntent().getSerializableExtra("classschedule");
        mChapter = (CourseChapter) getIntent().getSerializableExtra("coursechapter");
        mChapterNum = getIntent().getIntExtra("chapternum", 0);
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
        mChapterView.setText(String.format("第%s节:%s", mChapterNum, mChapter.getChapterName()));
        mCourseView.setText(mSchedule.getCourse2().getName());
        ClassInfo classInfo = mSchedule.getClassInfo();
        mTitleView.setText(String.format("%s/%d人", classInfo.getName(), classInfo.getCount()));
        mLearnTimeView = findViewById(R.id.view_learn_time);
        mTaskLayout = findViewById(R.id.layout_task);
        mHomeworkTaskView = findViewById(R.id.view_homework_view);
        mHomeworkTaskView.getHomeworkAdapter().setClickListener(new HomeworkView.HomeworkAdapter.ClickListener() {
            @Override
            public void onAnalysis(int pos, Question question) {
                HomeworkDetailActivity.startActivity(CourseTaskActivity.this, question, pos, mSchedule.getClassInfo(), mChapter, HomeworkDetailActivity.HOMEWORK_ANALYSIS);
            }

            @Override
            public void onDetail(int pos, Question question) {
                HomeworkDetailActivity.startActivity(CourseTaskActivity.this, question, pos, mSchedule.getClassInfo(), mChapter, HomeworkDetailActivity.HOMEWORK_DETAIL);
            }

        });
        mStuListView = findViewById(R.id.view_stu_list);
        mStuListView.setCourseAdapter(mChapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_bar_back:
                finish();
                break;
            case R.id.tv_time:
                changeTaskView(mLearnTimeView);
                changeTabViewState(mTimeView);
                break;
            case R.id.tv_homework:
                changeTabViewState(mHomeworkView);
                changeTaskView(mHomeworkTaskView);
                break;
            case R.id.tv_class:
                changeTabViewState(mClassView);
                changeTaskView(mStuListView);
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

    private void changeTaskView(View targetView) {
        for (int i = 0; i < mTaskLayout.getChildCount(); i++) {
            View view = mTaskLayout.getChildAt(i);
            if (view == targetView) {
                view.setVisibility(View.VISIBLE);
                continue;
            }
            view.setVisibility(View.GONE);
        }
    }

    private void initData() {
        CourseTaskModel.getVideoStudyProgress(mChapter, mSchedule.getClassInfo(), new CourseTaskModel.Callback<List<ChapterProgress>>() {
            @Override
            public void onResult(final List<ChapterProgress> chapterProgresses) {
                if (chapterProgresses == null) {
                    return;
                }
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mLearnTimeView.setData(mSchedule.getClassInfo().getCount(), chapterProgresses);
                        mStuListView.setChapterProgresses(chapterProgresses);
                    }
                });
            }
        });
        HomeworkModel.getHomeworkProgress(mChapter, mSchedule.getClassInfo(), new HomeworkModel.Callback<List<Question>, List<HomeworkProgress>>() {
            @Override
            public void callback(List<Question> questions, List<HomeworkProgress> progresses) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mHomeworkTaskView.setQuestionData(questions);
                        mHomeworkTaskView.setPieChartData(mSchedule.getClassInfo().getCount(), questions.size(), progresses);
                        mStuListView.setHomeworkProgresses(questions.size(), progresses);
                    }
                });
            }
        });
        ClassInfoModel.getStudents(mSchedule.getClassInfo(), new ClassInfoModel.Callback<List<Student>>() {
            @Override
            public void onResult(boolean suc, List<Student> students) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mStuListView.setStudents(StuListView.StudentProxy.transfer(students));
                    }
                });
            }
        });
    }
}
