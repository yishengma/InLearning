package com.yishengma.inlearning.classroom.report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class StudyReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, StudyReportActivity.class);
        context.startActivity(intent);
    }


}
