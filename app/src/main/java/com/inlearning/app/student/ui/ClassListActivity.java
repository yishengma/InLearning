package com.inlearning.app.student.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inlearning.app.student.adapter.ClassInfoAdapter;
import com.inlearning.app.student.bean.ChapterBean;
import com.inlearning.app.common.bean.Course;
import com.inlearning.app.R;
import com.inlearning.app.common.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

public class ClassListActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mClassInfosView;
    private ClassInfoAdapter mClassInfoAdapter;
    private ImageView mBackView;
    private List<Course> mCourseList;
    private TextView mTitleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_common_view);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this,true);
        List<ChapterBean> list = new ArrayList<>();
        list.add(new ChapterBean().setName("第一11节拷贝数据"));
        list.add(new ChapterBean().setName("第一22节拷贝数据"));

        List<ChapterBean> list2 = new ArrayList<>();
        list2.add(new ChapterBean().setName("第一1节拷贝数据"));
        list2.add(new ChapterBean().setName("第一2节拷贝数据"));
        list2.add(new ChapterBean().setName("第一3节拷贝数据"));
        list2.add(new ChapterBean().setName("第一4节拷贝数据"));
        list2.add(new ChapterBean().setName("第一5节拷贝数据"));
        list2.add(new ChapterBean().setName("第一6节拷贝数据"));

        Course course1 = new Course();
        course1.setName("计算机科学与技术1");
        course1.setChapters(list2);

        Course course2 = new Course();
        course2.setName("计算机科学与技术2");
        course2.setChapters(list2);

        Course course3 = new Course();
        course3.setName("计算机科学与技术3");
        course3.setChapters(list);

        Course course4 = new Course();
        course4.setName("计算机科学与技术4");
        course4.setChapters(list);

        Course course5 = new Course();
        course5.setName("计算机科学与技术5");
        course5.setChapters(list2);

        Course course6 = new Course();
        course6.setName("计算机科学与技术6");
        course6.setChapters(list);
        mCourseList = new ArrayList<>();
        mCourseList.add(course1);
        mCourseList.add(course2);
        mCourseList.add(course3);
        mCourseList.add(course4);
        mCourseList.add(course5);
        mCourseList.add(course6);

        mClassInfosView = findViewById(R.id.rv_common_view);
        mTitleView = findViewById(R.id.tv_title);
        mTitleView.setText("上课");
        mClassInfoAdapter = new ClassInfoAdapter(mCourseList);
        mClassInfosView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mClassInfosView.setAdapter(mClassInfoAdapter);
        mBackView = findViewById(R.id.back);
        mBackView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ClassListActivity.class);
        context.startActivity(intent);
    }
}
