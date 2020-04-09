package com.inlearning.app.teacher.classes.coursetask.task;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Answer;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Question;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.teacher.classes.coursetask.ChapterActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.util.V;

public class HomeworkDetailActivity extends AppCompatActivity implements View.OnClickListener {


    public static final int HOMEWORK_DETAIL = 0;
    public static final int HOMEWORK_ANALYSIS = 1;

    public static void startActivity(Context context, Question question, int position, ClassInfo classInfo, CourseChapter chapter, int flag) {
        Intent intent = new Intent(context, HomeworkDetailActivity.class);
        intent.putExtra("chapter", chapter);
        intent.putExtra("question", question);
        intent.putExtra("classInfo", classInfo);
        intent.putExtra("flag", flag);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    private int mFlag;
    private CourseChapter mChapter;
    private Question mQuestion;
    private ClassInfo mClassInfo;
    private ImageView mBackView;
    private TextView mTitleView;
    private TextView mSubTitleView;
    private TextView mCheckQuestionView;
    private int mQuesPosition;
    private HomeworkAnalysisView mHomeworkAnalysisView;
    private HomeworkDetailView mHomeworkDetailView;
    private HomeworkCheckView mHomeworkCheckView;
    private FrameLayout mBarLayout;
    private List<Answer> mAnswers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        setContentView(R.layout.activity_homework_detail);
        getIntentData();
        initView();
        initData();
    }

    private void getIntentData() {
        mChapter = (CourseChapter) getIntent().getSerializableExtra("chapter");
        mQuestion = (Question) getIntent().getSerializableExtra("question");
        mClassInfo = (ClassInfo) getIntent().getSerializableExtra("classInfo");
        mFlag = getIntent().getIntExtra("flag", -1);
        mQuesPosition = getIntent().getIntExtra("position", -1);
    }

    private void initView() {
        mBackView = findViewById(R.id.imv_bar_back);
        mTitleView = findViewById(R.id.tv_bar_title);
        mSubTitleView = findViewById(R.id.tv_bar_subtitle);
        mCheckQuestionView = findViewById(R.id.tv_bar_check);
        mHomeworkAnalysisView = findViewById(R.id.view_homework_analysis);
        mHomeworkDetailView = findViewById(R.id.view_homework_detail);
        mHomeworkCheckView = findViewById(R.id.view_homework_check);
        mBarLayout = findViewById(R.id.fl_title_bar);
        if (mFlag == HOMEWORK_DETAIL) {
            mHomeworkAnalysisView.setVisibility(View.GONE);
            mHomeworkDetailView.setVisibility(View.VISIBLE);
        }
        if (mFlag == HOMEWORK_ANALYSIS) {
            mHomeworkAnalysisView.setVisibility(View.VISIBLE);
            mHomeworkDetailView.setVisibility(View.GONE);
        }
        mTitleView.setText(mChapter.getChapterName());
        mSubTitleView.setText(String.format("<第%s道>", mQuesPosition));
        mBackView.setOnClickListener(this);
        mCheckQuestionView.setOnClickListener(this);
        mHomeworkCheckView.setClickListener(new HomeworkCheckView.ClickListener() {
            @Override
            public void onBack() {
                if (mFlag == HOMEWORK_DETAIL) {
                    mHomeworkAnalysisView.setVisibility(View.GONE);
                    mHomeworkDetailView.setVisibility(View.VISIBLE);
                }
                if (mFlag == HOMEWORK_ANALYSIS) {
                    mHomeworkAnalysisView.setVisibility(View.VISIBLE);
                    mHomeworkDetailView.setVisibility(View.GONE);
                }
                mBarLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    private void initData() {
        HomeworkModel.getAnswerByClassInfo(mClassInfo, mQuestion, new HomeworkModel.Callback2<List<Answer>>() {
            @Override
            public void callback(List<Answer> answers) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mAnswers.clear();
                        mAnswers.addAll(answers);
                        if (mFlag == HOMEWORK_ANALYSIS) {
                            mHomeworkAnalysisView.setData(mAnswers, mQuestion);
                        }
                        if (mFlag == HOMEWORK_DETAIL) {
                            mHomeworkDetailView.setAnswers(mAnswers);
                        }

                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_bar_back:
                finish();
                break;
            case R.id.tv_bar_check:
                mHomeworkCheckView.show(mQuestion);
                mHomeworkAnalysisView.setVisibility(View.GONE);
                mHomeworkDetailView.setVisibility(View.GONE);
                mBarLayout.setVisibility(View.GONE);
                break;
        }
    }
}
