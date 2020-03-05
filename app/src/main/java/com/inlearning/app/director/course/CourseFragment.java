package com.inlearning.app.director.course;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.adapter.CommonFragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class CourseFragment extends BaseFragment implements View.OnClickListener {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CommonFragmentStatePagerAdapter mFragmentAdapter;
    private List<BaseFragment> mCourseFragmentList;
//    private ImageView mAddView;
//    private ImageView mSearchView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_director_course, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        if (null == getActivity()) {
            return;
        }
        mCourseFragmentList = new ArrayList<>();
        mCourseFragmentList.add(new CourseInfoFragment());
        mCourseFragmentList.add(new CourseInfoFragment());
        mViewPager = view.findViewById(R.id.vp_content);
        mFragmentAdapter = new CommonFragmentStatePagerAdapter<>(getChildFragmentManager(), mCourseFragmentList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout = view.findViewById(R.id.tab_layout);
//        mAddView = view.findViewById(R.id.imv_add);
//        mSearchView = view.findViewById(R.id.imv_search);
        mTabLayout.setSmoothScrollingEnabled(true);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("专业课");
        mTabLayout.getTabAt(1).setText("选修课");
//        mAddView.setOnClickListener(this);
//        mSearchView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_search:
//                CourseSearchActivity.startActivity(getContext());
                break;
//            case R.id.imv_add:
//                CourseEditActivity.startActivity(getContext());
//                break;
            default:
                break;
        }
    }
}
