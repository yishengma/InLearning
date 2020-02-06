package com.inlearning.app.director.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.adapter.CommonFragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SpecialityFragment extends BaseFragment implements View.OnClickListener{
    private ImageView mSearchView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CommonFragmentStatePagerAdapter mFragmentAdapter;
    private List<BaseFragment> mSpecialityFragmentList;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_director_speciality, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        if (null == getActivity()) {
            return;
        }
        mSpecialityFragmentList = new ArrayList<>();
        mSpecialityFragmentList.add(new SpecialityInfoFragment());
        mSpecialityFragmentList.add(new SpecialityInfoFragment());
        mSpecialityFragmentList.add(new SpecialityInfoFragment());
        mSpecialityFragmentList.add(new SpecialityInfoFragment());
        mSpecialityFragmentList.add(new SpecialityInfoFragment());
        mSearchView = view.findViewById(R.id.imv_search);
        mSearchView.setOnClickListener(this);
        mViewPager = view.findViewById(R.id.vp_recent_course);
        mFragmentAdapter = new CommonFragmentStatePagerAdapter<>(getChildFragmentManager(), mSpecialityFragmentList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mTabLayout.setSmoothScrollingEnabled(true);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("全部");
        mTabLayout.getTabAt(1).setText("计科");
        mTabLayout.getTabAt(2).setText("网工");
        mTabLayout.getTabAt(3).setText("软工");
        mTabLayout.getTabAt(4).setText("信安");
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
