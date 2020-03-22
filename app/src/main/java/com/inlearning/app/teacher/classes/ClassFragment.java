package com.inlearning.app.teacher.classes;

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
import com.inlearning.app.common.bean.ClassSchedule;

import java.util.ArrayList;
import java.util.List;

public class ClassFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CommonFragmentStatePagerAdapter mFragmentAdapter;
    private List<BaseFragment> mFragmentList;
    private List<TabLayout.Tab> mTabList;
    private List<ClassSchedule> mSchedules;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_class, container, false);
        initView(view);
        return view;
    }

    public void initView(View rootView) {
        mTabList = new ArrayList<>();
        mSchedules = new ArrayList<>();
        mTabLayout = rootView.findViewById(R.id.tab_layout);
        mViewPager = rootView.findViewById(R.id.vp_class);
        mFragmentAdapter = new CommonFragmentStatePagerAdapter<>(getChildFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setSmoothScrollingEnabled(true);
        for (TabLayout.Tab tab : mTabList) {
            mTabLayout.addTab(tab);
        }
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabList.size(); i++) {
            mTabLayout.getTabAt(i).setText(mSchedules.get(i).getCourse2().getName());
        }
    }
}
