package com.inlearning.app.director.speciality.classinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    public static void startSearchActivity(Context context, ArrayList<Student> students) {
        Intent intent = new Intent(context, StudentSearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("students",students);
        intent.putExtra("bundle",bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        mStudents = (ArrayList<Student>) bundle.getSerializable("students");
    }

    private ArrayList<Student> mStudents;
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
        mStudentList.clear();
        for (Student student: mStudents) {
            if (student.getName().contains(key) || student.getAccount().contains(key)) {
                mStudentList.add(student);
            }
        }
        mInfoAdapter.notifyDataSetChanged();
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
