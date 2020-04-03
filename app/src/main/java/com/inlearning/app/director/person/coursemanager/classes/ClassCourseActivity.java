package com.inlearning.app.director.person.coursemanager.classes;

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
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.SpecialitySchedule;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.person.coursemanager.classes.organize.OrganizeCoursePresenter;
import com.inlearning.app.director.person.coursemanager.speciality.SpecialityScheduleModel;
import com.inlearning.app.director.teacher.TeacherModel;

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
    private List<Teacher> mTeachers;
    private List<Course2> mCourse2s;
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
        mTeachers = new ArrayList<>();
        mCourse2s = new ArrayList<>();
        mClassTeaAdapter = new ClassTeaAdapter(mClassSchedules);
        mClassTeaAdapter.setClickListener(new ClassTeaAdapter.ClickListener() {
            @Override
            public void onClick(ClassSchedule schedule) {

            }
        });
        mPresenter = new OrganizeCoursePresenter(this);
        mAddView.setOnClickListener(this);
        mBackView.setOnClickListener(this);
    }


    private void getIntentData() {
        mClassInfo = (ClassInfo) getIntent().getSerializableExtra("classinfo");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_bar_back:
                finish();
                break;
            case R.id.tv_bar_add:
                mPresenter.showOrganizeDialog();
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
        SpecialityScheduleModel.getSpecialitySchedule(mClassInfo.getSpeciality(), new SpecialityScheduleModel.Callback<List<SpecialitySchedule>>() {
            @Override
            public void onResult(List<SpecialitySchedule> specialitySchedules) {
                for (SpecialitySchedule schedule : specialitySchedules) {
                    mCourse2s.add(schedule.getCourse2());
                }
            }
        });
        TeacherModel.getTeacherList(new TeacherModel.Callback<List<Teacher>>() {
            @Override
            public void onResult(boolean suc, List<Teacher> teachers) {
                mTeachers.addAll(teachers);
            }
        });
    }

    private void updateClassCourse(final List<ClassSchedule> classSchedules) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                mClassSchedules.clear();
                mClassSchedules.addAll(classSchedules);
                mClassTeaAdapter.notifyDataSetChanged();
            }
        });

    }
}
