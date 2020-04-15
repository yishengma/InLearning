package com.inlearning.app.director.person.coursemanager.speciality;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.adapter.CommonFragmentStatePagerAdapter;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.director.DirectorAppRuntime;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CourseActivity.class);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity activity, Speciality speciality) {
        Intent intent = new Intent(activity, CourseActivity.class);
        intent.putExtra("speciality", speciality);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    public static final int REQUEST_CODE = 1234;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CommonFragmentStatePagerAdapter mFragmentAdapter;
    private List<BaseFragment> mCourseFragmentList;
    private FixCourseInfoFragment mSpecialityFragment;
    private FixCourseInfoFragment mAdaptiveFragment;
    private Speciality mSpeciality;
    private ImageView mBackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        initView();
        initData();
        getIntentData();
    }

    private void initView() {
        mCourseFragmentList = new ArrayList<>();
        mCourseFragmentList.add(mSpecialityFragment = new FixCourseInfoFragment().setFragmentTitle("专业课"));
        mCourseFragmentList.add(mAdaptiveFragment = new FixCourseInfoFragment().setFragmentTitle("选修课"));
        mViewPager = findViewById(R.id.vp_content);
        mFragmentAdapter = new CommonFragmentStatePagerAdapter<>(getSupportFragmentManager(), mCourseFragmentList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.setSmoothScrollingEnabled(true);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("专业课");
        mTabLayout.getTabAt(1).setText("选修课");
        mBackView = findViewById(R.id.imv_bar_back);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void getIntentData() {
        mSpeciality = (Speciality) getIntent().getSerializableExtra("speciality");
        mSpecialityFragment.setSpeciality(mSpeciality);
        mAdaptiveFragment.setSpeciality(mSpeciality);
    }

    private void initData() {
        mAdaptiveFragment.setCourseList(DirectorAppRuntime.getCourse2s());
        mSpecialityFragment.setCourseList(DirectorAppRuntime.getCourse2s());
    }

}
