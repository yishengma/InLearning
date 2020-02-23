package com.inlearning.app.director.speciality.classinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.util.ThreadMgr;

import java.util.ArrayList;
import java.util.List;

public class ClassInfoActivity extends AppCompatActivity {
    private RecyclerView mStudentInfoRecyclerView;
    private StudentInfoAdapter mStudentInfoAdapter;
    private ClassInfo mClassInfo;
    private List<Student> mStudentList;

    public static void startActivity(Context context,ClassInfo classInfo) {
        Intent intent = new Intent(context, ClassInfoActivity.class);
        intent.putExtra("classInfo",classInfo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_info);
        initView();
        initData();
    }

    private void initView() {
        mStudentInfoRecyclerView = findViewById(R.id.rv_student_info);
        mStudentList = new ArrayList<>();
        mStudentInfoAdapter = new StudentInfoAdapter(mStudentList);
        mStudentInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mStudentInfoRecyclerView.setAdapter(mStudentInfoAdapter);
    }

    private void initData() {
        ClassInfoModel.getStudents(mClassInfo, new ClassInfoModel.Callback<List<Student>>() {
            @Override
            public void onResult(boolean suc, List<Student> students) {
                if (suc) {
                    updateList(students);
                }
            }
        });
    }

    private void updateList(final List<Student> students) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                mStudentList.clear();
                mStudentList.addAll(students);
                mStudentInfoAdapter.notifyDataSetChanged();
            }
        });
    }



}
