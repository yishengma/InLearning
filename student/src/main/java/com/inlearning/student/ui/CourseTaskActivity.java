package com.inlearning.student.ui;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inlearning.student.R;
import com.inlearning.student.bean.ChapterBean;
import com.inlearning.student.adapter.CourseTaskAdapter;
import com.inlearning.student.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

public class CourseTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRvLessonTasks;
    private CourseTaskAdapter mCourseTaskAdapter;
    private List<ChapterBean> mChapterTasks;
    private TextView mChapterTabView;
    private TextView mNoteTabView;
    private TextView mClassTabView;
    private View mNoteDetailView;
    private View mClassDetailView;
    private ImageView mBackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_task);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);

        initView();
    }

    private void initView() {
        mRvLessonTasks = findViewById(R.id.rv_lesson_task);
        mChapterTasks = new ArrayList<>();
        mChapterTasks.add(new ChapterBean());
        mChapterTasks.add(new ChapterBean());
        mChapterTasks.add(new ChapterBean());
        mChapterTasks.add(new ChapterBean());
        mChapterTasks.add(new ChapterBean());
        mChapterTasks.add(new ChapterBean());
        mChapterTasks.add(new ChapterBean());
        mCourseTaskAdapter = new CourseTaskAdapter(mChapterTasks);
        mRvLessonTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvLessonTasks.setAdapter(mCourseTaskAdapter);
        mChapterTabView = findViewById(R.id.tv_chapter);
        mNoteTabView = findViewById(R.id.tv_note);
        mClassTabView = findViewById(R.id.tv_class);
        mNoteDetailView = findViewById(R.id.layout_note);
        mClassDetailView = findViewById(R.id.layout_class);
        mBackView = findViewById(R.id.imv_back);
        mBackView.setOnClickListener(this);
        mChapterTabView.setOnClickListener(this);
        mNoteTabView.setOnClickListener(this);
        mClassTabView.setOnClickListener(this);
        mCourseTaskAdapter.setOnClickListener(new CourseTaskAdapter.OnClickListener() {
            @Override
            public void onClick(ChapterBean chapter) {
                ClassRoomActivity.startActivity(CourseTaskActivity.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_chapter:
                mRvLessonTasks.setVisibility(View.VISIBLE);
                mNoteDetailView.setVisibility(View.INVISIBLE);
                mClassDetailView.setVisibility(View.INVISIBLE);
                mChapterTabView.setTextColor(getResources().getColor(R.color.textGreen));
                mNoteTabView.setTextColor(getResources().getColor(R.color.textBlack));
                mClassTabView.setTextColor(getResources().getColor(R.color.textBlack));
                mChapterTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_on));
                mNoteTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_off));
                mClassTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_off));
                break;
            case R.id.tv_note:
                mRvLessonTasks.setVisibility(View.INVISIBLE);
                mNoteDetailView.setVisibility(View.VISIBLE);
                mClassDetailView.setVisibility(View.INVISIBLE);
                mChapterTabView.setTextColor(getResources().getColor(R.color.textBlack));
                mNoteTabView.setTextColor(getResources().getColor(R.color.textGreen));
                mClassTabView.setTextColor(getResources().getColor(R.color.textBlack));
                mChapterTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_off));
                mNoteTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_on));
                mClassTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_off));
                break;
            case R.id.tv_class:
                mRvLessonTasks.setVisibility(View.INVISIBLE);
                mNoteDetailView.setVisibility(View.INVISIBLE);
                mClassDetailView.setVisibility(View.VISIBLE);
                mChapterTabView.setTextColor(getResources().getColor(R.color.textBlack));
                mNoteTabView.setTextColor(getResources().getColor(R.color.textBlack));
                mClassTabView.setTextColor(getResources().getColor(R.color.textGreen));
                mChapterTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_off));
                mNoteTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_off));
                mClassTabView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(R.drawable.bg_indicator_on));
                break;
            case R.id.imv_back:
                finish();
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
        Intent intent = new Intent(context, CourseTaskActivity.class);
        context.startActivity(intent);
    }
}
