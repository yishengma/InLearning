package com.yishengma.inlearning.classroom.node;

import android.content.Context;
import android.content.Intent;

import com.yishengma.inlearning.classroom.common.BaseTaskActivity;

public class NoteListActivity extends BaseTaskActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context,NoteListActivity.class);
        context.startActivity(intent);
    }
}
