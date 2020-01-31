package com.yishengma.inlearning.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yishengma.inlearning.R;
import com.yishengma.inlearning.adapter.CommonFragmentStatePagerAdapter;
import com.yishengma.inlearning.ui.CourseSearchActivity;

import java.util.ArrayList;
import java.util.List;

public class RecentFragment extends BaseFragment implements View.OnClickListener {
    private ImageView mSearchView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CommonFragmentStatePagerAdapter mFragmentAdapter;
    private List<BaseFragment> mCoursePageFragmentList;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        if (null == getActivity()) {
            return;
        }
        mCoursePageFragmentList = new ArrayList<>();
        mCoursePageFragmentList.add(new RecentAllFragment());
        mCoursePageFragmentList.add(new RecentCompulsoryFragment());
        mCoursePageFragmentList.add(new RecentElectiveFragment());
        mSearchView = view.findViewById(R.id.imv_search);
        mSearchView.setOnClickListener(this);
        mViewPager = view.findViewById(R.id.vp_recent_course);
        mFragmentAdapter = new CommonFragmentStatePagerAdapter<>(getActivity().getSupportFragmentManager(), mCoursePageFragmentList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mTabLayout.setSmoothScrollingEnabled(true);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("全部");
        mTabLayout.getTabAt(1).setText("必修");
        mTabLayout.getTabAt(2).setText("选修");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_search:
                CourseSearchActivity.startActivity(getContext());
                break;
            default:
                break;
        }
    }
}
