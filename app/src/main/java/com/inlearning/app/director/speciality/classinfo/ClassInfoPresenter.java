package com.inlearning.app.director.speciality.classinfo;

import android.util.Log;

import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassInfoPresenter {
    private static final String TAG = "ClassInfoPresenter";
    public List<Student> addStudents(String filePath) {
        List<Map<String,String>> list = FileUtil.readExcel(filePath,new String[]{"学号","姓名","性别"});
        List<Student> students = new ArrayList<>();
        for (Map<String,String> map:list) {
            Student student = new Student();
            student.mAccount = map.get("学号");
            student.mName = map.get("姓名");
            student.setSex(map.get("性别"));
            students.add(student);
        }
        Log.e(TAG, "addStudents: "+students.size() );
        return students;
    }
}
