package com.inlearning.app.director.course;

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

import java.util.ArrayList;
import java.util.List;

public class CourseInfoFragment extends BaseFragment{
    private RecyclerView mRvCourseInfo;
    private List<Course2> mCourseList;
    private CourseInfoAdapter mCourseInfoAdapter;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_director_course_info, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRvCourseInfo = view.findViewById(R.id.rv_content);
        mRvCourseInfo.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mCourseList = new ArrayList<>();
        mCourseInfoAdapter = new CourseInfoAdapter(mCourseList);
        mRvCourseInfo.setAdapter(mCourseInfoAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        CourseModel.getCourseList(new CourseModel.Callback<List<Course2>>() {
            @Override
            public void onResult(boolean suc, List<Course2> course2s) {
                mCourseList.clear();
                mCourseList.addAll(course2s);
                mCourseInfoAdapter.notifyDataSetChanged();
            }
        });
    }
}

