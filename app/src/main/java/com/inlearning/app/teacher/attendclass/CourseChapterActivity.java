package com.inlearning.app.teacher.attendclass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;

import java.util.ArrayList;
import java.util.List;

public class CourseChapterActivity extends AppCompatActivity implements View.OnClickListener {

    public static void startActivity(Context context, Course2 course2) {
        Intent intent = new Intent(context, CourseChapterActivity.class);
        intent.putExtra("course", course2);
        context.startActivity(intent);
    }

    private ImageView mBackView;
    private TextView mAddView;
    private TextView mTitleView;
    private Course2 mCourse2;
    private RecyclerView mRvChapter;
    private CourseChapterAdapter mChapterAdapter;
    private List<CourseChapter> mChapters;

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
            case R.id.imv_bar_add:
                break;
        }
    }

    private void initView() {
        mBackView = findViewById(R.id.imv_bar_back);
        mTitleView = findViewById(R.id.tv_edit_title);
        mAddView = findViewById(R.id.tv_bar_add);
        mBackView.setOnClickListener(this);
        mAddView.setOnClickListener(this);
        mTitleView.setText(mCourse2.getName());
        mRvChapter = findViewById(R.id.rv_chapter);
        mChapters = new ArrayList<>();
        mChapterAdapter = new CourseChapterAdapter(mChapters);
        mRvChapter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvChapter.setAdapter(mChapterAdapter);
    }

    private void getIntentData() {
        mCourse2 = (Course2) getIntent().getSerializableExtra("course");
    }

    private void initData() {
        CourseModel.getCourseChapter(mCourse2, new CourseModel.Callback<List<CourseChapter>>() {
            @Override
            public void onResult(List<CourseChapter> courseChapters) {
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
            }
        });
    }

}
