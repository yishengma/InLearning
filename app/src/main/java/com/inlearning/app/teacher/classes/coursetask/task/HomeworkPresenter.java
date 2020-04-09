package com.inlearning.app.teacher.classes.coursetask.task;


import android.app.Activity;
import android.view.View;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Student;

import com.inlearning.app.student.course.func.homework.Homework;

import java.util.ArrayList;
import java.util.List;


public class HomeworkPresenter {

    private HomeworkFuncView mHomeworkFuncView;
    private List<Homework> mHomeworkList;
    private HomeworkAdapter mHomeworkAdapter;
    private CourseChapter mChapter;
    private Student mStudent;

    public HomeworkPresenter(final Activity context, CourseChapter chapter, Student student) {
        mHomeworkFuncView = context.findViewById(R.id.view_homework_func);
        mHomeworkFuncView.setClickListener(new HomeworkFuncView.ClickListener() {
            @Override
            public void onBack() {
                context.finish();
            }
        });
        mHomeworkList = new ArrayList<>();
        mHomeworkAdapter = new HomeworkAdapter(mHomeworkList, context);

        mHomeworkFuncView.getRvHomework().setAdapter(mHomeworkAdapter);
        mChapter = chapter;
        mStudent = student;
        HomeworkModel.getHomework(mChapter, mStudent, new HomeworkModel.Callback2<List<Homework>>() {
            @Override
            public void callback(List<Homework> homework) {
                mHomeworkList.clear();
                mHomeworkList.addAll(homework);
                mHomeworkAdapter.notifyDataSetChanged();
                mHomeworkFuncView.refreshUI(!mHomeworkList.isEmpty());
            }
        });
    }

    public void show() {
        mHomeworkFuncView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mHomeworkFuncView.setVisibility(View.GONE);
    }

}
