package com.yishengma.inlearning.classroom.exam;

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
import java.util.ArrayList;
import java.util.List;

public class ExamListActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mExamsView;
    private ExamAdapter mExamAdapter;
    private ImageView mBackView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_common_view);
        mExamsView = findViewById(R.id.rv_common_view);
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
        mExamAdapter = new ExamAdapter(list);
        mExamsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mExamsView.setAdapter(mExamAdapter);
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
        Intent intent = new Intent(context, ExamListActivity.class);
        context.startActivity(intent);
    }

}
