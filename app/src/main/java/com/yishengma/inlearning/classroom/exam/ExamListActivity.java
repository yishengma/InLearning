package com.yishengma.inlearning.classroom.exam;

import android.content.Context;
import android.content.Intent;

import com.yishengma.inlearning.classroom.common.BaseTaskActivity;

public class ExamListActivity extends BaseTaskActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context,ExamListActivity.class);
        context.startActivity(intent);
    }
}
