package com.yishengma.inlearning.classroom.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yishengma.inlearning.R;

public abstract class BaseTaskActivity extends AppCompatActivity {
    private RecyclerView mLessonRecyclerView;
    private TextView mTitleView;
    private ImageView mBackView;
    private TextView mCorrelationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_common_view);
        initView();
    }

    private void initView() {
        mLessonRecyclerView = findViewById(R.id.rv_common_view);
        mTitleView = findViewById(R.id.title);
        mBackView = findViewById(R.id.back);
        mCorrelationView = findViewById(R.id.correlation);
        mLessonRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    protected abstract void setRecyclerViewAdapter();
}
