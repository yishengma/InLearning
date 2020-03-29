package com.inlearning.app.teacher.attendclass;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobPointer;

public class CourseChapterActivity extends AppCompatActivity implements View.OnClickListener {

    public static void startActivity(Context context, Course2 course2) {
        Intent intent = new Intent(context, CourseChapterActivity.class);
        intent.putExtra("course", course2);
        context.startActivity(intent);
    }

    private ImageView mBackView;
    private TextView mAddView;
    private TextView mTitleView;
    private Course2 mCourse2;
    private RecyclerView mRvChapter;
    private CourseChapterAdapter mChapterAdapter;
    private List<CourseChapter> mChapters;
    private Dialog mChapterDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        setContentView(R.layout.activity_course_chapter);
        getIntentData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_bar_back:
                finish();
                break;
            case R.id.tv_bar_add:
                showAddDialog();
                break;
        }
    }

    private void initView() {
        mBackView = findViewById(R.id.imv_bar_back);
        mTitleView = findViewById(R.id.tv_edit_title);
        mAddView = findViewById(R.id.tv_bar_add);
        mBackView.setOnClickListener(this);
        mAddView.setOnClickListener(this);
        mTitleView.setText(mCourse2.getName());
        mRvChapter = findViewById(R.id.rv_chapter);
        mChapters = new ArrayList<>();
        mChapterAdapter = new CourseChapterAdapter(mChapters);
        mRvChapter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvChapter.setAdapter(mChapterAdapter);
        mChapterAdapter.setOnClickListener(new CourseChapterAdapter.OnClickListener() {
            @Override
            public void onTitleClick() {
                Log.e("ethan","onTitleClick");
            }

            @Override
            public void onVideoClick() {
                Log.e("ethan","onVideoClick");
            }

            @Override
            public void onTimeClick() {
                Log.e("ethan","onTimeClick");
            }

            @Override
            public void onExerciseClick() {
                Log.e("ethan","onExerciseClick");
            }

            @Override
            public void onMaterialClick() {
                Log.e("ethan","onMaterialClick");
            }

            @Override
            public void onHomeworkClick() {
                Log.e("ethan","onHomeworkClick");
            }

            @Override
            public void onDiscussClick() {
                Log.e("ethan","onDiscussClick");
            }
        });
    }

    private void getIntentData() {
        mCourse2 = (Course2) getIntent().getSerializableExtra("course");
    }

    private void initData() {
        ChapterModel.getCourseChapter(mCourse2, new ChapterModel.Callback<List<CourseChapter>>() {
            @Override
            public void onResult(List<CourseChapter> courseChapters) {
                updateChapters(courseChapters);
            }
        });
    }

    private void updateChapters(final List<CourseChapter> courseChapters) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                mChapters.clear();
                mChapters.addAll(courseChapters);
                mChapterAdapter.notifyDataSetChanged();
            }
        });
    }

    private void showAddDialog() {
        mChapterDialog = new Dialog(this, R.style.SimpleDialog);//SimpleDialog
        mChapterDialog.setContentView(R.layout.dialog_tea_add_chapter);
        mChapterDialog.setCanceledOnTouchOutside(true);
        final TextInputEditText editText = mChapterDialog.findViewById(R.id.et_input_content);
        TextView cancelView = mChapterDialog.findViewById(R.id.tv_cancel);
        TextView confirmView = mChapterDialog.findViewById(R.id.tv_confirm);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChapterDialog.dismiss();
            }
        });
        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chapterName = editText.getText().toString();
                if (TextUtils.isEmpty(chapterName)) {
                    Toast.makeText(CourseChapterActivity.this, "请输入章节名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                CourseChapter courseChapter = new CourseChapter();
                courseChapter.setCourse2(mCourse2);
                courseChapter.setChapterNum(mChapters.size() + 1);
                courseChapter.setChapterName(chapterName);
                ChapterModel.addCourseChapter(courseChapter, new ChapterModel.Callback<CourseChapter>() {
                    @Override
                    public void onResult(CourseChapter chapter) {
                        updateAddChapter(chapter);
                    }
                });
            }
        });
        mChapterDialog.show();
    }

    private void updateAddChapter(final CourseChapter chapter) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                if (chapter != null) {
                    mChapters.add(chapter);
                    mChapterAdapter.notifyDataSetChanged();
                    mChapterDialog.dismiss();
                    return;
                }
                Toast.makeText(CourseChapterActivity.this, "添加失败", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
