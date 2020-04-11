package com.inlearning.app.student;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.adapter.CommonFragmentStatePagerAdapter;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.student.course.CourseFragment;
import com.inlearning.app.student.person.PersonFragment;

import java.util.ArrayList;
import java.util.List;

public class StudentHomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DirectorHomeActivity";
    private TabLayout mBottomTab;
    private CommonFragmentStatePagerAdapter mFragmentAdapter;
    private ViewPager mViewPager;
    private List<BaseFragment> mHomepageFragmentList;
    private TextView mBarTitle;
    private RelativeLayout mTitleBar;


    public static void startHomePageActivity(Context context) {
        Intent intent = new Intent(context, StudentHomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        mHomepageFragmentList = new ArrayList<>();
        initData();
        initView();
    }

    private void initData() {
        mHomepageFragmentList = new ArrayList<>();
        mHomepageFragmentList.add(new CourseFragment());
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

        mBottomTab.setSmoothScrollingEnabled(true);
        mBottomTab.setupWithViewPager(mViewPager);
        mBottomTab.getTabAt(0).setIcon(R.drawable.tab_selector_attend_class);
        mBottomTab.getTabAt(1).setIcon(R.drawable.tab_selector_mine);
        mBottomTab.getTabAt(0).setText("上课");
        mBottomTab.getTabAt(1).setText("我的");
    }

    @Override
    public void onClick(View view) {

    }
}
