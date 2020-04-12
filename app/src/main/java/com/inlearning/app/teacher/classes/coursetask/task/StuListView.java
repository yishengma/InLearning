package com.inlearning.app.teacher.classes.coursetask.task;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Student;

import java.util.ArrayList;
import java.util.List;

public class StuListView extends LinearLayout {

    public StuListView(Context context) {
        this(context, null);
    }

    public StuListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StuListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private List<Student> mStudents;
    private RecyclerView mRvStudent;
    private StudentInfoAdapter mStudentInfoAdapter;
    private CourseChapter mChapter;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_tea_course_task_stu_list, this);
        mRvStudent = view.findViewById(R.id.rv_content);
        mStudents = new ArrayList<>();
        mStudentInfoAdapter = new StudentInfoAdapter(mStudents);
        mRvStudent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRvStudent.setAdapter(mStudentInfoAdapter);
        mStudentInfoAdapter.setClickListener(new StudentInfoAdapter.ClickListener() {
            @Override
            public void onClick(Student student) {
                StuHomeworkActivity.startActivity(getContext(), mChapter, student);
            }
        });
    }

    public void setStudents(List<Student> list) {
        mStudents.clear();
        mStudents.addAll(list);
        mStudentInfoAdapter.notifyDataSetChanged();
    }

    public void setCourseAdapter(CourseChapter courseAdapter) {
        mChapter = courseAdapter;
    }

}
