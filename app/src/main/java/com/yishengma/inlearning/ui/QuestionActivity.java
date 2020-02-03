package com.yishengma.inlearning.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yishengma.inlearning.R;
import com.yishengma.inlearning.adapter.AnswerAdapter;
import com.yishengma.inlearning.bean.AnswerBean;
import com.yishengma.inlearning.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    private RecyclerView mRvAnswers;
    private TextView mQuestionView;
    private ImageView mImageView;
    private TextView mNameView;
    private TextView mTimeView;
    private List<AnswerBean> mAnswerBeanList;
    private AnswerAdapter mAnswerAdapter;
    private ImageView mBackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this,true);
        mRvAnswers = findViewById(R.id.rv_answer);
        mQuestionView = findViewById(R.id.tv_question);
        mImageView = findViewById(R.id.imv_image);
        mNameView = findViewById(R.id.tv_name);
        mTimeView = findViewById(R.id.tv_time);
        mAnswerBeanList = new ArrayList<>();
        mAnswerBeanList.add(new AnswerBean());
        mAnswerBeanList.add(new AnswerBean());
        mAnswerBeanList.add(new AnswerBean());
        mAnswerBeanList.add(new AnswerBean());
        mAnswerBeanList.add(new AnswerBean());
        mAnswerBeanList.add(new AnswerBean());
        mAnswerBeanList.add(new AnswerBean());
        mAnswerBeanList.add(new AnswerBean());
        mBackView = findViewById(R.id.imv_back);
        mAnswerAdapter = new AnswerAdapter(mAnswerBeanList);
        mRvAnswers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvAnswers.setAdapter(mAnswerAdapter);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, QuestionActivity.class);
        context.startActivity(intent);
    }
}
