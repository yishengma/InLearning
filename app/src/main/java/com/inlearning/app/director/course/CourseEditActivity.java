package com.inlearning.app.director.course;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.director.course.edit.IconItemView;
import com.inlearning.app.director.course.edit.NameItemView;
import com.inlearning.app.director.course.edit.ScoreItemView;
import com.inlearning.app.director.course.edit.TimeItemView;
import com.inlearning.app.director.course.edit.TypeItemView;

public class CourseEditActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CourseEditActivity.class);
        context.startActivity(intent);
    }

    private IconItemView mIconItemView;
    private NameItemView mNameItemView;
    private TypeItemView mTypeItemView;
    private TimeItemView mTimeItemView;
    private ScoreItemView mScoreItemView;
    private ImageView mInfoSaveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        initView();

    }

    private void initView() {
        View view = findViewById(R.id.root_view);
        mIconItemView = new IconItemView(view);
        mNameItemView = new NameItemView(view);
        mTypeItemView = new TypeItemView(view);
        mTimeItemView = new TimeItemView(view);
        mScoreItemView = new ScoreItemView(view);
        mInfoSaveView = findViewById(R.id.imv_edit_course_save);
        mInfoSaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInfo();
            }
        });
    }

    private void saveInfo() {
        Course2 course = new Course2();
        course.setName(mNameItemView.getContent())
                .setTime(mTimeItemView.getContent())
                .setScore(mScoreItemView.getContent())
                .setType(mTypeItemView.getContent());
        CourseModel.saveCourseInfo(course, new CourseModel.Callback<Course2>() {
            @Override
            public void onResult(boolean suc, Course2 course2) {
                if (suc) {
                    finish();
                }
            }
        });
    }


//    private void chooseIcon() {
//        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
//        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
//        startActivityForResult(intentToPickPic, 1);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 1:
//                Uri uri = data.getData();
//                String filePath = FileUtil.getFilePathByUri(this, uri);
//                break;
//        }
//    }
}
