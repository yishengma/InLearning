package com.inlearning.app.director.person.coursemanager.classes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.person.coursemanager.classes.organize.OrganizeCoursePresenter;


import java.util.ArrayList;
import java.util.List;

public class ClassCourseActivity extends AppCompatActivity implements View.OnClickListener {
    public static void startActivity(Context context, ClassInfo classInfo) {
        Intent intent = new Intent(context, ClassCourseActivity.class);
        intent.putExtra("classinfo", classInfo);
        context.startActivity(intent);
    }

    private TextView mTitleView;
    private ImageView mBackView;
    private TextView mAddView;
    private RecyclerView mRvCourse;
    private ClassTeaAdapter mClassTeaAdapter;
    private List<ClassSchedule> mClassSchedules;

    private ClassInfo mClassInfo;
    private OrganizeCoursePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_course);
        initView();
        getIntentData();
        initData();
    }

    private void initView() {
        mTitleView = findViewById(R.id.tv_edit_title);
        mBackView = findViewById(R.id.imv_bar_back);
        mRvCourse = findViewById(R.id.rv_course);
        mAddView = findViewById(R.id.tv_bar_add);
        mRvCourse.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mClassSchedules = new ArrayList<>();

        mClassTeaAdapter = new ClassTeaAdapter(mClassSchedules);

        mClassTeaAdapter.setClickListener(new ClassTeaAdapter.ClickListener() {
            @Override
            public void onClick(ClassSchedule schedule) {

            }
        });
        mRvCourse.setAdapter(mClassTeaAdapter);
        mAddView.setOnClickListener(this);
        mBackView.setOnClickListener(this);
    }


    private void getIntentData() {
        mClassInfo = (ClassInfo) getIntent().getSerializableExtra("classinfo");
        mPresenter = new OrganizeCoursePresenter(this, mClassInfo);
        mPresenter.setClickListener(new OrganizeCoursePresenter.ClickListener() {
            @Override
            public void onAdd(ClassSchedule schedule) {
                updateClassCourse(schedule);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_bar_back:
                finish();
                break;
            case R.id.tv_bar_add:
                mPresenter.showDialog();
                break;
        }
    }

    private void initData() {
        ClassCourseModel.getClassCourse(mClassInfo, new ClassCourseModel.Callback<List<ClassSchedule>>() {
            @Override
            public void onResult(boolean suc, List<ClassSchedule> classSchedules) {
                updateClassCourse(classSchedules);
            }
        });

    }

    private void updateClassCourse(final List<ClassSchedule> classSchedules) {
        Log.e("ethan","updateClassCourse"+classSchedules.size());
                mClassSchedules.clear();
                mClassSchedules.addAll(classSchedules);
                mClassTeaAdapter.notifyDataSetChanged();
        for (ClassSchedule s:classSchedules) {
            Log.e("ethan",s.getCourse2().getName()+"course");
        }
    }


    private void updateClassCourse(final ClassSchedule classSchedule) {

        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                mClassSchedules.add(classSchedule);
                mClassTeaAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }
}
