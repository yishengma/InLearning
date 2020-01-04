package com.yishengma.inlearning.classroom.attendclass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yishengma.inlearning.classroom.common.BaseTaskActivity;

public class ClassListActivity extends BaseTaskActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setRecyclerViewAdapter() {

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ClassListActivity.class);
        context.startActivity(intent);
    }
}
