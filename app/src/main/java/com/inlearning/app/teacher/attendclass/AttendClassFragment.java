package com.inlearning.app.teacher.attendclass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.TeacherCourse;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.student.course.CourseInfoAdapter;
import com.inlearning.app.teacher.TeacherRuntime;

import java.util.ArrayList;
import java.util.List;

public class AttendClassFragment extends BaseFragment {

    private RecyclerView mRvCourse;
    private CourseInfoAdapter mCourseInfoAdapter;
    private List<Course2> mCourse2s;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_attend_class, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View rootView) {
        mRvCourse = rootView.findViewById(R.id.rv_tea_course);
        mRvCourse.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mCourse2s = new ArrayList<>();
        mCourseInfoAdapter = new CourseInfoAdapter(mCourse2s);
        mCourseInfoAdapter.setImport(false);
        mRvCourse.setAdapter(mCourseInfoAdapter);
        mCourseInfoAdapter.setClickListener(new CourseInfoAdapter.ClickListener() {
            @Override
            public void onClick(Course2 course) {
                CourseChapterActivity.startActivity(getContext(), course);
            }
        });
    }

    private void initData() {
        ChapterModel.getTeacherSchedule(new ChapterModel.Callback<List<TeacherCourse>>() {
            @Override
            public void onResult(List<TeacherCourse> classSchedules) {
                TeacherRuntime.setSchedules(classSchedules);
                List<Course2> course2s = new ArrayList<>();
                for (TeacherCourse s : classSchedules) {
                    course2s.add(s.getCourse2());
                }
                updateData(course2s);
            }
        });
    }

    public void updateData(final List<Course2> course2s) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                mCourse2s.clear();
                mCourse2s.addAll(course2s);
                mCourseInfoAdapter.notifyDataSetChanged();
            }
        });
    }


}
