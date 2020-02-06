package com.inlearning.app.student.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Course;
import com.inlearning.app.student.ui.CourseTaskActivity;
import com.inlearning.app.common.widget.ClassTaskView;
import com.inlearning.app.student.adapter.CourseAdapter;
import com.inlearning.app.student.ui.ClassListActivity;
import com.inlearning.app.student.ui.HomeworkListActivity;
import com.inlearning.app.student.ui.NoteListActivity;
import com.inlearning.app.student.ui.ExerciseActivity;
import java.util.ArrayList;
import java.util.List;

public class ClassFragment extends BaseFragment implements View.OnClickListener {
    private ClassTaskView mAttendClassView;
    private ClassTaskView mExamView;
    private ClassTaskView mNoteView;
    private ClassTaskView mReportView;
    private RecyclerView mLessonsRecyclerView;
    private List<Course> mCourseTasks;
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
            public void onClick(Course task) {
                CourseTaskActivity.startActivity(getActivity());
            }
        });
    }

    private void initData() {
        mCourseTasks = new ArrayList<>();
        mCourseTasks.add(new Course());
        mCourseTasks.add(new Course());
        mCourseTasks.add(new Course());
        mCourseTasks.add(new Course());
        mCourseTasks.add(new Course());
        mCourseTasks.add(new Course());
        mCourseTasks.add(new Course());
        mCourseTasks.add(new Course());
        mCourseTasks.add(new Course());
        mCourseTasks.add(new Course());
        mCourseTasks.add(new Course());
        mCourseTasks.add(new Course());
        mCourseTasks.add(new Course());
        mCourseTasks.add(new Course());

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
