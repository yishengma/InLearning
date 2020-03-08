package com.inlearning.app.director.teacher;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.util.PixeUtil;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.common.widget.EditItemView;

import static android.view.Gravity.CENTER;

public class TeacherEditActivity extends AppCompatActivity {
    public static void startActivity(Context context, Teacher teacher) {
        Intent intent = new Intent(context, TeacherEditActivity.class);
        intent.putExtra("teacher", teacher);
        context.startActivity(intent);
    }

    protected ViewGroup mRootView;
    protected ImageView mBackView;
    protected TextView mTitleView;
    private EditItemView mNameEditView;
    private EditItemView mJonNumberEditView;
    private EditItemView mTitleEditView;
    private TextView mSaveView;
    private Teacher mTeacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_edit);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        getIntentData();
        initView();
        initSaveButton();
    }


    private void getIntentData() {
        Intent intent = getIntent();
        mTeacher = (Teacher) intent.getSerializableExtra("teacher");
    }

    private void initView() {
        mNameEditView = new EditItemView(this);
        mNameEditView.setHint("姓名");
        mNameEditView.setText(mTeacher.getName());
        mJonNumberEditView = new EditItemView(this);
        mJonNumberEditView.setHint("工号");
        mJonNumberEditView.setText(mTeacher.getJobNumber());
        mTitleEditView = new EditItemView(this);
        mTitleEditView.setHint("职称");
        mTitleEditView.setText(mTeacher.getTitle());
        mRootView = findViewById(R.id.root_view);
        mBackView = findViewById(R.id.imv_edit_back);
        mTitleView = findViewById(R.id.tv_edit_title);
        mTitleView.setText("教师信息");
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRootView.addView(mJonNumberEditView);
        mRootView.addView(mNameEditView);
        mRootView.addView(mTitleEditView);
    }

    private void initSaveButton() {
        mSaveView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = PixeUtil.dp2px(24);
        params.rightMargin = PixeUtil.dp2px(24);
        params.topMargin = PixeUtil.dp2px(24);
        mSaveView.setBackgroundResource(R.drawable.bg_edit_blue_shape);
        mSaveView.setText("更改并保存");
        mSaveView.setEnabled(true);
        mSaveView.setTextColor(Color.WHITE);
        mSaveView.setGravity(CENTER);
        mSaveView.setTextSize(PixeUtil.dp2sp(16));
        mSaveView.setLayoutParams(params);
        mRootView.addView(mSaveView);
        mSaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTeacher.setJobNumber(mJonNumberEditView.getContent())
                        .setTitle(mTitleEditView.getContent())
                        .setName(mNameEditView.getContent());
                TeacherModel.updateTeacher(mTeacher, new TeacherModel.Callback<Teacher>() {
                    @Override
                    public void onResult(boolean suc, Teacher teacher) {
                        if (suc) {
                            showToast("更新成功");
                            finish();

                        }
                    }
                });
            }
        });
    }

    private void showToast(final String msg) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TeacherEditActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
