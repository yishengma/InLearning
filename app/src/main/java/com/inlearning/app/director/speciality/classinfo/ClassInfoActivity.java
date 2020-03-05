package com.inlearning.app.director.speciality.classinfo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.util.FileUtil;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;

import java.util.ArrayList;
import java.util.List;

public class ClassInfoActivity extends AppCompatActivity {
    private RecyclerView mStudentInfoRecyclerView;
    private StudentInfoAdapter mStudentInfoAdapter;
    private ClassInfo mClassInfo;
    private List<Student> mStudentList;
    private TextView mClassInfoView;
    private ClassInfoPresenter mClassInfoPresenter;

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
//        mAddView = findViewById(R.id.imv_add);
//        mAddView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openFileManager();
//            }
//        });
//        mSaveView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ClassInfoModel.saveStudents(mClassInfo,mStudentList);
//            }
//        });
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

    private void openFileManager() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*.xls");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){//选择文件返回
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            switch(requestCode){
                case 1:
                    String chooseFilePath;
                    Uri uri=data.getData();
                    chooseFilePath = FileUtil.getChooseFileResultPath(ClassInfoActivity.this,uri);
                    Log.e("chooseFilePath",chooseFilePath);
                    mStudentList.clear();
                    mStudentList.addAll(mClassInfoPresenter.addStudents(chooseFilePath));
                    mStudentInfoAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
