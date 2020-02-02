package com.yishengma.inlearning.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yishengma.inlearning.R;
import com.yishengma.inlearning.bean.CourseBean;
import com.yishengma.inlearning.ui.CourseTaskActivity;
import com.yishengma.inlearning.widget.ClassTaskView;
import com.yishengma.inlearning.adapter.CourseAdapter;
import com.yishengma.inlearning.ui.ClassListActivity;
import com.yishengma.inlearning.ui.HomeworkListActivity;
import com.yishengma.inlearning.ui.NoteListActivity;
import com.yishengma.inlearning.ui.ExerciseActivity;
import java.util.ArrayList;
import java.util.List;

public class ClassFragment extends BaseFragment implements View.OnClickListener {
    private ClassTaskView mAttendClassView;
    private ClassTaskView mExamView;
    private ClassTaskView mNoteView;
    private ClassTaskView mReportView;
    private RecyclerView mLessonsRecyclerView;
    private List<CourseBean> mCourseTasks;
    private CourseAdapter mCourseAdapter;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attend_class, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        mAttendClassView = new ClassTaskView(view.findViewById(R.id.attend_class), view.findViewById(R.id.point_attend_class));
        mExamView = new ClassTaskView(view.findViewById(R.id.homework_and_exam), view.findViewById(R.id.point_exam));
        mNoteView = new ClassTaskView(view.findViewById(R.id.note), view.findViewById(R.id.point_note));
        mReportView = new ClassTaskView(view.findViewById(R.id.study_report), view.findViewById(R.id.point_study_report));
        mAttendClassView.setOnClickListener(this);
        mExamView.setOnClickListener(this);
        mNoteView.setOnClickListener(this);
        mReportView.setOnClickListener(this);
        mLessonsRecyclerView = view.findViewById(R.id.rv_lessons);
        mLessonsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mCourseAdapter = new CourseAdapter(mCourseTasks);
        mLessonsRecyclerView.setAdapter(mCourseAdapter);
        mCourseAdapter.setOnClickListener(new CourseAdapter.OnClickListener() {
            @Override
            public void onClick(CourseBean task) {
                CourseTaskActivity.startActivity(getActivity());
            }
        });
    }

    private void initData() {
        mCourseTasks = new ArrayList<>();
        mCourseTasks.add(new CourseBean());
        mCourseTasks.add(new CourseBean());
        mCourseTasks.add(new CourseBean());
        mCourseTasks.add(new CourseBean());
        mCourseTasks.add(new CourseBean());
        mCourseTasks.add(new CourseBean());
        mCourseTasks.add(new CourseBean());
        mCourseTasks.add(new CourseBean());
        mCourseTasks.add(new CourseBean());
        mCourseTasks.add(new CourseBean());
        mCourseTasks.add(new CourseBean());
        mCourseTasks.add(new CourseBean());
        mCourseTasks.add(new CourseBean());
        mCourseTasks.add(new CourseBean());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attend_class:
                ClassListActivity.startActivity(getContext());
                break;
            case R.id.homework_and_exam:
                HomeworkListActivity.startActivity(getContext());
                break;
            case R.id.note:
                NoteListActivity.startActivity(getContext());
                break;
            case R.id.study_report:
                ExerciseActivity.startActivity(getContext());
                break;
        }

    }
}
