package com.inlearning.app.director.person.coursemanager.speciality;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import com.inlearning.app.BaseActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.adapter.CommonFragmentStatePagerAdapter;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.bean.SpecialitySchedule;
import com.inlearning.app.common.util.LoadingDialog;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;

import java.util.ArrayList;
import java.util.List;

import static com.inlearning.app.director.person.coursemanager.speciality.CourseActivity.REQUEST_CODE;

public class SpecialityScheduleActivity extends BaseActivity implements View.OnClickListener, CourseInfoFragment.ClickListener {

    public static void startScheduleActivity(Context context, Speciality speciality) {
        Intent intent = new Intent(context, SpecialityScheduleActivity.class);
        intent.putExtra("speciality", speciality);
        context.startActivity(intent);
    }

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CommonFragmentStatePagerAdapter mFragmentAdapter;
    private List<BaseFragment> mCourseFragmentList;
    private ImageView mBackView;
    private TextView mAddView;
    private Speciality mSpeciality;
    private CourseInfoFragment mSpecialityFragment;
    private CourseInfoFragment mAdaptiveFragment;
    private List<SpecialitySchedule> mSchedules;
    private TextView mTotalInfoView;
    private TextView mSpecialityView;
    private TextView mChooseView;
    private TextView mTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speciality_shedule);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        initView();
        getIntentData();
        initData();
    }

    private void initView() {
        mCourseFragmentList = new ArrayList<>();
        mCourseFragmentList.add(mSpecialityFragment = new CourseInfoFragment().setFragmentTitle("专业课"));
        mCourseFragmentList.add(mAdaptiveFragment = new CourseInfoFragment().setFragmentTitle("选修课"));
        mViewPager = findViewById(R.id.vp_content);
        mFragmentAdapter = new CommonFragmentStatePagerAdapter<>(getSupportFragmentManager(), mCourseFragmentList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.setSmoothScrollingEnabled(true);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("专业课");
        mTabLayout.getTabAt(1).setText("选修课");
        mAddView = findViewById(R.id.tv_bar_add);
        mBackView = findViewById(R.id.imv_bar_back);
        mAddView.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        mSpecialityFragment.setClickListener(this);
        mAdaptiveFragment.setClickListener(this);
        mTotalInfoView = findViewById(R.id.tv_course_total);
        mSpecialityView = findViewById(R.id.tv_course_speciality);
        mChooseView = findViewById(R.id.tv_course_choose);
        mTitleView = findViewById(R.id.tv_edit_title);
    }

    private void getIntentData() {
        mSpeciality = (Speciality) getIntent().getSerializableExtra("speciality");
        mTitleView.setText(String.format("%s/专业排课", mSpeciality.getShortName()));
    }

    private void initData() {
        LoadingDialog.showLoadingDialog(this,"正在加载...");
        SpecialityScheduleModel.getSpecialitySchedule(mSpeciality, new SpecialityScheduleModel.Callback<List<SpecialitySchedule>>() {
            @Override
            public void onResult(List<SpecialitySchedule> specialitySchedules) {
                LoadingDialog.closeDialog();
                List<Course2> course2s = new ArrayList<>();
                for (SpecialitySchedule s : specialitySchedules) {
                    course2s.add(s.getCourse2());
                }
                mAdaptiveFragment.setCourseList(course2s);
                mSpecialityFragment.setCourseList(course2s);
                mSchedules = specialitySchedules;
                int totalScore = 0;
                int specialityCount = 0;
                int specialityScore = 0;
                int chooseScore = 0;
                int chooseCount = 0;


                for (Course2 c : course2s) {
                    if (c.getType().equals("专业课")) {
                        specialityCount++;
                        specialityScore += Integer.valueOf(c.getScore());
                    } else {
                        c.getType().equals("选修课");
                        chooseScore++;
                        chooseCount += Integer.valueOf(c.getScore());
                    }
                }
                totalScore = specialityScore + chooseScore;
                mTotalInfoView.setText(String.format("%s 门课，共 %s 学分", course2s.size(), totalScore));
                mSpecialityView.setText(String.format("专业课 %s 门 ， 共 %s 学分", specialityCount, specialityScore));
                mChooseView.setText(String.format("选修课 %s 门 ， 共 %s 学分", chooseCount, chooseScore));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_bar_back:
                finish();
                break;
            case R.id.tv_bar_add:
                CourseActivity.startActivityForResult(SpecialityScheduleActivity.this, mSpeciality);
                break;
        }
    }

    private static final String TAG = "SpecialityScheduleActiv";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: " + resultCode + requestCode);
        if (resultCode == 0 && requestCode == REQUEST_CODE) {
            SpecialityScheduleModel.getSpecialitySchedule(mSpeciality, new SpecialityScheduleModel.Callback<List<SpecialitySchedule>>() {
                @Override
                public void onResult(List<SpecialitySchedule> specialitySchedules) {
                    List<Course2> course2s = new ArrayList<>();
                    for (SpecialitySchedule s : specialitySchedules) {
                        course2s.add(s.getCourse2());
                    }
                    mSchedules = specialitySchedules;
                    mAdaptiveFragment.setCourseList(course2s);
                    mSpecialityFragment.setCourseList(course2s);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int totalScore = 0;
                            int specialityCount = 0;
                            int specialityScore = 0;
                            int chooseScore = 0;
                            int chooseCount = 0;


                            for (Course2 c : course2s) {
                                if (c.getType().equals("专业课")) {
                                    specialityCount++;
                                    specialityScore += Integer.valueOf(c.getScore());
                                } else {
                                    c.getType().equals("选修课");
                                    chooseScore++;
                                    chooseCount += Integer.valueOf(c.getScore());
                                }
                            }
                            totalScore = specialityScore + chooseScore;
                            mTotalInfoView.setText(String.format("%s 门课，共 %s 学分", course2s.size(), totalScore));
                            mSpecialityView.setText(String.format("专业课 %s 门 ， 共 %s 学分", specialityCount, specialityScore));
                            mChooseView.setText(String.format("选修课 %s 门 ， 共 %s 学分", chooseCount, chooseScore));
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onClick(Course2 course) {
        showDialog(course);
    }


    private void showDialog(final Course2 course2) {
        final Dialog dialog = new Dialog(SpecialityScheduleActivity.this, R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_delete);
        TextView titleView = dialog.findViewById(R.id.tv_title);
        titleView.setText("删除");
        TextView infoView = dialog.findViewById(R.id.tv_content);
        infoView.setText("确定删除该排课信息？删除之后不可恢复！");
        TextView cancelView = dialog.findViewById(R.id.tv_cancel);
        TextView confirmView = dialog.findViewById(R.id.tv_confirm);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse(course2);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void deleteCourse(final Course2 course2) {
        SpecialitySchedule specialitySchedule = null;
        for (SpecialitySchedule s : mSchedules) {
            if (s.getCourse2().getObjectId().equals(course2.getObjectId())) {
                specialitySchedule = s;
                break;
            }
        }
        if (specialitySchedule == null) {
            return;
        }
        mAdaptiveFragment.removeCourse(course2);
        mSpecialityFragment.removeCourse(course2);
        SpecialityScheduleModel.deleteSpecialitySchedule(specialitySchedule, new SpecialityScheduleModel.Callback<SpecialitySchedule>() {
            @Override
            public void onResult(SpecialitySchedule schedule) {
                showToast("删除成功");
            }
        });
    }

    private void showToast(final String msg) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SpecialityScheduleActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
