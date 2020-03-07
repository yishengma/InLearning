package com.inlearning.app.director.course;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.util.FileUtil;
import com.inlearning.app.director.BaseExcelImportActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CourseExcelImportActivity extends BaseExcelImportActivity {
    public static void startExcelImportActivity(Context context) {
        Intent intent = new Intent(context, CourseExcelImportActivity.class);
        context.startActivity(intent);
    }

    private List<Course2> mCourseList;
    private CourseInfoAdapter mInfoAdapter;
    private static final String[] COURSE_INFO = new String[]{"课程名", "姓名", "学时", "学分"};


    @Override
    protected String getTitleMsg() {
        return "新增课程";
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mCourseList = new ArrayList<>();
        mInfoAdapter = new CourseInfoAdapter(mCourseList);
        mInfoAdapter.setImport(true);
        return mInfoAdapter;
    }

    @Override
    protected void delete() {
        Iterator<Course2> iterator = mCourseList.iterator();
        while (iterator.hasNext()) {
            Course2 course = iterator.next();
            if (course.isSelected()) {
                iterator.remove();
            }
        }
        mInfoAdapter.notifyDataSetChanged();
        updateContentView();
    }

    @Override
    protected void upload() {
        CourseModel.addCourseList(mCourseList, new CourseModel.Callback<List<Course2>>() {
            @Override
            public void onResult(boolean suc, List<Course2> course2s) {
                if (suc) {
                    finish();
                }
            }
        });
    }

    @Override
    protected void chooseAll(boolean is) {
        for (Course2 course : mCourseList) {
            course.setSelected(is);
        }
        mInfoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void doOpenFileResult(String path) {
        List<Map<String, String>> data = FileUtil.readExcel(path, COURSE_INFO);
        List<Course2> course2s = new ArrayList<>();
        for (Map<String, String> map : data) {
            Course2 course = new Course2();
            course.setName(map.get("课程名"))
                    .setType(map.get("类型"))
                    .setScore(map.get("学分"))
                    .setTime(map.get("学时"));
            course2s.add(course);
        }
        mCourseList.addAll(course2s);
        mInfoAdapter.notifyDataSetChanged();
        updateContentView();
    }

    private void updateContentView() {
        if (mCourseList.isEmpty()) {
            setEmptyTipsVisibility(View.VISIBLE);
            setRvContentVisibility(View.GONE);
        } else {
            setEmptyTipsVisibility(View.GONE);
            setRvContentVisibility(View.VISIBLE);
        }
    }
}