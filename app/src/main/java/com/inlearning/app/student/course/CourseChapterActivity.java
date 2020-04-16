package com.inlearning.app.student.course;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.util.LoadingDialogUtil;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.student.course.func.ChapterFunctionActivity;
import com.inlearning.app.student.course.func.video.StuVideoActivity;
import com.inlearning.app.teacher.attendclass.ChapterModel;


import java.util.ArrayList;

import java.util.List;


public class CourseChapterActivity extends AppCompatActivity implements View.OnClickListener {

    public static void startActivity(Context context, ClassSchedule schedule) {
        Intent intent = new Intent(context, CourseChapterActivity.class);
        intent.putExtra("classschedule", schedule);
        context.startActivity(intent);
    }

    private ImageView mBackView;
    private TextView mAddView;
    private TextView mTitleView;
    private ClassSchedule mClassSchedule;
    private RecyclerView mRvChapter;
    private CourseChapterAdapter mChapterAdapter;
    private List<CourseChapter> mChapters;
    private TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        setContentView(R.layout.activity_course_chapter);
        getIntentData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_bar_back:
                finish();
                break;
            case R.id.tv_bar_add:

                break;
        }
    }

    private void initView() {
        mBackView = findViewById(R.id.imv_bar_back);
        mTitleView = findViewById(R.id.tv_edit_title);
        mAddView = findViewById(R.id.tv_bar_add);
        mBackView.setOnClickListener(this);
        mAddView.setVisibility(View.GONE);
        mTitleView.setText(mClassSchedule.getCourse2().getName());
        mRvChapter = findViewById(R.id.rv_chapter);
        mEmptyView = findViewById(R.id.tv_empty);
        mChapters = new ArrayList<>();
        mChapterAdapter = new CourseChapterAdapter(mChapters);
        mRvChapter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvChapter.setAdapter(mChapterAdapter);
        mChapterAdapter.setOnClickListener(new CourseChapterAdapter.OnClickListener() {
            @Override
            public void onTitleClick(CourseChapter chapter) {
            }

            @Override
            public void onVideoClick(CourseChapter chapter) {
                StuVideoActivity.startActivity(CourseChapterActivity.this, chapter);
            }

            @Override
            public void onTimeClick(CourseChapter chapter) {

            }

            @Override
            public void onExerciseClick(CourseChapter chapter) {
            }

            @Override
            public void onMaterialClick(CourseChapter chapter) {
                ChapterFunctionActivity.startActivity(CourseChapterActivity.this, chapter, ChapterFunctionActivity.FLAG.MATERIAL_FUNCTION);

            }

            @Override
            public void onHomeworkClick(CourseChapter chapter) {
                ChapterFunctionActivity.startActivity(CourseChapterActivity.this, chapter, ChapterFunctionActivity.FLAG.HOMEWORK_FUNCTION);
            }

            @Override
            public void onDiscussClick(CourseChapter chapter) {
                ChapterFunctionActivity.startActivity(CourseChapterActivity.this, chapter, ChapterFunctionActivity.FLAG.DISCUSS_FUNCTION);
            }
        });
    }

    private void getIntentData() {
        mClassSchedule = (ClassSchedule) getIntent().getSerializableExtra("classschedule");
    }

    private void initData() {
        LoadingDialogUtil.showLoadingDialog(this, "正在加载...");
        CourseModel.getCourseChapter(mClassSchedule, new ChapterModel.Callback<List<CourseChapter>>() {
            @Override
            public void onResult(List<CourseChapter> courseChapters) {
                LoadingDialogUtil.closeDialog();
                updateChapters(courseChapters);
            }
        });
    }

    private void updateChapters(final List<CourseChapter> courseChapters) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                mChapters.clear();
                mChapters.addAll(courseChapters);
                mChapterAdapter.notifyDataSetChanged();
                mEmptyView.setVisibility(mChapters.isEmpty() ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
