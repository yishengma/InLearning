package com.yishengma.inlearning.classroom.report;

import android.content.Context;
import android.content.Intent;

import com.yishengma.inlearning.classroom.common.BaseTaskActivity;

public class StudyReportActivity extends BaseTaskActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, StudyReportActivity.class);
        context.startActivity(intent);
    }
}
