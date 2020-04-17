package com.inlearning.app.director.person.coursemanager.classes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.person.coursemanager.classes.organize.OrganizeCoursePresenter;

import java.util.ArrayList;
import java.util.List;

public class ClassCourseActivity extends AppCompatActivity implements View.OnClickListener {
    public static void startActivity(Context context, ClassInfo classInfo) {
        Intent intent = new Intent(context, ClassCourseActivity.class);
        intent.putExtra("classinfo", classInfo);
        context.startActivity(intent);
    }

    private TextView mTitleView;
    private ImageView mBackView;
    private TextView mAddView;
    private RecyclerView mRvCourse;
    private ClassTeaAdapter mClassTeaAdapter;
    private List<ClassSchedule> mClassSchedules;

    private ClassInfo mClassInfo;
    private OrganizeCoursePresenter mPresenter;
    private TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_course);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        initView();
        getIntentData();
        initData();
    }

    private void initView() {
        mTitleView = findViewById(R.id.tv_edit_title);
        mBackView = findViewById(R.id.imv_bar_back);
        mRvCourse = findViewById(R.id.rv_course);
        mAddView = findViewById(R.id.tv_bar_add);
        mRvCourse.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mClassSchedules = new ArrayList<>();

        mClassTeaAdapter = new ClassTeaAdapter(mClassSchedules);

        mClassTeaAdapter.setClickListener(new ClassTeaAdapter.ClickListener() {
            @Override
            public void onClick(ClassSchedule schedule) {
                showDialog(schedule);
            }
        });
        mRvCourse.setAdapter(mClassTeaAdapter);
        mAddView.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        mEmptyView = findViewById(R.id.tv_empty);
    }


    private void getIntentData() {
        mClassInfo = (ClassInfo) getIntent().getSerializableExtra("classinfo");
        mPresenter = new OrganizeCoursePresenter(this, mClassInfo, mClassSchedules);
        mPresenter.setClickListener(new OrganizeCoursePresenter.ClickListener() {
            @Override
            public void onAdd(ClassSchedule schedule) {
                updateClassCourse(schedule);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_bar_back:
                finish();
                break;
            case R.id.tv_bar_add:
                mPresenter.showDialog();
                break;
        }
    }

    private void initData() {
        ClassCourseModel.getClassCourse(mClassInfo, new ClassCourseModel.Callback<List<ClassSchedule>>() {
            @Override
            public void onResult(boolean suc, List<ClassSchedule> classSchedules) {
                updateClassCourse(classSchedules);
            }
        });

    }

    private void updateClassCourse(final List<ClassSchedule> classSchedules) {
        mClassSchedules.clear();
        mClassSchedules.addAll(classSchedules);
        mClassTeaAdapter.notifyDataSetChanged();
        mEmptyView.setVisibility(mClassSchedules.isEmpty() ? View.VISIBLE : View.GONE);
    }


    private void updateClassCourse(final ClassSchedule classSchedule) {

        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                mClassSchedules.add(classSchedule);
                mClassTeaAdapter.notifyDataSetChanged();
                mEmptyView.setVisibility(mClassSchedules.isEmpty() ? View.VISIBLE : View.GONE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    private void showDialog(ClassSchedule schedule) {
        Dialog dialog = new Dialog(this, R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_organize_course);
        TextView titleView = dialog.findViewById(R.id.tv_title);
        TextView selectCourseView = dialog.findViewById(R.id.tv_select_course);
        TextView courseNameView = dialog.findViewById(R.id.tv_course_name);
        TextView courseInfoView = dialog.findViewById(R.id.tv_course_info);
        TextView selectTeaView = dialog.findViewById(R.id.tv_select_teacher);
        ImageView teaIconView = dialog.findViewById(R.id.imv_teacher_icon);
        TextView teaNameView = dialog.findViewById(R.id.tv_teacher_name);
        TextView teaJobNumView = dialog.findViewById(R.id.tv_teacher_job_number);
        TextView cancelView = dialog.findViewById(R.id.tv_cancel);
        TextView confirmView = dialog.findViewById(R.id.tv_confirm);
        View selectTeaLayout = dialog.findViewById(R.id.view_select_teacher);
        selectCourseView.setVisibility(View.GONE);
        selectTeaLayout.setVisibility(View.VISIBLE);
        selectTeaView.setVisibility(View.GONE);
        courseNameView.setText(schedule.getCourse2().getName());
        courseNameView.setVisibility(View.VISIBLE);
        courseInfoView.setVisibility(View.VISIBLE);
        courseInfoView.setText(String.format("学分：%s 学时：%s", schedule.getCourse2().getScore(), schedule.getCourse2().getTime()));
        if (TextUtils.isEmpty(schedule.getTeacher().getProfilePhotoUrl())) {
            teaIconView.setBackground(getDrawable(R.drawable.viewpage_guide_3));
        } else {
            Glide.with(this).load(schedule.getTeacher().getProfilePhotoUrl()).into(teaIconView);
        }
        teaNameView.setText(schedule.getTeacher().getName());
        teaJobNumView.setText(schedule.getTeacher().getAccount());
        confirmView.setText("删除");
        titleView.setText("排课");
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(dialog, schedule);
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void showDeleteDialog(Dialog firsetDialog, final ClassSchedule schedule) {
        final Dialog dialog = new Dialog(this, R.style.SimpleDialog);//SimpleDialog
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
                firsetDialog.dismiss();
            }
        });
        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassCourseModel.deleteClassCourse(schedule, new ClassCourseModel.Callback<ClassSchedule>() {
                    @Override
                    public void onResult(boolean suc, ClassSchedule schedule) {
                        ThreadMgr.getInstance().postToUIThread(new Runnable() {
                            @Override
                            public void run() {
                                mClassSchedules.remove(schedule);
                                mClassTeaAdapter.notifyDataSetChanged();
                                mEmptyView.setVisibility(mClassSchedules.isEmpty() ? View.VISIBLE : View.GONE);
                            }
                        });
                    }
                });
                dialog.dismiss();
                firsetDialog.dismiss();
            }
        });
        dialog.show();
    }


}
