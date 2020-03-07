package com.inlearning.app.director.speciality.classinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.util.FileUtil;
import com.inlearning.app.director.BaseExcelImportActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StudentExcelImportActivity extends BaseExcelImportActivity {
    public static void startExcelImportActivity(Context context, ClassInfo classInfo) {
        Intent intent = new Intent(context, StudentExcelImportActivity.class);
        intent.putExtra("classInfo", classInfo);
        context.startActivity(intent);
    }

    private List<Student> mStudentList;
    private StudentInfoAdapter mInfoAdapter;
    private ClassInfo mClassInfo;
    private static final String[] STUDENT_INFO = new String[]{"学号", "姓名", "性别"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
    }


    private void getIntentData() {
        Intent intent = getIntent();
        mClassInfo = (ClassInfo) intent.getSerializableExtra("classInfo");
    }

    @Override
    protected String getTitleMsg() {
        return "新增教师";
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mStudentList = new ArrayList<>();
        mInfoAdapter = new StudentInfoAdapter(mStudentList);
        mInfoAdapter.setImport(true);
        return mInfoAdapter;
    }

    @Override
    protected void delete() {
        Iterator<Student> iterator = mStudentList.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.isSelected()) {
                iterator.remove();
            }
        }
        mInfoAdapter.notifyDataSetChanged();
        updateContentView();
    }

    @Override
    protected void upload() {
        ClassInfoModel.saveStudents(mClassInfo, mStudentList, new ClassInfoModel.Callback<Student>() {
            @Override
            public void onResult(boolean suc, Student student) {
                if (suc) {
                    finish();
                }
            }
        });
    }

    @Override
    protected void chooseAll(boolean is) {
        for (Student student : mStudentList) {
            student.setSelected(is);
        }
        mInfoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void doOpenFileResult(String path) {
        List<Map<String, String>> data = FileUtil.readExcel(path, STUDENT_INFO);
        List<Student> students = new ArrayList<>();
        for (Map<String, String> map : data) {
            Student student = new Student();
            student.mAccount = map.get("学号");
            student.mName = map.get("姓名");
            student.setSex(map.get("性别"));
            students.add(student);
        }
        mStudentList.addAll(students);
        mInfoAdapter.notifyDataSetChanged();
        updateContentView();
    }

    private void updateContentView() {
        if (mStudentList.isEmpty()) {
            setEmptyTipsVisibility(View.VISIBLE);
            setRvContentVisibility(View.GONE);
        } else {
            setEmptyTipsVisibility(View.GONE);
            setRvContentVisibility(View.VISIBLE);
        }
    }
}
