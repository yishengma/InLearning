package com.inlearning.app.director.speciality.classinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Student;

public class StudentInfoActivity extends AppCompatActivity {
    public static void startActivity(Context context, Student student) {
        Intent intent = new Intent(context, StudentInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("student", student);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    private Student mStudent;
    private static final String TAG = "StudentInfoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        mStudent = (Student) bundle.getSerializable("student");
        Log.e(TAG, "onCreate: "+mStudent );
    }
}
