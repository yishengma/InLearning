package com.inlearning.app.director.fragment;

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
        mCourseFragmentList.add(new CourseInfoFragment());
        mCourseFragmentList.add(new CourseInfoFragment());
        mViewPager = view.findViewById(R.id.vp_content);
        mFragmentAdapter = new CommonFragmentStatePagerAdapter<>(getChildFragmentManager(), mCourseFragmentList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mTabLayout.setSmoothScrollingEnabled(true);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("第一学年");
        mTabLayout.getTabAt(1).setText("第二学年");
        mTabLayout.getTabAt(2).setText("第三学年");
        mTabLayout.getTabAt(3).setText("第四学年");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_search:
//                CourseSearchActivity.startActivity(getContext());
                break;
            default:
                break;
        }
    }
}
