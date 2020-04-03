package com.inlearning.app.director.person.coursemanager.classes.organize;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.SpecialitySchedule;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.person.coursemanager.classes.ClassCourseActivity;
import com.inlearning.app.director.person.coursemanager.speciality.SpecialityScheduleModel;
import com.inlearning.app.director.teacher.TeacherModel;
import com.inlearning.app.teacher.attendclass.func.ChapterFunctionActivity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class OrganizeListActivity extends AppCompatActivity {

    @IntDef({OrganizeListActivity.FLAG.TEACHER_LIST, OrganizeListActivity.FLAG.COURSE_LIST})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
    public @interface FLAG {
        int TEACHER_LIST = 0;
        int COURSE_LIST = 1;
    }

    public static void startActivity(Activity context, ClassInfo classInfo, @OrganizeListActivity.FLAG int flag) {
        Intent intent = new Intent(context, OrganizeListActivity.class);
        intent.putExtra("flag", flag);
        intent.putExtra("classinfo", classInfo);
        context.startActivityForResult(intent, flag);
    }

    private TeacherListView mTeacherListView;
    private CourseListView mCourseListView;
    private List<Teacher> mTeachers;
    private List<Course2> mCourse2s;
    private ClassInfo mClassInfo;
    private int mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organize_list);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        mTeacherListView = findViewById(R.id.view_tea_list);
        mCourseListView = findViewById(R.id.view_course_list);
        mCourseListView.setClickListener(new CourseListView.ClickListener() {
            @Override
            public void onClick(Course2 course) {
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("course", course);
                //设置返回数据
                setResult(RESULT_OK, intent);//RESULT_OK为自定义常量
                finish();
            }

            @Override
            public void onBack() {
                finish();
            }
        });
        mTeacherListView.setClickListener(new TeacherListView.ClickListener() {
            @Override
            public void onClick(Teacher teacher) {
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("teacher", teacher);
                //设置返回数据
                setResult(RESULT_OK, intent);//RESULT_OK为自定义常量
                finish();
            }

            @Override
            public void onBack() {
                finish();
            }
        });
        mTeachers = new ArrayList<>();
        mCourse2s = new ArrayList<>();
        mClassInfo = (ClassInfo) getIntent().getSerializableExtra("classinfo");
        mFlag = (int) getIntent().getSerializableExtra("flag");
        if (mFlag == FLAG.COURSE_LIST) {
            mCourseListView.setVisibility(View.VISIBLE);
        }
        if (mFlag == FLAG.TEACHER_LIST) {
            mTeacherListView.setVisibility(View.VISIBLE);
        }
        SpecialityScheduleModel.getSpecialitySchedule(mClassInfo.getSpeciality(), new SpecialityScheduleModel.Callback<List<SpecialitySchedule>>() {
            @Override
            public void onResult(List<SpecialitySchedule> specialitySchedules) {
                for (SpecialitySchedule schedule : specialitySchedules) {
                    mCourse2s.add(schedule.getCourse2());
                }
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mCourseListView.updateList(mCourse2s);
                    }
                });
            }
        });
        TeacherModel.getTeacherList(new TeacherModel.Callback<List<Teacher>>() {
            @Override
            public void onResult(boolean suc, List<Teacher> teachers) {
                mTeachers.addAll(teachers);
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mTeacherListView.updateList(mTeachers);
                    }
                });
            }
        });
    }

}
