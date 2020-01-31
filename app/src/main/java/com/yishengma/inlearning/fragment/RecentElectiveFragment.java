package com.yishengma.inlearning.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yishengma.inlearning.R;
import com.yishengma.inlearning.adapter.RecentCourseAdapter;
import com.yishengma.inlearning.bean.CourseBean;
import com.yishengma.inlearning.ui.CourseTaskActivity;
import java.util.ArrayList;
import java.util.List;

public class RecentElectiveFragment extends BaseFragment {
    private List<CourseBean> mList;
    private RecentCourseAdapter mCourseAdapter;
    private RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_elective, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.rv_recent_course);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        mCourseAdapter = new RecentCourseAdapter(mList);
        mRecyclerView.setAdapter(mCourseAdapter);
        mCourseAdapter.setOnClickListener(new RecentCourseAdapter.OnClickListener() {
            @Override
            public void onClick(CourseBean courseBean) {
                CourseTaskActivity.startActivity(getContext());
            }
        });
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
    }
}
