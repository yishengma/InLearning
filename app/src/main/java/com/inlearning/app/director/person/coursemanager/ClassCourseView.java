package com.inlearning.app.director.person.coursemanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.inlearning.app.R;

public class ClassCourseView extends FrameLayout {

    public ClassCourseView(@NonNull Context context) {
        this(context, null);
    }

    public ClassCourseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassCourseView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.laout_speciality_course_manager, this);

    }
}
