package com.inlearning.app.director.speciality.classinfo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.inlearning.app.common.bean.Student;
import com.inlearning.app.director.BaseSearchActivity;

import java.util.ArrayList;
import java.util.List;

public class StudentSearchActivity extends BaseSearchActivity {

    public static void startSearchActivity(Context context) {
        Intent intent = new Intent(context, StudentSearchActivity.class);
        context.startActivity(intent);
    }

    private List<Student> mStudentList;
    private StudentInfoAdapter mInfoAdapter;

    @Override
    protected void initAdapter() {
        mStudentList = new ArrayList<>();
        mInfoAdapter = new StudentInfoAdapter(mStudentList);
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
        mStudentList.clear();
        mInfoAdapter.notifyDataSetChanged();
    }

    @Override
    protected String editHint() {
        return "搜索学生";
    }
}
