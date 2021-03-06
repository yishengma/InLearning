package com.inlearning.app.director.speciality;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.adapter.CommonFragmentStatePagerAdapter;
import com.inlearning.app.common.bean.Speciality;

import java.util.ArrayList;
import java.util.List;

public class SpecialityPresenter {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CommonFragmentStatePagerAdapter mFragmentAdapter;
    private List<BaseFragment> mSpecialityFragmentList;
    private List<TabLayout.Tab> mTabList;
    private List<Speciality> mSpecialities;

    public SpecialityPresenter(FragmentManager manager, View rootView) {
        mSpecialityFragmentList = new ArrayList<>();
        mTabList = new ArrayList<>();
        mSpecialities = new ArrayList<>();
        mTabLayout = rootView.findViewById(R.id.tab_layout);
        mViewPager = rootView.findViewById(R.id.vp_recent_course);
        mFragmentAdapter = new CommonFragmentStatePagerAdapter<>(manager, mSpecialityFragmentList);
        mViewPager.setAdapter(mFragmentAdapter);

        mTabLayout.setSmoothScrollingEnabled(true);
        for (TabLayout.Tab tab : mTabList) {
            mTabLayout.addTab(tab);
        }
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabList.size(); i++) {
            mTabLayout.getTabAt(i).setText(mSpecialities.get(i).getShortName());
        }
    }

    public void addSpeciality(Speciality speciality) {
        mSpecialityFragmentList.add(new SpecialityInfoFragment().setSpeciality(speciality));
        mFragmentAdapter.notifyDataSetChanged(true);
        TabLayout.Tab tab = mTabLayout.newTab();
        tab.setText(speciality.getShortName());
        mTabLayout.addTab(tab);
        mTabList.add(tab);
        mTabLayout.setupWithViewPager(mViewPager);
        mSpecialities.add(speciality);
        mViewPager.setOffscreenPageLimit(mSpecialities.size());
        for (int i = 0; i < mTabList.size(); i++) {
            mTabLayout.getTabAt(i).setText(mSpecialities.get(i).getShortName());
        }
    }

    public void reset() {
        mSpecialityFragmentList.clear();
        mFragmentAdapter.notifyDataSetChanged();
        mSpecialities.clear();
        mTabList.clear();
        mTabLayout.removeAllTabs();
    }
}
