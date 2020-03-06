package com.inlearning.app.director.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.teacher.edit.JobNumberItem;
import com.inlearning.app.director.teacher.edit.NameItem;
import com.inlearning.app.director.teacher.edit.TitleItem;


@Deprecated
public class TeacherEditActivity extends AppCompatActivity implements View.OnClickListener {

    public static void startTeacherEditActivity(Context context) {
        Intent intent = new Intent(context, TeacherEditActivity.class);
        context.startActivity(intent);
    }

    private JobNumberItem mJobNumberItem;
    private NameItem mNameItem;
    private TitleItem mTitleItem;
    private View mRootView;
    private ImageView mSaveView;
    private ImageView mBackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_edit);
        initView();
    }

    private void initView() {
        mRootView = findViewById(R.id.root_view);
        mJobNumberItem = new JobNumberItem(this, mRootView);
        mNameItem = new NameItem(this, mRootView);
        mTitleItem = new TitleItem(this, mRootView);
        mSaveView = findViewById(R.id.imv_edit_tea_save);
        mBackView = findViewById(R.id.imv_edit_tea_back);
        mSaveView.setOnClickListener(this);
        mBackView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_edit_tea_back:
                finish();
                break;
            case R.id.imv_edit_tea_save:
                addTeaInfo();
                break;

        }
    }

    private void addTeaInfo() {
        Teacher teacher = new Teacher();
        teacher.setJobNumber(mJobNumberItem.getContent())
                .setName(mNameItem.getContent())
                .setTitle(mTitleItem.getContent());
        TeacherModel.addTeacher(teacher, new TeacherModel.Callback<Teacher>() {
            @Override
            public void onResult(boolean suc, Teacher teacher) {
                if (suc) {
                    showToast();
                    finish();
                }
            }
        });
    }

    private void showToast() {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
