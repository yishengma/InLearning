package com.inlearning.director;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.inlearning.common.adapter.CommonFragmentStatePagerAdapter;
import com.inlearning.common.base.BaseFragment;
import com.inlearning.common.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

public class DirectorHomeActivity extends AppCompatActivity {

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
        mHomepageFragmentList.add(new BaseFragment());
        mHomepageFragmentList.add(new BaseFragment());
        mHomepageFragmentList.add(new BaseFragment());
        mHomepageFragmentList.add(new BaseFragment());
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
        mBottomTab.getTabAt(0).setText("专业");
        mBottomTab.getTabAt(1).setText("老师");
        mBottomTab.getTabAt(2).setText("课程");
        mBottomTab.getTabAt(3).setText("我的");
    }

    public static void startHomePageActivity(Context context) {
        Intent intent = new Intent(context, DirectorHomeActivity.class);
        context.startActivity(intent);
    }
}
