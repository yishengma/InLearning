package com.inlearning.app.director.teacher;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.inlearning.app.R;

public class TeacherCourseEditView extends LinearLayout implements View.OnClickListener {

    public TeacherCourseEditView(Context context) {
        this(context, null);
    }

    public TeacherCourseEditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TeacherCourseEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public interface ClickListener {
        void onClick();
    }

    private TextView mAddCourseView;
    private ClickListener mClickListener;
    private LinearLayout mCourseContentView;


    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_tea_edit_add_course, this);
        mAddCourseView = view.findViewById(R.id.tv_add_course);
        mCourseContentView = view.findViewById(R.id.view_course_content);
        mAddCourseView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_course:
                if (mClickListener != null) {
                    mClickListener.onClick();
                }
                break;
        }
    }

    public void addView(View view) {
        mCourseContentView.addView(view);
    }

    public void removeView(View view) {
        mCourseContentView.removeView(view);
    }
}
