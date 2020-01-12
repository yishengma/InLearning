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
import android.view.View;
import android.widget.TextView;

import com.yishengma.inlearning.R;

import java.util.ArrayList;
import java.util.List;

public class LessonTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private RecyclerView mRvLessonTasks;
    private LessonTaskAdapter mLessonTaskAdapter;
    private List<ChapterTask> mChapterTasks;
    private TextView mChapterTabView;
    private TextView mNoteTabView;
    private TextView mClassTabView;
    private View mNoteDetailView;
    private View mClassDetailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_task);
        initView();
    }

    private void initView() {
//        mToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowTitleEnabled(false);
//        }
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
        mRvLessonTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvLessonTasks.setAdapter(mLessonTaskAdapter);
        mChapterTabView = findViewById(R.id.tv_chapter);
        mNoteTabView = findViewById(R.id.tv_note);
        mClassTabView = findViewById(R.id.tv_class);
        mNoteDetailView = findViewById(R.id.layout_note);
        mClassDetailView = findViewById(R.id.layout_class);
        mChapterTabView.setOnClickListener(this);
        mNoteTabView.setOnClickListener(this);
        mClassTabView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_chapter:
                mRvLessonTasks.setVisibility(View.VISIBLE);
                mNoteDetailView.setVisibility(View.INVISIBLE);
                mClassDetailView.setVisibility(View.INVISIBLE);
                mChapterTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_on));
                mNoteTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_off));
                mClassTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_off));
                break;
            case R.id.tv_note:
                mRvLessonTasks.setVisibility(View.INVISIBLE);
                mNoteDetailView.setVisibility(View.VISIBLE);
                mClassDetailView.setVisibility(View.INVISIBLE);
                mChapterTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_off));
                mNoteTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_on));
                mClassTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_off));
                break;
            case R.id.tv_class:
                mRvLessonTasks.setVisibility(View.INVISIBLE);
                mNoteDetailView.setVisibility(View.INVISIBLE);
                mClassDetailView.setVisibility(View.VISIBLE);
                mChapterTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_off));
                mNoteTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_off));
                mClassTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_on));
                break;

        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home: {
//                finish();
//            }
//            break;
//        }
//        return true;
//    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LessonTaskActivity.class);
        context.startActivity(intent);
    }
}