package com.inlearning.app.director.teacher;

import android.support.v7.widget.RecyclerView;

import com.inlearning.app.director.BaseExcelImportActivity;

public class TeacherExcelImportActivity extends BaseExcelImportActivity {

    @Override
    protected String getTitleMsg() {
        return "新增教师";
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return null;
    }
}
