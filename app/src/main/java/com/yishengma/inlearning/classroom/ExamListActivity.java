package com.yishengma.inlearning.classroom;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yishengma.inlearning.R;

public class ExamListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context,ExamListActivity.class);
        context.startActivity(intent);
    }
}
