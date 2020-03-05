package com.inlearning.app.director.teacher;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.director.BaseSearchActivity;

import java.util.ArrayList;
import java.util.List;

public class TeacherSearchActivity extends BaseSearchActivity {

    public static void startSearchActivity(Context context) {
        Intent intent = new Intent(context, TeacherSearchActivity.class);
        context.startActivity(intent);
    }

    private List<Teacher> mTeacherList;
    private TeacherInfoAdapter mInfoAdapter;

    @Override
    protected void initAdapter() {
        mTeacherList = new ArrayList<>();
        mInfoAdapter = new TeacherInfoAdapter(mTeacherList);
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
        mTeacherList.clear();
        mInfoAdapter.notifyDataSetChanged();
    }

    @Override
    protected String editHint() {
        return "搜索教师";
    }
}
