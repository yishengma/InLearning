package com.inlearning.student.ui;

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

import com.inlearning.student.R;
import com.inlearning.student.adapter.NoteInfoAdapter;
import com.inlearning.student.bean.ChapterBean;
import com.inlearning.student.bean.CourseBean;
import com.inlearning.student.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mNoteInfoView;
    private NoteInfoAdapter mNoteInfoAdapter;
    private ImageView mBackView;
    private List<CourseBean> mCourseList;
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

        CourseBean courseBean1 = new CourseBean();
        courseBean1.setName("计算机科学与技术1");
        courseBean1.setChapters(list2);

        CourseBean courseBean2 = new CourseBean();
        courseBean2.setName("计算机科学与技术2");
        courseBean2.setChapters(list2);

        CourseBean courseBean3 = new CourseBean();
        courseBean3.setName("计算机科学与技术3");
        courseBean3.setChapters(list);

        CourseBean courseBean4 = new CourseBean();
        courseBean4.setName("计算机科学与技术4");
        courseBean4.setChapters(list);

        CourseBean courseBean5 = new CourseBean();
        courseBean5.setName("计算机科学与技术5");
        courseBean5.setChapters(list2);

        CourseBean courseBean6 = new CourseBean();
        courseBean6.setName("计算机科学与技术6");
        courseBean6.setChapters(list);
        mCourseList = new ArrayList<>();
        mCourseList.add(courseBean1);
        mCourseList.add(courseBean2);
        mCourseList.add(courseBean3);
        mCourseList.add(courseBean4);
        mCourseList.add(courseBean5);
        mCourseList.add(courseBean6);

        mNoteInfoView = findViewById(R.id.rv_common_view);
        mTitleView = findViewById(R.id.tv_title);
        mTitleView.setText("笔记");
        mNoteInfoAdapter = new NoteInfoAdapter(mCourseList);
        mNoteInfoView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mNoteInfoView.setAdapter(mNoteInfoAdapter);
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
        Intent intent = new Intent(context,NoteListActivity.class);
        context.startActivity(intent);
    }


}
