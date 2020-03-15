package com.inlearning.app.director.person.coursemanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.bean.SpecialitySchedule;
import com.inlearning.app.director.course.CourseInfoAdapter;

import java.util.ArrayList;
import java.util.List;

public class FixCourseInfoFragment extends BaseFragment {
    private RecyclerView mRvCourseInfo;
    private List<Course2> mCourseList;
    private CourseInfoAdapter mCourseInfoAdapter;
    private String mFragmentTitle;
    private Speciality mSpeciality;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_director_course_info, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRvCourseInfo = view.findViewById(R.id.rv_content);
        mRvCourseInfo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        if (mCourseList == null) {
            mCourseList = new ArrayList<>();
        }
        mCourseInfoAdapter = new CourseInfoAdapter(mCourseList);
        mRvCourseInfo.setAdapter(mCourseInfoAdapter);
        mCourseInfoAdapter.setClickListener(new CourseInfoAdapter.ClickListener() {
            @Override
            public void onClick(Course2 course) {
                addSpeciality(course);
            }

            @Override
            public void onLongClick(View view, float x, float y, Course2 course) {

            }
        });
    }

    public FixCourseInfoFragment setFragmentTitle(String fragmentTitle) {
        mFragmentTitle = fragmentTitle;
        return this;
    }

    public void setCourseList(List<Course2> courseList) {
        if (mCourseList == null) {
            mCourseList = new ArrayList<>();
        }
        mCourseList.clear();
        for (Course2 c : courseList) {
            if (mFragmentTitle.contains(c.getType())) {
                mCourseList.add(c);
            }
        }
        if (mCourseInfoAdapter != null) {
            mCourseInfoAdapter.notifyDataSetChanged();
        }
    }


    public void setSpeciality(Speciality speciality) {
        mSpeciality = speciality;
    }

    public void addSpeciality(Course2 course2) {
        SpecialitySchedule specialitySchedule = new SpecialitySchedule();
        specialitySchedule.setCourse2(course2);
        Speciality speciality = new Speciality();
        speciality.setObjectId(mSpeciality.getObjectId());
        specialitySchedule.setSpeciality(speciality);
        SpecialityScheduleModel.addSpecialitySchedule(specialitySchedule, new SpecialityScheduleModel.Callback<SpecialitySchedule>() {
            @Override
            public void onResult(SpecialitySchedule schedule) {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });
    }
}
