package com.inlearning.student.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.inlearning.student.R;
import com.inlearning.student.adapter.RecentCourseAdapter;
import com.inlearning.student.bean.CourseBean;
import com.inlearning.student.ui.CourseTaskActivity;
import java.util.ArrayList;
import java.util.List;

public class RecentAllFragment extends BaseFragment {
    private List<CourseBean> mList;
    private RecentCourseAdapter mCourseAdapter;
    private RecyclerView mRecyclerView;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        initData();
        initViewPager(view);
        return view;
    }



    private void initViewPager(View view) {

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

        mList.add(new CourseBean().setItemType(RecentCourseAdapter.ITEM_TYPE_BANNER));
        mList.add(new CourseBean().setItemType(RecentCourseAdapter.ITEM_TYPE_TITLE));
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean().setItemType(RecentCourseAdapter.ITEM_TYPE_TITLE));
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
    }


    //    @Override
//    protected void onA() {
//        if (mBannerViewPager != null)
//            mBannerViewPager.stopLoop();
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mBannerViewPager != null)
//            mBannerViewPager.startLoop();
//    }
}