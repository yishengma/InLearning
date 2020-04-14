package com.inlearning.app.director.teacher;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.util.FileUtil;
import com.inlearning.app.common.util.LoadingDialogUtil;
import com.inlearning.app.common.util.ToastUtil;
import com.inlearning.app.director.BaseExcelImportActivity;

import org.apache.poi.ss.usermodel.CellType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TeacherExcelImportActivity extends BaseExcelImportActivity {
    public static void startExcelImportActivity(Context context) {
        Intent intent = new Intent(context, TeacherExcelImportActivity.class);
        context.startActivity(intent);
    }

    private List<Teacher> mTeacherList;
    private TeacherInfoAdapter mInfoAdapter;
    private static final String[] TEACHER_INFO = new String[]{"工号", "姓名", "职称"};
    private static final CellType[] CELL_TYPES = new CellType[]{CellType.STRING,CellType.STRING,CellType.STRING};


    @Override
    protected String getTitleMsg() {
        return "新增教师";
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mTeacherList = new ArrayList<>();
        mInfoAdapter = new TeacherInfoAdapter(mTeacherList);
        mInfoAdapter.setImport(true);
        return mInfoAdapter;
    }

    @Override
    protected void delete() {
        Iterator<Teacher> iterator = mTeacherList.iterator();
        while (iterator.hasNext()) {
            Teacher teacher = iterator.next();
            if (teacher.isSelected()) {
                iterator.remove();
            }
        }
        mInfoAdapter.notifyDataSetChanged();
        updateContentView();
    }

    @Override
    protected void upload() {
        LoadingDialogUtil.showLoadingDialog(TeacherExcelImportActivity.this, "正在上传");
        TeacherModel.addTeacherList(mTeacherList, new TeacherModel.Callback<List<Teacher>>() {
            @Override
            public void onResult(boolean suc, List<Teacher> teachers) {
                LoadingDialogUtil.closeDialog();
                if (suc) {
                    finish();
                }
            }
        });
    }

    @Override
    protected void chooseAll(boolean is) {
        for (Teacher teacher : mTeacherList) {
            teacher.setSelected(is);
        }
        mInfoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void doOpenFileResult(String path) {
        List<Map<String, String>> data = FileUtil.readExcel(path, TEACHER_INFO, CELL_TYPES);
        if (data == null) {
            ToastUtil.showToast("导入失败，请检查文件格式", Toast.LENGTH_SHORT);
            return;
        }
        List<Teacher> teachers = new ArrayList<>();
        for (Map<String, String> map : data) {
            Teacher teacher = new Teacher();
            teacher.setAccount(map.get("工号"));
            teacher.setTitle(map.get("职称"));
            teacher.setName(map.get("姓名"));
            teachers.add(teacher);
        }
        mTeacherList.addAll(teachers);
        mInfoAdapter.notifyDataSetChanged();
        updateContentView();
    }

    private void updateContentView() {
        if (mTeacherList.isEmpty()) {
            setEmptyTipsVisibility(View.VISIBLE);
            setRvContentVisibility(View.GONE);
        } else {
            setEmptyTipsVisibility(View.GONE);
            setRvContentVisibility(View.VISIBLE);
        }
    }
}
