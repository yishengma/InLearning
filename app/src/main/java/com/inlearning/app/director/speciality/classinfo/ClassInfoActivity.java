package com.inlearning.app.director.speciality.classinfo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;

import java.util.ArrayList;
import java.util.List;

public class ClassInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mStudentInfoRecyclerView;
    private StudentInfoAdapter mStudentInfoAdapter;
    private ClassInfo mClassInfo;
    private List<Student> mStudentList;
    private TextView mClassInfoView;
    private ClassInfoPresenter mClassInfoPresenter;
    private ImageView mAddView;
    private ImageView mSearchView;

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
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        mStudentInfoRecyclerView = findViewById(R.id.rv_student_info);
        mClassInfoView = findViewById(R.id.bar_title);
        mStudentList = new ArrayList<>();
        mStudentInfoAdapter = new StudentInfoAdapter(mStudentList);
        mStudentInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mStudentInfoRecyclerView.setAdapter(mStudentInfoAdapter);
        mAddView = findViewById(R.id.imv_bar_add);
        mSearchView = findViewById(R.id.imv_bar_search);
        mAddView.setOnClickListener(this);
        mSearchView.setOnClickListener(this);
        mStudentInfoAdapter.setClickListener(new StudentInfoAdapter.ClickListener() {
            @Override
            public void onClick(Student student) {
                StudentInfoActivity.startActivity(ClassInfoActivity.this, student);
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mClassInfo = (ClassInfo) intent.getSerializableExtra("classInfo");
        mClassInfoView.setText(mClassInfo.getName());
    }

    private void initData() {
        getIntentData();
        mClassInfoPresenter = new ClassInfoPresenter();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_bar_search:
                StudentSearchActivity.startSearchActivity(this);
                break;
            case R.id.imv_bar_add:
                showDialog();
                break;
            default:
                break;
        }
    }


    private void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_import_way);
        TextView singleView = dialog.findViewById(R.id.tv_single_import);
        TextView excelView = dialog.findViewById(R.id.tv_excel_import);
        singleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentSingleImportActivity.startSingleImportActivity(ClassInfoActivity.this, mClassInfo);
                dialog.dismiss();
            }
        });
        excelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentExcelImportActivity.startExcelImportActivity(ClassInfoActivity.this, mClassInfo);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
