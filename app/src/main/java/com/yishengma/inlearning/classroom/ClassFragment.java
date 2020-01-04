package com.yishengma.inlearning.classroom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yishengma.inlearning.R;
import com.yishengma.inlearning.classroom.attendclass.ClassListActivity;
import com.yishengma.inlearning.classroom.exam.ExamListActivity;
import com.yishengma.inlearning.classroom.node.NoteListActivity;
import com.yishengma.inlearning.classroom.report.StudyReportActivity;
import com.yishengma.inlearning.home.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class ClassFragment extends BaseFragment implements View.OnClickListener {
    private ClassTaskView mAttendClassView;
    private ClassTaskView mExamView;
    private ClassTaskView mNoteView;
    private ClassTaskView mReportView;
    private RecyclerView mLessonsRecyclerView;
    private List<LessonTask> mLessonTasks;
    private LessonsAdapter mLessonsAdapter;

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
        mLessonsAdapter = new LessonsAdapter(mLessonTasks);
        mLessonsRecyclerView.setAdapter(mLessonsAdapter);
    }

    private void initData() {
        mLessonTasks = new ArrayList<>();
        mLessonTasks.add(new LessonTask("计算机网络","http://pic.weifengke.com/attachments/1/1572/29cca9ba6c7cf1209c7f00918fbef35b.jpg","已结束","小名老师","http://bpic.588ku.com/element_origin_min_pic/01/38/06/41573c70d7d87fb.jpg",12,3));
        mLessonTasks.add(new LessonTask("计算机网络","http://pic.weifengke.com/attachments/1/1572/29cca9ba6c7cf1209c7f00918fbef35b.jpg","已结束","小名老师","http://bpic.588ku.com/element_origin_min_pic/01/38/06/41573c70d7d87fb.jpg",12,3));
        mLessonTasks.add(new LessonTask("计算机网络","http://pic.weifengke.com/attachments/1/1572/29cca9ba6c7cf1209c7f00918fbef35b.jpg","已结束","小名老师","http://bpic.588ku.com/element_origin_min_pic/01/38/06/41573c70d7d87fb.jpg",12,3));
        mLessonTasks.add(new LessonTask("计算机网络","http://pic.weifengke.com/attachments/1/1572/29cca9ba6c7cf1209c7f00918fbef35b.jpg","已结束","小名老师","http://bpic.588ku.com/element_origin_min_pic/01/38/06/41573c70d7d87fb.jpg",12,3));
        mLessonTasks.add(new LessonTask("计算机网络","http://pic.weifengke.com/attachments/1/1572/29cca9ba6c7cf1209c7f00918fbef35b.jpg","已结束","小名老师","http://bpic.588ku.com/element_origin_min_pic/01/38/06/41573c70d7d87fb.jpg",12,3));
        mLessonTasks.add(new LessonTask("计算机网络","http://pic.weifengke.com/attachments/1/1572/29cca9ba6c7cf1209c7f00918fbef35b.jpg","已结束","小名老师","http://bpic.588ku.com/element_origin_min_pic/01/38/06/41573c70d7d87fb.jpg",12,3));
        mLessonTasks.add(new LessonTask("计算机网络","http://pic.weifengke.com/attachments/1/1572/29cca9ba6c7cf1209c7f00918fbef35b.jpg","已结束","小名老师","http://bpic.588ku.com/element_origin_min_pic/01/38/06/41573c70d7d87fb.jpg",12,3));
        mLessonTasks.add(new LessonTask("计算机网络","http://pic.weifengke.com/attachments/1/1572/29cca9ba6c7cf1209c7f00918fbef35b.jpg","已结束","小名老师","http://bpic.588ku.com/element_origin_min_pic/01/38/06/41573c70d7d87fb.jpg",12,3));
        mLessonTasks.add(new LessonTask("计算机网络","http://pic.weifengke.com/attachments/1/1572/29cca9ba6c7cf1209c7f00918fbef35b.jpg","已结束","小名老师","http://bpic.588ku.com/element_origin_min_pic/01/38/06/41573c70d7d87fb.jpg",12,3));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attend_class:
                ClassListActivity.startActivity(getContext());
                break;
            case R.id.homework_and_exam:
                ExamListActivity.startActivity(getContext());
                break;
            case R.id.note:
                NoteListActivity.startActivity(getContext());
                break;
            case R.id.study_report:
                StudyReportActivity.startActivity(getContext());
                break;
        }

    }
}
