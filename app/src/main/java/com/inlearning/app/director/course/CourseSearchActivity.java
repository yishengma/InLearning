package com.inlearning.app.director.course;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.director.BaseSearchActivity;

import java.util.ArrayList;
import java.util.List;

public class CourseSearchActivity extends BaseSearchActivity {

    public static void startSearchActivity(Context context) {
        Intent intent = new Intent(context, CourseSearchActivity.class);
        context.startActivity(intent);
    }

    private List<Course2> mCourseList;
    private CourseInfoAdapter mInfoAdapter;

    @Override
    protected void initAdapter() {
        mCourseList = new ArrayList<>();
        mInfoAdapter = new CourseInfoAdapter(mCourseList);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return mInfoAdapter;
    }

    @Override
    protected void doSearch(String key) {

    }

    @Override
    protected void resetList() {
        mCourseList.clear();
        mInfoAdapter.notifyDataSetChanged();
    }

    @Override
    protected String editHint() {
        return "搜索课程";
    }
}
