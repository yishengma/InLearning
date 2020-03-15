package com.inlearning.app.director.person.coursemanager.classes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Course2;

import java.util.ArrayList;
import java.util.List;

public class ClassCourseActivity extends AppCompatActivity {
    public static void startActivity(Context context, ClassInfo classInfo) {
        Intent intent = new Intent(context, ClassListActivity.class);
        intent.putExtra("classinfo", classInfo);
        context.startActivity(intent);
    }

    private TextView mTitleView;
    private ImageView mBackView;
    private RecyclerView mRvCourse;
    private ClassTeaAdapter mClassTeaAdapter;
    private List<ClassScheduletBean> mScheduletBeans;
    private ClassInfo mClassInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_course);
        initView();
        getIntentData();
    }

    private void initView() {
        mTitleView = findViewById(R.id.tv_edit_title);
        mBackView = findViewById(R.id.imv_bar_back);
        mRvCourse = findViewById(R.id.rv_course);
        mRvCourse.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mScheduletBeans = new ArrayList<>();
        mClassTeaAdapter = new ClassTeaAdapter(mScheduletBeans);
        mClassTeaAdapter.setClickListener(new ClassTeaAdapter.ClickListener() {
            @Override
            public void onClick(Course2 course) {

            }
        });
    }

    private void getIntentData() {
        mClassInfo = (ClassInfo) getIntent().getSerializableExtra("classinfo");
    }



}
