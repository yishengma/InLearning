package com.inlearning.app.director;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.adapter.CommonFragmentStatePagerAdapter;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.director.course.CourseFragment;
import com.inlearning.app.director.course.CourseSearchActivity;
import com.inlearning.app.director.person.PersonFragment;
import com.inlearning.app.director.speciality.SpecialityClassSearchActivity;
import com.inlearning.app.director.speciality.SpecialityEditActivity;
import com.inlearning.app.director.speciality.SpecialityFragment;
import com.inlearning.app.director.teacher.TeacherFragment;
import com.inlearning.app.director.teacher.TeacherSearchActivity;

import java.util.ArrayList;
import java.util.List;

public class DirectorHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DirectorHomeActivity";
    private TabLayout mBottomTab;
    private CommonFragmentStatePagerAdapter mFragmentAdapter;
    private ViewPager mViewPager;
    private List<BaseFragment> mHomepageFragmentList;
    private TextView mBarTitle;
    private RelativeLayout mTitleBar;
    private ImageView mAddView;
    private ImageView mSearchView;
    private TabLayout.Tab mSelectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this,true);
        mHomepageFragmentList = new ArrayList<>();
        initData();
        initView();
    }

    private void initData() {
        mHomepageFragmentList = new ArrayList<>();
        mHomepageFragmentList.add(new SpecialityFragment());
        mHomepageFragmentList.add(new CourseFragment());
        mHomepageFragmentList.add(new TeacherFragment());
        mHomepageFragmentList.add(new PersonFragment());
    }


    private void initView() {
        mViewPager = findViewById(R.id.vp_home_page);
        mFragmentAdapter = new CommonFragmentStatePagerAdapter<>(getSupportFragmentManager(), mHomepageFragmentList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mBarTitle = findViewById(R.id.bar_title);
        mTitleBar = findViewById(R.id.fl_title_bar);
        mBottomTab = findViewById(R.id.tl_bottom_tab);
        mAddView = findViewById(R.id.imv_bar_add);
        mSearchView = findViewById(R.id.imv_bar_search);
        mAddView.setOnClickListener(this);
        mSearchView.setOnClickListener(this);
        mBottomTab.setSmoothScrollingEnabled(true);
        mBottomTab.setupWithViewPager(mViewPager);
        mBottomTab.getTabAt(0).setIcon(R.drawable.tab_selector_speciality);
        mBottomTab.getTabAt(1).setIcon(R.drawable.tab_selector_course);
        mBottomTab.getTabAt(2).setIcon(R.drawable.tab_selector_teacher);
        mBottomTab.getTabAt(3).setIcon(R.drawable.tab_selector_mine);
        mBottomTab.getTabAt(0).setText("专业");
        mBottomTab.getTabAt(1).setText("课程");
        mBottomTab.getTabAt(2).setText("教师");
        mBottomTab.getTabAt(3).setText("我的");
        mSelectedTab = mBottomTab.getTabAt(0);
        mBottomTab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabSelect(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void onTabSelect(TabLayout.Tab tab) {
        mSelectedTab = tab;
        if ("我的".equals(tab.getText())) {
            mTitleBar.setVisibility(View.GONE);
        } else {
            mTitleBar.setVisibility(View.VISIBLE);
            mBarTitle.setText(tab.getText());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_bar_search:
                jumpToSearch();
                break;
            case R.id.imv_bar_add:
                jumpToAdd();
                break;
        }
    }

    private void jumpToSearch() {
        if (mSelectedTab == null) {
            Log.e(TAG, "jumpToSearch");
            return;
        }
        switch (mSelectedTab.getText().toString()) {
            case "专业":
                SpecialityClassSearchActivity.startSearchActivity(this);
                break;
            case "课程":
                CourseSearchActivity.startSearchActivity(this);
                break;
            case "教师":
                TeacherSearchActivity.startSearchActivity(this);
                break;
        }
    }

    private void jumpToAdd() {
        if (mSelectedTab == null) {
            Log.e(TAG, "jumpToAdd");
            return;
        }
        switch (mSelectedTab.getText().toString()) {
            case "专业":
                SpecialityEditActivity.startEditActivity(this);
                break;
            case "课程":
                CourseSearchActivity.startSearchActivity(this);
                break;
            case "教师":
                TeacherSearchActivity.startSearchActivity(this);
                break;
        }
    }



    public static void startHomePageActivity(Context context) {
        Intent intent = new Intent(context, DirectorHomeActivity.class);
        context.startActivity(intent);
    }
}
