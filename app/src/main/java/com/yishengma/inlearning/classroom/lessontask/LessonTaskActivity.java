package com.yishengma.inlearning.classroom.lessontask;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.yishengma.inlearning.R;

import java.util.ArrayList;
import java.util.List;

public class LessonTaskActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRvLessonTasks;
    private LessonTaskAdapter mLessonTaskAdapter;
    private List<ChapterTask> mChapterTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_task);
        initView();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mRvLessonTasks = findViewById(R.id.rv_lesson_task);
        mChapterTasks = new ArrayList<>();
        mChapterTasks.add(new ChapterTask());
        mChapterTasks.add(new ChapterTask());
        mChapterTasks.add(new ChapterTask());
        mChapterTasks.add(new ChapterTask());
        mChapterTasks.add(new ChapterTask());
        mChapterTasks.add(new ChapterTask());
        mChapterTasks.add(new ChapterTask());
        mLessonTaskAdapter = new LessonTaskAdapter(mChapterTasks);
        mRvLessonTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;//静止滚动
            }
        });
        mRvLessonTasks.setAdapter(mLessonTaskAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
            }
            break;
        }
        return true;
    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LessonTaskActivity.class);
        context.startActivity(intent);
    }
}
