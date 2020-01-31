package com.yishengma.inlearning.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.yishengma.inlearning.R;
import com.yishengma.inlearning.adapter.StudyReportAdapter;
import com.yishengma.inlearning.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

public class StudyReportActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mStudyReportsView;
    private StudyReportAdapter mStudyReportAdapter;
    private ImageView mBackView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_common_view);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this,true);
        mStudyReportsView = findViewById(R.id.rv_common_view);
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        mStudyReportAdapter = new StudyReportAdapter(list);
        mStudyReportsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mStudyReportsView.setAdapter(mStudyReportAdapter);
        mBackView = findViewById(R.id.back);
        mBackView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, StudyReportActivity.class);
        context.startActivity(intent);
    }


}