package com.inlearning.app.director.person.coursemanager.classes.organize;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.exoplayer2.C;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.common.util.ToastUtil;
import com.inlearning.app.director.person.coursemanager.classes.ClassCourseModel;

import java.util.List;

public class OrganizeCoursePresenter {

    private Activity mContext;
    private Dialog mDialog;
    private TextView mSelectCourseView;
    private TextView mCourseNameView;
    private TextView mCourseInfoView;

    private TextView mSelectTeaView;
    private RelativeLayout mSelectTeaLayout;
    private ImageView mTeaIconView;
    private TextView mTeaNameView;
    private TextView mTeaJobNumView;
    private TextView mCancelView;
    private TextView mConfirmView;


    private Teacher mTeacher;
    private Course2 mCourse2;
    private ClassInfo mClassInfo;

    private List<ClassSchedule> mSchedules;


    public interface ClickListener {
        void onAdd(ClassSchedule schedule);
    }

    private ClickListener mClickListener;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public OrganizeCoursePresenter(Activity context, ClassInfo classInfo,List<ClassSchedule> schedules) {
        mContext = context;
        mClassInfo = classInfo;
        mSchedules = schedules;
        initDialog();
    }

    public void initDialog() {
        mDialog = new Dialog(mContext, R.style.SimpleDialog);//SimpleDialog
        mDialog.setContentView(R.layout.dialog_organize_course);
        mSelectCourseView = mDialog.findViewById(R.id.tv_select_course);
        mCourseNameView = mDialog.findViewById(R.id.tv_course_name);
        mCourseInfoView = mDialog.findViewById(R.id.tv_course_info);
        mSelectTeaView = mDialog.findViewById(R.id.tv_select_teacher);
        mTeaIconView = mDialog.findViewById(R.id.imv_teacher_icon);
        mTeaNameView = mDialog.findViewById(R.id.tv_teacher_name);
        mTeaJobNumView = mDialog.findViewById(R.id.tv_teacher_job_number);
        mCancelView = mDialog.findViewById(R.id.tv_cancel);
        mConfirmView = mDialog.findViewById(R.id.tv_confirm);
        mSelectTeaLayout = mDialog.findViewById(R.id.view_select_teacher);
        mCancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                resetCourseView();
                resetTeacherView();
            }
        });
        mConfirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadClassSchedule();
            }
        });
        mDialog.setCanceledOnTouchOutside(true);
        mSelectTeaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrganizeListActivity.startActivity(mContext, mClassInfo, mCourse2, OrganizeListActivity.FLAG.TEACHER_LIST);
            }
        });

        mSelectCourseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrganizeListActivity.startActivity(mContext, mClassInfo, OrganizeListActivity.FLAG.COURSE_LIST);
            }
        });
    }

    public void showDialog() {
        resetCourseView();
        resetTeacherView();
        mDialog.show();
    }


    private void setTeacherView(Teacher teacher) {
        if (teacher == null) {
            return;
        }
        mTeacher = teacher;
        mSelectTeaView.setVisibility(View.GONE);
        mTeaIconView.setVisibility(View.VISIBLE);
        mTeaNameView.setVisibility(View.VISIBLE);
        mTeaJobNumView.setVisibility(View.VISIBLE);

        mTeaNameView.setText(teacher.getName());
        mTeaJobNumView.setText(teacher.getAccount());
    }

    private void resetTeacherView() {
        mSelectTeaView.setVisibility(View.VISIBLE);
        mTeaIconView.setVisibility(View.GONE);
        mTeaNameView.setVisibility(View.GONE);
        mTeaJobNumView.setVisibility(View.GONE);
        mTeacher = null;
    }

    private void setCourseView(Course2 course) {
        if (course == null) {
            return;
        }
        mCourse2 = course;
        mSelectCourseView.setVisibility(View.GONE);
        mCourseNameView.setVisibility(View.VISIBLE);
        mCourseInfoView.setVisibility(View.VISIBLE);
        mCourseNameView.setText(course.getName());
        mCourseInfoView.setText(String.format("学分：%s 学时：%s", course.getScore(), course.getTime()));
        mSelectTeaLayout.setVisibility(View.VISIBLE);
    }

    private void resetCourseView() {
        mCourse2 = null;
        mSelectCourseView.setVisibility(View.VISIBLE);
        mCourseNameView.setVisibility(View.GONE);
        mCourseInfoView.setVisibility(View.GONE);
        mSelectTeaLayout.setVisibility(View.GONE);
    }

    private void updateConfirmView() {
        if (mTeacher == null || mCourse2 == null) {
            mConfirmView.setEnabled(false);
            return;
        }
        mConfirmView.setEnabled(true);
    }

    private void uploadClassSchedule() {
        if (mCourse2 == null || mTeacher == null) {
            ToastUtil.showToast("请先选择课程和教师", Toast.LENGTH_SHORT);
            return;
        }
        for (ClassSchedule schedule:mSchedules) {
            if (schedule.getCourse2().equals(mCourse2.getName())) {
                ToastUtil.showToast("请勿重复添加课程", Toast.LENGTH_SHORT);
                return;
            }
        }
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setClassInfo(mClassInfo);
        classSchedule.setCourse2(mCourse2);
        classSchedule.setTeacher(mTeacher);
        ClassCourseModel.addClassCourse(classSchedule, new ClassCourseModel.Callback<ClassSchedule>() {
            @Override
            public void onResult(boolean suc, ClassSchedule schedule) {
                if (suc && mClickListener != null) {
                    mClickListener.onAdd(schedule);
                    hide();
                }

            }
        });
    }

    private void hide() {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
                resetCourseView();
                resetTeacherView();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case OrganizeListActivity.FLAG.COURSE_LIST:
                setCourseView((Course2) data.getSerializableExtra("course"));

                break;
            case OrganizeListActivity.FLAG.TEACHER_LIST:
                setTeacherView((Teacher) data.getSerializableExtra("teacher"));
                break;
        }
    }
}
