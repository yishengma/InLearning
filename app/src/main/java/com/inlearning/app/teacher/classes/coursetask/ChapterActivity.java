package com.inlearning.app.teacher.classes.coursetask;

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
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.teacher.attendclass.ChapterModel;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.util.V;

public class ChapterActivity extends AppCompatActivity {

    public static void startActivity(Context context, ClassSchedule schedule) {
        Intent intent = new Intent(context, ChapterActivity.class);
        intent.putExtra("classschedule", schedule);
        context.startActivity(intent);
    }

    private ClassSchedule mSchedule;
    private RecyclerView mRvChapter;
    private List<CourseChapter> mChapters;
    private ChapterAdapter mAdapter;
    private ImageView mBackView;
    private TextView mTitleView;
    private TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_classes_chapter);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        initView();
        getIntentData();
        initData();
    }

    private void initView() {
        mBackView = findViewById(R.id.imv_bar_back);
        mTitleView = findViewById(R.id.tv_edit_title);
        mRvChapter = findViewById(R.id.rv_chapter);
        mEmptyView = findViewById(R.id.tv_empty);
        mChapters = new ArrayList<>();
        mAdapter = new ChapterAdapter(mChapters);
        mAdapter.setListener(new ChapterAdapter.ClickListener() {
            @Override
            public void onClick(CourseChapter courseChapter, int position) {
                CourseTaskActivity.startActivity(ChapterActivity.this, mSchedule, courseChapter, position);
            }
        });
        mRvChapter.setAdapter(mAdapter);
        mRvChapter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getIntentData() {
        mSchedule = (ClassSchedule) getIntent().getSerializableExtra("classschedule");
        mTitleView.setText(String.format("%s/%s", mSchedule.getCourse2().getName(), mSchedule.getClassInfo().getName()));
    }

    private void initData() {
        ChapterModel.getCourseChapter(mSchedule.getCourse2(), new ChapterModel.Callback<List<CourseChapter>>() {
            @Override
            public void onResult(final List<CourseChapter> courseChapters) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("ethan", "chapterActivity" + courseChapters.size());
                        mChapters.clear();
                        mChapters.addAll(courseChapters);
                        mAdapter.notifyDataSetChanged();
                        mEmptyView.setVisibility(mChapters.isEmpty() ? View.VISIBLE : View.GONE);
                    }
                });
            }
        });
    }
}
