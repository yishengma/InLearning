package com.inlearning.teacher.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.inlearning.common.adapter.CommonFragmentStatePagerAdapter;
import com.inlearning.common.base.BaseFragment;
import com.inlearning.common.util.StatusBar;
import com.inlearning.teacher.R;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private TabLayout mBottomTab;
    private CommonFragmentStatePagerAdapter mFragmentAdapter;
    private ViewPager mViewPager;
    private List<BaseFragment> mHomepageFragmentList;

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
//        mHomepageFragmentList.add(new RecentFragment());
//        mHomepageFragmentList.add(new ForumFragment());
//        mHomepageFragmentList.add(new ClassFragment());
//        mHomepageFragmentList.add(new PersonFragment());
    }


    private void initView() {
        mViewPager = findViewById(R.id.vp_home_page);
        mFragmentAdapter = new CommonFragmentStatePagerAdapter<>(getSupportFragmentManager(), mHomepageFragmentList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mBottomTab = findViewById(R.id.tl_bottom_tab);
        mBottomTab.setSmoothScrollingEnabled(true);
        mBottomTab.setupWithViewPager(mViewPager);
        mBottomTab.getTabAt(0).setIcon(R.drawable.selector_tab_subscribe);
        mBottomTab.getTabAt(1).setIcon(R.drawable.selector_tab_hot);
        mBottomTab.getTabAt(2).setIcon(R.drawable.selector_tab_original);
        mBottomTab.getTabAt(3).setIcon(R.drawable.selector_tab_excerpt);
        mBottomTab.getTabAt(0).setText("最近");
        mBottomTab.getTabAt(1).setText("课后");
        mBottomTab.getTabAt(2).setText("上课");
        mBottomTab.getTabAt(3).setText("我的");
    }

    public static void startHomePageActivity(Context context) {
        Intent intent = new Intent(context, HomePageActivity.class);
        context.startActivity(intent);
    }
}
