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
import com.inlearning.app.common.bean.ChapterProgress;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.HomeworkProgress;
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

    private List<StudentProxy> mStudents;
    private RecyclerView mRvStudent;
    private StudentInfoAdapter mStudentInfoAdapter;
    private CourseChapter mChapter;
    private List<ChapterProgress> mChapterProgresses;
    private List<HomeworkProgress> mHomeworkProgresses;
    private int mQuestion;

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

    public void setStudents(List<StudentProxy> list) {
        mStudents.clear();
        mStudents.addAll(list);
        mStudentInfoAdapter.notifyDataSetChanged();
        if (mChapterProgresses != null && !mChapterProgresses.isEmpty()) {
            setChapterProgresses(mChapterProgresses);
        }
        if (mHomeworkProgresses != null && !mHomeworkProgresses.isEmpty()) {
            setHomeworkProgresses(mQuestion, mHomeworkProgresses);
        }
    }

    public void setChapterProgresses(List<ChapterProgress> list) {
        mChapterProgresses = list;
        if (mStudents == null || mStudents.isEmpty()) {
            return;
        }
        //
        for (ChapterProgress progress : list) {
            Student student = progress.getStudent();
            for (StudentProxy proxy : mStudents) {
                if (proxy.getStudent().equals(student)) {
                    proxy.setVideoDone(progress.isDone());
                    break;
                }
            }
        }
        mStudentInfoAdapter.notifyDataSetChanged();
    }

    public void setHomeworkProgresses(int count, List<HomeworkProgress> list) {
        mHomeworkProgresses = list;
        mQuestion = count;
        if (mStudents == null || mStudents.isEmpty()) {
            return;
        }
        //
        for (HomeworkProgress progress : list) {
            Student student = progress.getStudent();
            for (StudentProxy proxy : mStudents) {
                if (proxy.getStudent().equals(student)) {
                    proxy.setHomeworkDone(progress.getProgress() == count);
                    break;
                }
            }
        }
        mStudentInfoAdapter.notifyDataSetChanged();
    }

    public void setCourseAdapter(CourseChapter courseAdapter) {
        mChapter = courseAdapter;
    }


    public static class StudentProxy {
        private Student mStudent;
        private boolean isHomeworkDone;
        private boolean isVideoDone;

        public StudentProxy(Student student) {
            mStudent = student;
        }

        public Student getStudent() {
            return mStudent;
        }

        public void setStudent(Student student) {
            mStudent = student;
        }

        public boolean isHomeworkDone() {
            return isHomeworkDone;
        }

        public void setHomeworkDone(boolean homeworkDone) {
            isHomeworkDone = homeworkDone;
        }

        public boolean isVideoDone() {
            return isVideoDone;
        }

        public void setVideoDone(boolean videoDone) {
            isVideoDone = videoDone;
        }

        public static List<StudentProxy> transfer(List<Student> list) {
            ArrayList<StudentProxy> proxies = new ArrayList<>();
            for (Student student : list) {
                proxies.add(new StudentProxy(student));
            }
            return proxies;
        }
    }

}
