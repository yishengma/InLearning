package com.inlearning.app.director.person.coursemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.director.person.coursemanager.classes.ClassListActivity;
import com.inlearning.app.director.person.coursemanager.speciality.SpecialityCoursePresenter;

public class CourseManagerActivity extends AppCompatActivity implements View.OnClickListener {
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CourseManagerActivity.class);
        context.startActivity(intent);
    }

    private ImageView mBackView;
    private TextView mSpecialityManagerView;
    private TextView mClassManagerView;
    private View mRootView;
    private LinearLayout mManagerChooseView;
    private SpecialityCoursePresenter mSpecialityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_manager);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        initView();
    }

    private void initView() {
        mBackView = findViewById(R.id.imv_bar_back);
        mSpecialityManagerView = findViewById(R.id.tv_speciality_course);
        mClassManagerView = findViewById(R.id.tv_class_course);
        mBackView.setOnClickListener(this);
        mSpecialityManagerView.setOnClickListener(this);
        mClassManagerView.setOnClickListener(this);
        mRootView = findViewById(R.id.root_view);
        mManagerChooseView = findViewById(R.id.view_manager_choose);
        mSpecialityPresenter = new SpecialityCoursePresenter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_bar_back:
                finish();
                break;
            case R.id.tv_speciality_course:
                mSpecialityPresenter.showView();
                mManagerChooseView.setVisibility(View.GONE);
                break;
            case R.id.tv_class_course:
                ClassListActivity.startActivity(CourseManagerActivity.this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mSpecialityPresenter.isShow()) {
            mSpecialityPresenter.hideView();
            mManagerChooseView.setVisibility(View.VISIBLE);
            return;
        }
        super.onBackPressed();
    }
}
